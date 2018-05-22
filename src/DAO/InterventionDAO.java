package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Intervention;
import Singleton.SingletonConnection;

public class InterventionDAO extends DAO<Intervention>{

	Connection SC = SingletonConnection.getConnection();

	@Override
	public boolean create(Intervention obj) {
		try {

			PreparedStatement prepare = SC
					.prepareStatement("Insert into \"Intervention\"(materiel,type_intervention,\"numFacture\",\"numBL\",\"refPiece\") values (?,?,?,?,?);");

			prepare.setInt(1, obj.getMateriel().getId_materiel());
			prepare.setInt(2, obj.getType_intervention().getId_type_intervention());
			prepare.setInt(3, obj.getNumFacture());
			prepare.setInt(4, obj.getNumBL());
			prepare.setInt(5, obj.getRefPiece());
			
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
			PreparedStatement prepare=SC.prepareStatement("Update \"Intervention\" set materiel=?, type_intervention=?, numFacture=?, numBL=?, refPiece=? where id_intervention=?");
			
			prepare.setInt(1, obj.getMateriel().getId_materiel());
			prepare.setInt(2, obj.getType_intervention().getId_type_intervention());
			prepare.setInt(3, obj.getNumFacture());
			prepare.setInt(4, obj.getNumBL());
			prepare.setInt(5, obj.getRefPiece());
			prepare.setInt(6, obj.getId_intervention());

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
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return intervention;
	}

}
