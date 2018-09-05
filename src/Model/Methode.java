package Model;

import java.io.File;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DAO.ClientDAO;
import DAO.Rendez_VousDAO;

public class Methode {

	/**
	 * Crée la requete HTTPS et la lance
	 * 
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
	 * 
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
	 * 
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
			mat.setMarque(result.getString("marque"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mat;
	}

	/**
	 * Lance la sauvegarde des informations dans le fichier excel
	 * @throws IOException
	 */
	public static void sauvegarde() throws IOException {
		CSVFileWriter csvFileWriter = new CSVFileWriter(new File("sauvegardeDataClient.csv"));
		csvFileWriter.launch();
		System.exit(0);
	}

	public static int getPlusLongue(List<Map<String, String>> mappedData) {
		int tailleMax = 0;
		for (Map<String, String> map : mappedData) {
			int sizeMap = map.size();
			if (sizeMap > tailleMax) {
				tailleMax = sizeMap;
			}
		}
		return (tailleMax - 5) / 3;
	}

	public static String[] convertListWithNMat(List<String> listTitles, int nbMaterielMaximum) {
		for (int i = 1; i <= nbMaterielMaximum; i++) {

			listTitles.add("Nom Materiel " + i);
			listTitles.add("Type Materiel " + i);
			listTitles.add("Numéro de série Materiel " + i);
			listTitles.add("Marque Materiel" + i);
		}
		String[] titlesTab = new String[listTitles.size()];
		for (int i = 0; i < listTitles.size(); i++) {
			titlesTab[i] = listTitles.get(i);
		}
		return titlesTab;
	}

	/**
	 * Trouve les neuf semaines à partir du param calendar
	 * @param calendar
	 * @return renvoie les neuf semaines sous la forme d'un HashMap<Monday, ResteDeLaSemaine>
	 */
	public static HashMap<Date, Date[]> findCurrentWeekInit(Calendar calendar) {
		HashMap<Date, Date[]> list = new HashMap<>();

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		list.put(calendar.getTime(), getDateOfWeek(calendar));

		for (int i = 1; i <= 8; i++) {
			do {
				calendar.add(Calendar.DATE, 1);
			} while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY);
			list.put(calendar.getTime(), getDateOfWeek(calendar));
		}
		return list;
	}

	/**
	 * Recupère les 6 jours suivant le jour en param
	 * @param calendar 
	 * @return un tableau de 6 jour
	 */
	private static Date[] getDateOfWeek(Calendar calendar) {
		Date[] dateOfWeek = new Date[6];
		for (int i = 0; i < dateOfWeek.length; i++) {
			calendar.add(Calendar.DATE, 1);
			dateOfWeek[i] = calendar.getTime();
		}
		return dateOfWeek;
	}

	/**
	 * Met en forme les 3 premières lignes de la liste (le trois dernière
	 * interventions)
	 * 
	 * @param listIntervention
	 * @return
	 */
	public static String toString3(ArrayList<Intervention> listIntervention) {
		int sizeList = listIntervention.size();
		ArrayList<Intervention> list3 = new ArrayList<>();
		switch (sizeList) {
		case 0:
			break;
		case 1:
			list3.add(listIntervention.get(0));
			break;
		case 2:
			list3.add(listIntervention.get(0));
			list3.add(listIntervention.get(1));
			break;
		default:
			list3.add(listIntervention.get(sizeList - 1));
			list3.add(listIntervention.get(sizeList - 2));
			list3.add(listIntervention.get(sizeList - 3));
		}

		return toStringInterventionList(list3, true);
	}

	/**
	 * La liste des interventions de la liste "list" converti en String et ajuster selon le parametre VP
	 * @param list
	 * @param VP : chaine de caractère restrainte si "true"
	 * @return
	 */
	public static String toStringInterventionList(ArrayList<Intervention> list, boolean VP) {
		String toString = "";
		for (Intervention i : list) {
			String inter = i.getDate().toString() + i.getCommentaire();
			if (VP == true)
				inter = monStringModifier(inter);
			toString += inter + "<br>";
		}
		return toString;
	}

	public static String composeTroisClient(String positionClient) {
		// TODO a partir de l'agenda et de la positionClient trouve les trois
		// rendez-vous les plus proche et génère un string
		Rendez_VousDAO rdvDAO = new Rendez_VousDAO();
		ArrayList<Rendez_Vous> list3Client = new ArrayList<>();
		HashMap<Integer, Double> map = new HashMap<>();
		Methode methode = new Methode();
		ValueComparator comparateur = methode.new ValueComparator(map);
		TreeMap<Integer, Double> mapTriee = new TreeMap<>(comparateur);
		for (Rendez_Vous rdv : rdvDAO.getListRdv()) {
			Double d = getDistance(rdv.getIntervention().getMateriel().getClient().getGps(), positionClient);
			map.put(rdv.getIntervention().getMateriel().getClient().getId_client(), d);
		}
		mapTriee.putAll(map);
		// TODO recupérer les trois premiers
		return null;
	}

	private static Double getDistance(String gps, String positionClient) {
		Double result = null;
		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + gps
					+ "&destinations=" + positionClient + "&units=metric&key=AIzaSyAf7g4C-OE5qG-sDfzctgKpX7kG0lXnVcg");
			result = postURLDistance(url, "");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static Double postURLDistance(URL url, String sParamsToPost) {
		StringBuilder Sb = new StringBuilder();

		// recup du saut de ligne
		String LineSep = null;
		try {
			LineSep = System.getProperty("line.separator");
		} catch (Exception e) {
			LineSep = "\n";
		}

		Double resultat = 0.0;
		try {
			HttpURLConnection UrlConn = (HttpURLConnection) url.openConnection();
			System.setProperty("http.agent", "Chrome");
			// UrlConn.setRequestMethod("POST");
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
			resultat = distance(Response);
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

	private static Double distance(InputStream result) {
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
		Double d = 0.0;
		return d;
	}

	class ValueComparator implements Comparator<Integer> {
		Map<Integer, Double> base;

		public ValueComparator(Map<Integer, Double> base) {
			this.base = base;
		}

		public int compare(Integer a, Integer b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	public static ArrayList<JButton> createButton(int nbMat, String name) {
		ArrayList<JButton> list = new ArrayList<>();
		for (int i = 0; i < nbMat; i++) {
			list.add(new JButton(name));
		}
		return list;
	}

	public static String toStringDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
		cal.setTime(date);
		return format.format(cal.getTime());
	}

	public static String toStringHourOfDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Calendar cal = new GregorianCalendar(Locale.FRANCE);
		cal.setTime(date);
		return format.format(cal.getTime());
	}

	static int VALEUR_MAX_LABEL = 60; // limite de 60 caractères dans un JLabel

	public static String monStringModifier(String lbl) {
		int taille = lbl.length();
		if (taille > VALEUR_MAX_LABEL)
			lbl = remplaceResteParVide(lbl);
		return lbl;
	}

	public static String remplaceResteParVide(String text) {

		StringBuilder sb = new StringBuilder();
		sb.append(text.subSequence(0, VALEUR_MAX_LABEL));// on recupère le
															// nombre de
															// caractère voulus
		sb.append("...");
		return sb.toString();
	}

}
