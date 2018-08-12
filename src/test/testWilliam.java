package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DAO.ClientDAO;
import DAO.InterventionDAO;
import DAO.MaterielDAO;
import DAO.Rendez_VousDAO;
import Model.Client;
import Model.Materiel;
import Model.Methode;
import Model.Rendez_Vous;
import Singleton.SingletonConnection;



public class testWilliam {

	public static void main(String[] args) {
		
		
        

//		 Connection SC = SingletonConnection.getConnection();

		// SUCCESS testClient(SC);
		// SUCCESS //testMaxId(SC);
		// testMateriel(SC);
		// testRendez_Vous(SC);

//		 String adresse = "3 rue rabelais";
//		 String ville = "angers";
//		 String adresse_complete = adresse + " " + ville;
//		 String coord = getGPSCoord(adresse_complete);

//		 try {
//		 double result = Methode.postURLDistance(new
//		 URL("https://www.viamichelin.fr/web/Itineraires?departure=3%20Rue%20Rabelais%2C%2049000%20Angers%2C%20France&arrival=20%20Rue%20Saint-L%C3%A9onard%2C%2049000%20Angers%2C%20France&index=0&vehicle=0&type=0&distance=km&currency=EUR&highway=false&toll=false&vignette=false&orc=false&crossing=true&caravan=false&shouldUseTraffic=false&withBreaks=false&break_frequency=7200&coffee_duration=1200&lunch_duration=3600&diner_duration=3600&night_duration=32400&car=hatchback&fuel=petrol&fuelCost=1.546&allowance=0&corridor=&departureDate=&arrivalDate=&fuelConsumption="),
//		 "");
//		 //System.out.println(result);
//		 } catch (MalformedURLException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }
		 
		 HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) new URL("https://www.viamichelin.fr/web/Itineraires?departure=3%20Rue%20Rabelais%2C%2049000%20Angers%2C%20France&arrival=20%20Rue%20Saint-L%C3%A9onard%2C%2049000%20Angers%2C%20France&index=0&vehicle=0&type=0&distance=km&currency=EUR&highway=false&toll=false&vignette=false&orc=false&crossing=true&caravan=false&shouldUseTraffic=false&withBreaks=false&break_frequency=7200&coffee_duration=1200&lunch_duration=3600&diner_duration=3600&night_duration=32400&car=hatchback&fuel=petrol&fuelCost=1.546&allowance=0&corridor=&departureDate=&arrivalDate=&fuelConsumption=").openConnection();
		
	        conn.connect();
	 
	        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	 
	        byte[] bytes = new byte[1024];
	        int tmp ;
	        while( (tmp = bis.read(bytes) ) != -1 ) {
	            String chaine = new String(bytes,0,tmp);
	            System.out.print(chaine);
	        }
	         
	        conn.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}

	private static void testMaxId(Connection sC) {
		ClientDAO clDAO = new ClientDAO();
		System.out.println(clDAO.maxId());
	}

	/**
	 * materiel fonctionne zebi
	 * 
	 * @param SC
	 */
	private static void testMateriel(Connection SC) {
		try {
			PreparedStatement prepare = SC.prepareStatement("Select * from \"Materiel\"");
			prepare.executeQuery();

			Materiel materiel = new Materiel();
			materiel.setNom("nom");
			materiel.setType("type");
			materiel.setNumSerie("numSerie");
			// maintenant ajouter un client

			MaterielDAO mat = new MaterielDAO();
			mat.create(materiel);
			System.out.println(mat.find(1));
			mat.delete(materiel);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * client fonctionne zebi !
	 * 
	 * @param SC
	 */
	private static void testClient(Connection SC) {

		try {
			PreparedStatement prepare = SC.prepareStatement("Select * from \"Client\"");
			prepare.executeQuery();
			ClientDAO cl = new ClientDAO();
			Client client = new Client();
			client.setNom("nom");
			client.setPrenom("prenom");
			String adresse = "3 rue rabelais";
			client.setAdresse(adresse);
			String ville = "angers";
			client.setVille(ville);
			client.setTel(0241);
			String adresse_complete = adresse + " " + ville;
			String gps = Methode.getGPSCoord(adresse_complete);
			System.out.println(gps);
			client.setGps(gps);
			client.setMail("courriel");
			int id = cl.maxId();
			System.out.println(id);
			client.setId_client(id);

			cl.create(client);
			// System.out.println(cl.find(1));
			// cl.delete(client);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testRendez_Vous(Connection SC) {
		try {
			PreparedStatement prepare = SC.prepareStatement("Select * from \"Rendez_vous\"");
			prepare.executeQuery();

			InterventionDAO interDAO = new InterventionDAO();

			Rendez_Vous rdv = new Rendez_Vous();
			rdv.setIntervention(interDAO.find(0));
			rdv.setDateDeb(java.sql.Date.valueOf("22-03-2018, 10:00"));

			Rendez_VousDAO rdvDAO = new Rendez_VousDAO();
			rdvDAO.create(rdv);
			System.out.println(rdvDAO.find(0));
			rdvDAO.delete(rdv);

		} catch (SQLException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
