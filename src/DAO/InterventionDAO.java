package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Model.Intervention;
import Singleton.SingletonConnection;

public class InterventionDAO extends DAO<Intervention>{

	Connection SC = SingletonConnection.getConnection();

	@Override
	public boolean create(Intervention obj) {
		try {

			PreparedStatement prepare = SC
					.prepareStatement("Insert into \"Intervention\"(id_intervention, materiel,type_intervention,\"numFacture\",\"numBL\",\"refPiece\", commentaire, date) values (?,?,?,?,?,?,?,?);");
			prepare.setInt(1, maxId());
			prepare.setInt(2, obj.getMateriel().getId_materiel());
			prepare.setInt(3, obj.getType_intervention().getId_type_intervention());
			prepare.setInt(4, obj.getNumFacture());
			prepare.setInt(5, obj.getNumBL());
			prepare.setInt(6, obj.getRefPiece());
			prepare.setString(7, obj.getCommentaire());
			prepare.setDate(8, (Date) obj.getDate());
			
			prepare.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Intervention obj) {
		try{
			PreparedStatement prepare =SC.prepareStatement("Delete from \"Intervention\" where id_intervention=?");

			prepare.setInt(1, obj.getId_intervention());
			
			prepare.executeUpdate();
			
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Intervention obj) {
		try{
			PreparedStatement prepare=SC.prepareStatement("Update \"Intervention\" set materiel=?, type_intervention=?, numFacture=?, numBL=?, refPiece=?, commentaire=?, date=? where id_intervention=?");
			
			prepare.setInt(1, obj.getMateriel().getId_materiel());
			prepare.setInt(2, obj.getType_intervention().getId_type_intervention());
			prepare.setInt(3, obj.getNumFacture());
			prepare.setInt(4, obj.getNumBL());
			prepare.setInt(5, obj.getRefPiece());
			prepare.setString(5, obj.getCommentaire());
			prepare.setDate(7, (Date) obj.getDate());
			prepare.setInt(8, obj.getId_intervention());

			prepare.executeUpdate();
			return true;	
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Intervention find(int id) {
		Intervention intervention = new Intervention();
		Type_InterventionDAO tiDAO = new Type_InterventionDAO();
		MaterielDAO matDAO = new MaterielDAO();
		try{
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Intervention\" WHERE id_intervention=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setInt(1, id);
			ResultSet result=prepare.executeQuery();
			
			if(result.first()){
				intervention.setId_intervention(id);
				intervention.setMateriel(matDAO.find(result.getInt("materiel")));
				intervention.setType_intervention(tiDAO.find(result.getInt("type_intervention")));
				intervention.setNumFacture(result.getInt("numFacture"));
				intervention.setNumBL(result.getInt("numBL"));
				intervention.setRefPiece(result.getInt("refPiece"));
				intervention.setCommentaire(result.getString("commentaire"));
				intervention.setDate(result.getDate("date"));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return intervention;
	}
	
	public int maxId() {
		Statement state;
		int nbRow=0;
		try {
			state = SC.createStatement();
			ResultSet nbLigne = state.executeQuery("SELECT MAX(id_intervention) FROM \"public\".\"Intervention\" ");
			nbLigne.next();
			nbRow = nbLigne.getInt(1) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbRow;
	}

	/**
	 * Méthode qui retourne la liste des interventions d'un matériels précis dans l'ordre décroissant des dates d'intervention
	 * @param id_materiel
	 * @return
	 */
	public ArrayList<Intervention> getListIntervention(int id_materiel) {
		ArrayList<Intervention> historiqueIntervention = new ArrayList<>();
		Type_InterventionDAO tiDAO = new Type_InterventionDAO();
		MaterielDAO matDAO = new MaterielDAO();
		try{
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Intervention\" WHERE materiel=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setInt(1, id_materiel);
			ResultSet result=prepare.executeQuery();
			
			while(result.next()){
				Intervention intervention = new Intervention();
				intervention.setId_intervention(result.getInt("id_intervention"));
				intervention.setMateriel(matDAO.find(id_materiel));
				intervention.setType_intervention(tiDAO.find(result.getInt("type_intervention")));
				intervention.setNumFacture(result.getInt("numFacture"));
				intervention.setNumBL(result.getInt("numBL"));
				intervention.setRefPiece(result.getInt("refPiece"));
				intervention.setCommentaire(result.getString("commentaire"));
				intervention.setDate(result.getDate("date"));
				
				historiqueIntervention.add(intervention);
			}
			
			Collections.sort(historiqueIntervention, ComparatorDate);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return historiqueIntervention;
	}
	
	public static Comparator<Intervention> ComparatorDate = new Comparator<Intervention>() {
	     
        @Override
        public int compare(Intervention i1, Intervention i2) {
            return i1.getDate().compareTo(i2.getDate());
        }
    };

}
