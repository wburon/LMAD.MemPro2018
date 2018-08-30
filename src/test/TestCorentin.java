package test;

import java.util.ArrayList;

import DAO.ClientDAO;
import DAO.MaterielDAO;
import Model.Client;
import Model.ResBrut;

public class TestCorentin {

	public static void main (String[] args){
		ClientDAO cDAO = new ClientDAO();
		MaterielDAO mDAO = new MaterielDAO();
		Client c = new Client();
		
		ArrayList<ResBrut> listResBrut1 = new ArrayList<>();
		ArrayList<ResBrut> listResBrut2 = new ArrayList<>();
		ArrayList<Integer> listNb = new ArrayList<>();
		
		String[] champs = {"nom" , "type", "marque"}; 
		String mot = "tondeuse";
		for(int i=0; i<champs.length; i++){
			listResBrut1 = mDAO.getResultat(mot,champs[i]);
			System.out.println(listResBrut1.get(0).getChamps());
		}
		
	}
}
