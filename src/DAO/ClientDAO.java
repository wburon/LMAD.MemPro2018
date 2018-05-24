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
	 * méthode pour rechercher la correspondance d'une liste de mot dans la base de données
	 * @param listMot une ArrayList de String avec les mots qu'on veut rechercher
	 * @return listClient une ArrayList de clients
	 */
	public ArrayList<Client> research(ArrayList<String> listMot){
		ArrayList<Client> listClient = new ArrayList<>();
		ArrayList<Client> listResearch = new ArrayList<>();
		String[] champs = {"nom", "prenom", "tel", "adresse"};
		Client obj = new Client();
		
		for(String s : listMot){
			for(int i=0; i<champs.length;i++){
				listResearch=research(s,champs[i]);
				System.out.println(champs[i]);
				if(!listResearch.isEmpty())
					listClient=append(listClient,listResearch);
			}
		}
		
		return listClient;
		
	}

	private ArrayList<Client> append(ArrayList<Client> listClient, ArrayList<Client> listResearch) {
		for(Client c:listResearch){
			if(!contain(listClient, c))
				listClient.add(c);
		}
		return listClient;
	}

	/**
	 * Méthode appeler par research(ArrayList<String> listMot)
	 * @param s le mot qu'on recherche
	 * @param champs la colonne où on veut chercher s
	 * @return la liste des résultats obtenus
	 */
	private ArrayList<Client> research(String s, String champs) {
		ArrayList<Client> listClient = new ArrayList<>();
		Statement state;
		Client obj = new Client();
		try {
			state = SC.createStatement();
			if(champs=="tel" && isInteger(s))
				state.executeQuery("SELECT * FROM \"public\".\"Client\" WHERE \""+champs+"\" = "+s);
			else if (champs != "tel")
				state.executeQuery("SELECT * FROM \"public\".\"Client\" WHERE \""+champs+"\" = '"+s+"'");
			else 
				return listClient;
			ResultSet result = state.getResultSet();
			while(result.next()){
				obj=find(result.getInt("id_client"));
				if(!contain(listClient, obj))
					listClient.add(obj);
			}
		} catch (SQLException e) {
			listClient=null;
			e.printStackTrace();
		}
		return listClient;
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
