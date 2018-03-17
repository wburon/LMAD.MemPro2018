package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Singleton.SingletonConnection;
import Model.Client;

public class ClientDAO extends DAO<Client>{
	Connection SC = SingletonConnection.getConnection();

	@Override
	public boolean create(Client obj) {
		try {

			PreparedStatement prepare = SC
					.prepareStatement("Insert into \"Client\"(nom,prenom,adresse,ville,tel,gps,courriel) values (?,?,?,?,?,?,?);");

			prepare.setString(1, obj.getNom());
			prepare.setString(2, obj.getPrenom());
			prepare.setString(3, obj.getAdresse());
			prepare.setString(4, obj.getVille());
			prepare.setInt(5, obj.getTel());
			prepare.setString(6, obj.getGps());
			prepare.setString(7, obj.getMail());
			
			prepare.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Client obj) {
		try{
			PreparedStatement prepare =SC.prepareStatement("Delete from \"Client\" where id_client=?");

			prepare.setInt(1, obj.getId_client());
			
			prepare.executeUpdate();
			
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Client obj) {
		try{
			PreparedStatement prepare=SC.prepareStatement("Update \"Client\" set adresse=?, ville=?, tel=?, gps=?, courriel=? where id_client=?");
			
			prepare.setString(1, obj.getAdresse());
			prepare.setString(2, obj.getVille());
			prepare.setInt(3, obj.getTel());
			prepare.setString(4, obj.getGps());
			prepare.setString(5, obj.getMail());
			prepare.setInt(6, obj.getId_client());

			prepare.executeUpdate();
			return true;	
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Client find(int id) {
		Client client = new Client();
		try{
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Client\" WHERE id_client=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setInt(1, id);
			ResultSet result=prepare.executeQuery();
			
			if(result.first()){
				client.setId_client(id);
				client.setNom(result.getString("nom"));
				client.setPrenom(result.getString("prenom"));
				client.setAdresse(result.getString("adresse"));
				client.setVille(result.getString("ville"));
				client.setTel(result.getInt("tel"));
				client.setGps(result.getString("gps"));
				client.setMail(result.getString("courriel"));					
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return client;
	}
}
