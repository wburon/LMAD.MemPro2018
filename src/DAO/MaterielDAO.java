package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Materiel;
import Model.Resultat;
import Singleton.SingletonConnection;

public class MaterielDAO extends DAO<Materiel>{

	Connection SC = SingletonConnection.getConnection();

	@Override
	public boolean create(Materiel obj) {
		try {

			PreparedStatement prepare = SC
					.prepareStatement("Insert into \"Materiel\"(id_materiel,nom,type,\"numSerie\",id_client,marque) values (?,?,?,?,?,?);");
			prepare.setInt(1, maxId());
			prepare.setString(2, obj.getNom());
			prepare.setString(3, obj.getType());
			prepare.setString(4, obj.getNumSerie());
			prepare.setInt(5, obj.getClient().getId_client());
			prepare.setString(6, obj.getMarque());
			
			prepare.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Materiel obj) {
		try{
			PreparedStatement prepare =SC.prepareStatement("Delete from \"Materiel\" where id_materiel=?");

			prepare.setInt(1, obj.getId_materiel());
			
			prepare.executeUpdate();
			
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Materiel obj) {
		try{
			PreparedStatement prepare=SC.prepareStatement("Update \"Materiel\" set nom=?, type=?, \"numSerie\"=?, id_client=?, marque = ? where id_materiel=?");
			
			prepare.setString(1, obj.getNom());
			prepare.setString(2, obj.getType());
			prepare.setString(3, obj.getNumSerie());
			prepare.setInt(4, obj.getClient().getId_client());
			prepare.setString(5, obj.getMarque());
			prepare.setInt(6, obj.getId_materiel());

			prepare.executeUpdate();
			return true;	
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Materiel find(int id) {
		Materiel mat = new Materiel();
		ClientDAO clDAO = new ClientDAO();
		try{
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Materiel\" WHERE id_materiel=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setInt(1, id);
			ResultSet result=prepare.executeQuery();
			
			if(result.first()){
				mat.setId_materiel(id);
				mat.setNom(result.getString("nom"));
				mat.setType(result.getString("type"));
				mat.setNumSerie(result.getString("numSerie"));
				mat.setClient(clDAO.find(result.getInt("id_client")));
				mat.setMarque(result.getString("marque"));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return mat;
	}
	
	public int maxId() {
		Statement state;
		int nbRow=0;
		try {
			state = SC.createStatement();
			ResultSet nbLigne = state.executeQuery("SELECT MAX(id_materiel) FROM \"public\".\"Materiel\" ");
			nbLigne.next();
			nbRow = nbLigne.getInt(1) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbRow;
	}
	
	/**
	 * Méthode qui permet d'obtenir les résultat de la recherche d'un mot dans une colonne de la base de donnée.
	 * @param mot String correspondant au mot recherché
	 * @param champs String correspond au nom de la colonne dans laquelle on recherche le mot
	 * @return listRes une liste de l'objet Resultat composer de l'id_client et de la note associé à ce résultat
	 */
	public ArrayList<Resultat> getResultat(String mot, String champs){
		ArrayList<Resultat> listRes = new ArrayList<>();
		Statement state;
		try{
			state = SC.createStatement();
			state.executeQuery("SELECT \"id_materiel\",\""+champs+"\" FROM \"public\".\"Materiel\"");
			ResultSet result=state.getResultSet();
			
			int lev;double note;
			
			//On parcours l'ensemble des mots de la colonne associé au String champs pour trouver une correspondance avec le mot recherché
			while(result.next()){
				String motTest = result.getString(champs);
				//La distance de Levenshtein permet de mesurer le nombre d'opérations pour passer du motTest au mot recherché
				lev=levenshtein(mot,motTest);
				//La note est la distance entre le motTest et le mot recherché sur la taille du mot recherché
				note = (double) lev/mot.length();
				//Si la note est inférieur à 0.4 on estime qu'il est pertinent d'enregistrer le résultat
				if(note<0.4)
					listRes.add(new Resultat(result.getInt("id_materiel"), note));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return listRes;
	}
	
	public int levenshtein(String s0, String s1) {
		int len0 = s0.length()+1;
		int len1 = s1.length()+1;
	 
		// les tableaux de distances
		int[] cost = new int[len0];
		int[] newcost = new int[len0];
	 
		// initial cost of skipping prefix in String s0
		for(int i=0;i<len0;i++) cost[i]=i;
	 
		// dynamicaly computing the array of distances
	 
		// transformation cost for each letter in s1
		for(int j=1;j<len1;j++) {
	 
			// initial cost of skipping prefix in String s1
			newcost[0]=j-1;
	 
			// transformation cost for each letter in s0
			for(int i=1;i<len0;i++) {
	 
				// matching current letters in both strings
				int match = (s0.charAt(i-1)==s1.charAt(j-1))?0:1;
	 
				// computing cost for each transformation
				int cost_replace = cost[i-1]+match;
				int cost_insert  = cost[i]+1;
				int cost_delete  = newcost[i-1]+1;
	 
				// keep minimum cost
				newcost[i] = min(cost_insert, cost_delete, cost_replace);
			}
	 
			// swap cost/newcost arrays
			int[] swap=cost; cost=newcost; newcost=swap;
		}
	 
		// the distance is the cost for transforming all letters in both strings
		return cost[len0-1];
	}
	
	private int min(int ci, int cd, int cr){
		int min=Integer.MAX_VALUE;
		int[] tab = {ci,cd,cr};
		for(int i=0; i<tab.length; i++)
			if(tab[i]<min)
				min = tab[i];
		
		return min;
	}

}
