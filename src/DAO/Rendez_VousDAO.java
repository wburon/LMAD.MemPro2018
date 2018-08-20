package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Model.Client;
import Model.Rendez_Vous;
import Singleton.SingletonConnection;

public class Rendez_VousDAO extends DAO<Rendez_Vous> {

	Connection SC = SingletonConnection.getConnection();

	@Override
	public boolean create(Rendez_Vous obj) {
		try {

			PreparedStatement prepare = SC.prepareStatement(
					"Insert into \"Rendez_vous\"(id_rdv, id_intervention, dateDebut, dateFin) values (?,?,?,?);");
			prepare.setInt(1, maxId());
			prepare.setInt(2, obj.getIntervention().getId_intervention());
			prepare.setDate(3, (Date) obj.getDateDeb());
			prepare.setDate(4, (Date) obj.getDateFin());

			prepare.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Rendez_Vous obj) {
		try {
			PreparedStatement prepare = SC.prepareStatement("Delete from \"Rendez_vous\" where id_rdv=?");

			prepare.setInt(1, obj.getId_rdv());

			prepare.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Rendez_Vous obj) {
		try {
			PreparedStatement prepare = SC
					.prepareStatement("Update \"Rendez_vous\" set dateDebut=?, dateFin=? where id_rdv=?");

			prepare.setDate(1, (Date) obj.getDateDeb());
			prepare.setDate(2, (Date) obj.getDateFin());
			prepare.setInt(3, obj.getId_rdv());

			prepare.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Rendez_Vous find(int id) {
		Rendez_Vous rdv = new Rendez_Vous();
		InterventionDAO interDAO = new InterventionDAO();
		try {
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Rendez_vous\" WHERE id_rdv=?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();

			if (result.first()) {
				rdv.setId_rdv(id);
				rdv.setIntervention(interDAO.find(result.getInt("intervention")));
				rdv.setDateDeb(result.getDate("dateDebut"));
				rdv.setDateFin(result.getDate("dateFin"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rdv;
	}

	public int maxId() {
		Statement state;
		int nbRow = 0;
		try {
			state = SC.createStatement();
			ResultSet nbLigne = state.executeQuery("SELECT MAX(id_rdv) FROM \"public\".\"Rendez_vous\" ");
			nbLigne.next();
			nbRow = nbLigne.getInt(1) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbRow;
	}

	public ArrayList<Rendez_Vous> getListRdv() {
		InterventionDAO interDAO = new InterventionDAO();
		Rendez_Vous rdv = new Rendez_Vous();
		ArrayList<Rendez_Vous> listRdv = new ArrayList<>();
		try {
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Rendez_vous\" WHERE dateDebut>?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setDate(1, (java.sql.Date) Calendar.getInstance().getTime());
			ResultSet result = prepare.executeQuery();
			while (result.next()) {
				rdv.setId_rdv(result.getInt("id_rdv"));
				rdv.setIntervention(interDAO.find(result.getInt("intervention")));
				rdv.setDateDeb(result.getDate("dateDebut"));
				rdv.setDateFin(result.getDate("dateFin"));
				listRdv.add(rdv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listRdv;
	}

}
