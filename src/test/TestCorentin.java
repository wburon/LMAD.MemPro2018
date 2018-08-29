package test;

import java.util.ArrayList;

import DAO.ClientDAO;
import Model.Client;
import Model.ResBrut;

public class TestCorentin {

	public static void main (String[] args){
		ClientDAO cDAO = new ClientDAO();
		
		ArrayList<ResBrut> listResBrut = new ArrayList<>();
		ArrayList<Integer> listNb = new ArrayList<>();
		
		listNb.add(1);
		
		listResBrut.add(new ResBrut(1, "nom", 0.1));
		listResBrut.add(new ResBrut(1, "prenom", 0.14));
		double note = 0.0;
		int i=0;
		for(int x : listNb)
			for(ResBrut rb : listResBrut){
				if (rb.getId()==x){
					note+=rb.getNote();
				}
				i++;
			}
		System.out.println(i);
		
	}
}
