package test;

import java.io.BufferedReader;
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
import java.util.Date;

import DAO.ClientDAO;
import DAO.InterventionDAO;
import DAO.MaterielDAO;
import DAO.Rendez_VousDAO;
import Model.Client;
import Model.Materiel;
import Model.Rendez_Vous;
import Singleton.SingletonConnection;

public class testWilliam {

	public static void main(String[] args) {
		
		Connection SC = SingletonConnection.getConnection();
		
		//testClient(SC);
		//testMateriel(SC);
		//testRendez_Vous(SC);
		try {
			String result = postURL(new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key=AIzaSyAf7g4C-OE5qG-sDfzctgKpX7kG0lXnVcg"), "");
			//System.out.println(result);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * materiel fonctionne zebi
	 * @param SC
	 */
	private static void testMateriel(Connection SC) {
		try {
			PreparedStatement prepare = SC.prepareStatement("Select * from \"Materiel\"");
			prepare.executeQuery();
			
			Materiel materiel  = new Materiel();
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
	 * @param SC
	 */
	private static void testClient(Connection SC){

		try {
			PreparedStatement prepare = SC.prepareStatement("Select * from \"Client\"");
			prepare.executeQuery();
			
			Client client = new Client();
			client.setNom("nom");
			client.setPrenom("prenom");
			client.setAdresse("adresse");
			client.setVille("ville");
			client.setTel(0241);
			client.setGps("gps");
			client.setMail("courriel");	
			
			ClientDAO cl = new ClientDAO();
			cl.create(client);
			System.out.println(cl.find(0));
			cl.delete(client);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*
	private static void testRendez_Vous(Connection SC){
		try {
			PreparedStatement prepare = SC.prepareStatement("Select * from \"Rendez_vous\"");
			prepare.executeQuery();
			
			InterventionDAO interDAO = new InterventionDAO();
			
			Rendez_Vous rdv = new Rendez_Vous();
			rdv.setIntervention(interDAO.find(0));
			rdv.setDate(java.sql.Date.valueOf("22-03-2018"));
			
			Rendez_VousDAO rdvDAO = new Rendez_VousDAO();
			rdvDAO.create(rdv);
			System.out.println(rdvDAO.find(0));
			rdvDAO.delete(rdv);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	
	
	 
	public static String postURL(URL a_Url, String a_sParamsToPost)
	{
	      StringBuilder o_oSb = new StringBuilder();
	 
	      //recup du saut de ligne
	      String o_sLineSep = null;
	      try
	      {
	         o_sLineSep = System.getProperty("line.separator");
	      }
	      catch (Exception e)
	      {
	         o_sLineSep = "\n";
	      }
	 
	      
		try
	      {
	         HttpURLConnection o_oUrlConn = (HttpURLConnection) a_Url.openConnection();
	         o_oUrlConn.setRequestMethod("POST");
	         o_oUrlConn.setAllowUserInteraction(false);
	         //envoyer des params
	         o_oUrlConn.setDoOutput(true);
	 
	         //poster les params
	         PrintWriter o_oParamWriter = new PrintWriter(o_oUrlConn.getOutputStream());
	 
	         o_oParamWriter.print(a_sParamsToPost);
	         //fermer le post avant de lire le resultat ... logique
	         o_oParamWriter.flush();
	         o_oParamWriter.close();
	 
	         //Lire la reponse
	         InputStream  o_oResponse = o_oUrlConn.getInputStream();
	         BufferedReader o_oBufReader = new BufferedReader(new InputStreamReader(o_oResponse));
	         String sLine;
	 
	         while ((sLine = o_oBufReader.readLine()) != null)
	         {
	            o_oSb.append(sLine);
	            o_oSb.append(o_sLineSep);
	         }
	         //deconnection
	         o_oUrlConn.disconnect();
	      }
	      catch(ConnectException ctx)
	      {
	        System.out.println("Connection lost : server may be down");
	        ctx.printStackTrace();
	      }
	      catch (Exception e)
	      {
	         System.out.println("postURL : "+e.getMessage());
	         e.printStackTrace();
	      }
	      System.out.println("retour url="+o_oSb.toString());
	      return o_oSb.toString();
	    }
}
