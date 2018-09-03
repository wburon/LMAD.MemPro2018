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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import Model.Client;
import Model.Methode;
import Model.Rendez_Vous;
import Singleton.SingletonConnection;

public class Rendez_VousDAO extends DAO<Rendez_Vous> {

	Connection SC = SingletonConnection.getConnection();

	@Override
	public boolean create(Rendez_Vous obj) {
		try {

			PreparedStatement prepare = SC.prepareStatement(
					"Insert into \"Rendez_vous\"(id_rdv, intervention, \"dateDebut\", \"dateFin\") values (?,?,?,?);");
			prepare.setInt(1, maxId());
			prepare.setInt(2, obj.getIntervention().getId_intervention());
			prepare.setTimestamp(3, new java.sql.Timestamp(obj.getDateDeb().getTime()), Calendar.getInstance(TimeZone.getTimeZone("UTC+2")));
			prepare.setTimestamp(4, new java.sql.Timestamp(obj.getDateFin().getTime()), Calendar.getInstance(TimeZone.getTimeZone("GMT")));

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
					.prepareStatement("Update \"Rendez_vous\" set \"dateDebut\"=?, \"dateFin\"=? where id_rdv=?");

			prepare.setDate(1, new java.sql.Date(obj.getDateDeb().getTime()));
			prepare.setDate(2, new java.sql.Date(obj.getDateFin().getTime()));
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
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Rendez_vous\" WHERE \"dateDebut\">?",
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

	public Rendez_Vous[] getRdvInDate(java.util.Date date) {
		InterventionDAO interDAO = new InterventionDAO();
		Rendez_Vous rdv = new Rendez_Vous();
		ArrayList<Rendez_Vous> listRdvInDate = new ArrayList<>();
		try {
			PreparedStatement prepare = SC.prepareStatement(
					"SELECT * FROM \"Intervention\" WHERE date=?", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setDate(1, new java.sql.Date(date.getTime()));
			ResultSet result = prepare.executeQuery();
			while (result.next()) {
				listRdvInDate.add(this.findInterDate(result.getInt("id_intervention")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HashMap<Integer, Rendez_Vous> map = new HashMap<>();
		DateComparator comparateur = this.new DateComparator(map);
		TreeMap<Integer,Rendez_Vous> mapTriee = new TreeMap<>(comparateur);
		for(Rendez_Vous r : listRdvInDate){
			map.put(r.getId_rdv(), r);
		}
		mapTriee.putAll(map);
		
		return mapTriee.values().toArray(new Rendez_Vous[mapTriee.keySet().size()]);
	}

	private Rendez_Vous findInterDate(int id) {
		Rendez_Vous rdv = new Rendez_Vous();
		InterventionDAO interDAO = new InterventionDAO();
		try {
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Rendez_vous\" WHERE intervention=?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();

			if (result.first()) {
				rdv.setId_rdv(result.getInt("id_rdv"));
				rdv.setIntervention(interDAO.find(id));
				rdv.setDateDeb(result.getTimestamp("dateDebut"));
				rdv.setDateFin(result.getTimestamp("dateFin"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rdv;
	}

	class DateComparator implements Comparator<Integer> {
		Map<Integer, Rendez_Vous> base;

		public DateComparator(Map<Integer, Rendez_Vous> base) {
			this.base = base;
		}

		public int compare(Integer a, Integer b) {
			if (base.get(a).getDateDeb().after(base.get(b).getDateDeb())) {
				return -1;
			} else {
				return 1;
			}
		}

	}

}
