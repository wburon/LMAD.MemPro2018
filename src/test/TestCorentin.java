package test;

import java.text.Normalizer;
import java.util.ArrayList;

import DAO.ClientDAO;
import DAO.MaterielDAO;
import Model.Client;
import Model.Resultat;

public class TestCorentin {

	public static void main (String[] args){
		ClientDAO cDAO = new ClientDAO();
		MaterielDAO mDAO = new MaterielDAO();
		Client c = new Client();
		
		ArrayList<Resultat> listResBrut1 = new ArrayList<>();
		ArrayList<Resultat> listResBrut2 = new ArrayList<>();
		ArrayList<Integer> listNb = new ArrayList<>();
		
		String test = "Jean-Marie";
		
		test = test.toLowerCase();
		test = Normalizer.normalize(test, Normalizer.Form.NFD);
		test = test.replaceAll("\\p{M}", "");
		System.out.println(test);
		
		
		
//		System.out.println(cDAO.levenshtein("William", "william"));
		
		
		
				
	}
}
