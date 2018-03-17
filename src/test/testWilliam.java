package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.ClientDAO;
import Model.Client;
import Singleton.SingletonConnection;

public class testWilliam {

	public static void main(String[] args) {
		
		Connection SC = SingletonConnection.getConnection();
		
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
			// client fonctionne zebi !
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
