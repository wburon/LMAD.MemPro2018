package test;

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

import java.net.*;
import java.io.*;

import com.google.gdata.client.*;
import com.google.gdata.client.calendar.*;
import com.google.gdata.data.*;
import com.google.gdata.data.acl.*;
import com.google.gdata.data.calendar.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;

import sample.util.*;

public class testWilliam {

	public static void main(String[] args) {

		Connection SC = SingletonConnection.getConnection();

		// SUCCESS testClient(SC);
		// SUCCESS //testMaxId(SC);
		// testMateriel(SC);
		// testRendez_Vous(SC);

		// String adresse = "3 rue rabelais";
		// String ville = "angers";
		// String adresse_complete = adresse + " " + ville;
		// String coord = getGPSCoord(adresse_complete);

		// try {
		// String result = postURL(new
		// URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key=AIzaSyAf7g4C-OE5qG-sDfzctgKpX7kG0lXnVcg"),
		// "");
		// //System.out.println(result);
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			CalendarService myService = new CalendarService("exampleCo-exampleApp-1.0");
			myService.setUserCredentials("myaccount@gmail.com", "mypass");
			URL feedUrl = new URL("http://www.google.com/calendar/feeds/default/allcalendars/full");
			CalendarFeed resultFeed = myService.getFeed(feedUrl, CalendarFeed.class);
			System.out.println("Your calendars:");
			System.out.println();
			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				CalendarEntry entry = resultFeed.getEntries().get(i);
				System.out.println("\t" + entry.getTitle().getPlainText());
			}
		} catch (Exception e) {
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
	/*
	 * private static void testRendez_Vous(Connection SC){ try {
	 * PreparedStatement prepare = SC.prepareStatement(
	 * "Select * from \"Rendez_vous\""); prepare.executeQuery();
	 * 
	 * InterventionDAO interDAO = new InterventionDAO();
	 * 
	 * Rendez_Vous rdv = new Rendez_Vous();
	 * rdv.setIntervention(interDAO.find(0));
	 * rdv.setDate(java.sql.Date.valueOf("22-03-2018"));
	 * 
	 * Rendez_VousDAO rdvDAO = new Rendez_VousDAO(); rdvDAO.create(rdv);
	 * System.out.println(rdvDAO.find(0)); rdvDAO.delete(rdv);
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

}
