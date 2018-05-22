package test;

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
		testRendez_Vous(SC);
		
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
}
