package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.jsoup.internal.Normalizer;

import Singleton.SingletonConnection;
import Model.Client;
import Model.Materiel;
import Model.Methode;
import Model.Resultat;

public class ClientDAO extends DAO<Client>{
	Connection SC = SingletonConnection.getConnection();

	@Override
	public boolean create(Client obj) {
		try {

			PreparedStatement prepare = SC
					.prepareStatement("Insert into \"Client\"(id_client,nom,prenom,adresse,ville,tel,gps,courriel) values (?,?,?,?,?,?,?);");
			prepare.setInt(1, maxId());
			prepare.setString(2, obj.getNom());
			prepare.setString(3, obj.getPrenom());
			String adresse = obj.getAdresse();
			prepare.setString(4, adresse);
			String ville = obj.getVille();
			prepare.setString(5, ville);
			prepare.setInt(6, obj.getTel());
			String adresse_complete = adresse+" "+ville;
			String gps = Methode.getGPSCoord(adresse_complete);
			prepare.setString(7, gps);
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
				lev=levenshtein(mot,motTest);
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
	
	public int levenshtein(String s0, String s1) {
		s0.toLowerCase();
		s1.toLowerCase();
		
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
