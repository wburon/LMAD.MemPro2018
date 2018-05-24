package Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DAO.ClientDAO;

public class Methode {

	/**
	 * Crée la requete HTTPS et la lance
	 * @param adresse_complete
	 * @return les coordonnées GPS correspondant à l'adresse complete
	 */
	public static String getGPSCoord(String adresse_complete) {
		String result = null;
		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="
					+ adresse_complete.replace(" ", "+") + "&key=AIzaSyAf7g4C-OE5qG-sDfzctgKpX7kG0lXnVcg");
			result = postURL(url, "");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * Recupère les coordonnées GPS d'une adresse complete (adresse + ville)
	 * @param url
	 * @param sParamsToPost
	 * @return les coordonnées GPS au format text, separateur : ","
	 */
	public static String postURL(URL url, String sParamsToPost) {
		StringBuilder Sb = new StringBuilder();

		// recup du saut de ligne
		String LineSep = null;
		try {
			LineSep = System.getProperty("line.separator");
		} catch (Exception e) {
			LineSep = "\n";
		}

		String resultat = null;
		try {
			HttpURLConnection UrlConn = (HttpURLConnection) url.openConnection();
			UrlConn.setRequestMethod("POST");
			UrlConn.setAllowUserInteraction(false);
			// envoyer des params
			UrlConn.setDoOutput(true);

			// poster les params
			PrintWriter ParamWriter = new PrintWriter(UrlConn.getOutputStream());

			ParamWriter.print(sParamsToPost);
			// fermer le post avant de lire le resultat ... logique
			ParamWriter.flush();
			ParamWriter.close();

			// Lire la reponse
			InputStream Response = UrlConn.getInputStream();
			Double[] coord = CoordAddress(Response);
			resultat = coord[0] + "," + coord[1];
			// deconnection
			UrlConn.disconnect();
		} catch (ConnectException ctx) {
			System.out.println("Connection lost : server may be down");
			ctx.printStackTrace();
		} catch (Exception e) {
			System.out.println("postURL : " + e.getMessage());
			e.printStackTrace();
		}
		return resultat;
	}

	/**
	 * Recupère le coordonnée GPS d'une réponse JSON
	 * 
	 * @param result
	 * @return Double[2] : {lng, lat}
	 */
	private static Double[] CoordAddress(InputStream result) {
		Double lng = null, lat = null;
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject myJSONResult = (JSONObject) jsonParser.parse(new InputStreamReader(result, "UTF-8"));
			JSONArray o = (JSONArray) myJSONResult.get("results");
			JSONObject p = (JSONObject) o.get(0);
			JSONObject q = (JSONObject) p.get("geometry");
			JSONObject location = (JSONObject) q.get("location");
			lng = (Double) location.get("lng");
			lat = (Double) location.get("lat");
			System.out.println("lng : " + lng + " lat : " + lat);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Double lng_lat[] = { lng, lat };
		return lng_lat;
	}

	/**
	 * Forme un object Materiel à partir d'un ResultSet
	 * @param result
	 * @return Materiel
	 */
	public static Materiel formulateMateriel(ResultSet result) {
		Materiel mat = new Materiel();
		ClientDAO clDAO = new ClientDAO();
		try {
			mat.setId_materiel(result.getInt("id_materiel"));
			mat.setNom(result.getString("nom"));
			mat.setType(result.getString("type"));
			mat.setNumSerie(result.getString("numSerie"));
			mat.setClient(clDAO.find(result.getInt("id_client")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mat;
	}

	public static void sauvegarde(JFrame frame) throws IOException {
		CSVFileWriter.launch();
		frame.dispose();
	}

}
