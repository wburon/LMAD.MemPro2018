package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Client;
import Model.Rendez_Vous;
import Singleton.SingletonConnection;

public class Rendez_VousDAO extends DAO<Rendez_Vous>{
	
	Connection SC = SingletonConnection.getConnection();

	@Override
	public boolean create(Rendez_Vous obj) {
		try {

			PreparedStatement prepare = SC
					.prepareStatement("Insert into \"Rendez_vous\"(intervention, data) values (?,?);");

			prepare.setInt(1, obj.getIntervention().getId_intervention());
			prepare.setDate(2, obj.getDate());
			
			prepare.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Rendez_Vous obj) {
		try{
			PreparedStatement prepare =SC.prepareStatement("Delete from \"Rendez_vous\" where id_rdv=?");

			prepare.setInt(1, obj.getId_rdv());
			
			prepare.executeUpdate();
			
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Rendez_Vous obj) {
		try{
			PreparedStatement prepare=SC.prepareStatement("Update \"Rendez_vous\" set data=? where id_rdv=?");
			
			prepare.setDate(1, obj.getDate());
			prepare.setInt(2, obj.getId_rdv());

			prepare.executeUpdate();
			return true;	
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Rendez_Vous find(int id) {
		Rendez_Vous rdv = new Rendez_Vous();
		InterventionDAO interDAO = new InterventionDAO();
		try{
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Rendez_vous\" WHERE id_rdv=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setInt(1, id);
			ResultSet result=prepare.executeQuery();
			
			if(result.first()){
				rdv.setId_rdv(id);
				rdv.setIntervention(interDAO.find(result.getInt("intervention")));
				rdv.setDate(result.getDate("data"));				
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rdv;
	}

}
