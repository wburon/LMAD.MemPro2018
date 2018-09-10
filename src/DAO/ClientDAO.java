package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import Singleton.SingletonConnection;
import Model.Client;
import Model.Materiel;
import Model.Methode;
import Model.Resultat;

public class ClientDAO extends DAO<Client>{
	Connection SC = SingletonConnection.getConnection();
	private Methode met = new Methode();

	@Override
	public boolean create(Client obj) {
		try {

			PreparedStatement prepare = SC
					.prepareStatement("Insert into \"Client\"(id_client,nom,prenom,adresse,ville,tel,gps,courriel) values (?,?,?,?,?,?,?,?);");
			prepare.setInt(1, maxId());
			prepare.setString(2, obj.getNom());
			prepare.setString(3, obj.getPrenom());
			String adresse = obj.getAdresse();
			prepare.setString(4, adresse);
			String ville = obj.getVille();
			prepare.setString(5, ville);
			prepare.setInt(6, obj.getTel());
			prepare.setString(7, null);
			prepare.setString(8, obj.getMail());
			
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
	
	/**
	 * Renvoie la liste de matériel d'un client
	 * @param client
	 * @return
	 */
	public ArrayList<Materiel> getListMateriel(Client client){
		int id = client.getId_client();
		ArrayList<Materiel> listMat = new ArrayList<>();
		try {
			PreparedStatement prepare = SC.prepareStatement("SELECT * FROM \"Materiel\" WHERE id_client = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();
			
			while(result.next()){
				listMat.add(Methode.formulateMateriel(result));
			}
					
		}catch(SQLException e){
			e.printStackTrace();
		}
		return listMat;
	}
	
	/**
	 * Renvoie la liste de tous les clients présents dans la base de données
	 * @return ArrayList<Client> listClient
	 */
	public ArrayList<Client> getListAccueil(){
		ArrayList<Client> listClient = new ArrayList<>();
		Statement state;
		Client obj = new Client();
		try{
			state = SC.createStatement();
			state.executeQuery("SELECT * FROM \"public\".\"Client\" ");
			ResultSet result = state.getResultSet();
			
			while(result.next()){
				obj = find(result.getInt("id_client"));
				listClient.add(obj);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return listClient;
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
			state.executeQuery("SELECT \"id_client\",\""+champs+"\" FROM \"public\".\"Client\"");
			ResultSet result=state.getResultSet();
			
			int lev;double note;
			
			//On parcours l'ensemble des mots de la colonne associé au String champs pour trouver une correspondance avec le mot recherché
			while(result.next()){
				String motTest = result.getString(champs);
				//La distance de Levenshtein permet de mesurer le nombre d'opérations pour passer du motTest au mot recherché
				lev=met.levenshtein(mot,motTest);
				//La note est la distance entre le motTest et le mot recherché sur la taille du mot recherché
				note = (double) lev/mot.length();
				//Si la note est inférieur à 0.4 on estime qu'il est pertinent d'enregistrer le résultat
				if(note<0.4)
					listRes.add(new Resultat(result.getInt("id_client"), note));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return listRes;
	}
	
	
	/**
	 * méthode récursive pour vérifier que la chaine de caractère est un entier
	 * @param s la chaine de caractère
	 * @return boolean
	 */
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) 
	            	return false;
	            else 
	            	continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) 
	        	return false;
	    }
	    return true;
	}

	public int maxId() {
		Statement state;
		int nbRow=0;
		try {
			state = SC.createStatement();
			ResultSet nbLigne = state.executeQuery("SELECT MAX(id_client) FROM \"public\".\"Client\" ");
			nbLigne.next();
			nbRow = nbLigne.getInt(1) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbRow;
	}
	
	public boolean contain(ArrayList<Client> listClient, Client c1){
		for(Client c2 : listClient){
			if(c2.equals(c1))
				return true;
		}
		return false;
	}
}
