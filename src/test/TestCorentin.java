package test;

import java.util.ArrayList;

import DAO.ClientDAO;
import Model.Client;

public class TestCorentin {

	public static void main (String[] args){
		ClientDAO cDAO = new ClientDAO();
		ArrayList<Client> lc = new ArrayList<>();
		lc = cDAO.getListAccueil();
		Client c=lc.get(0);
		System.out.println(c.getNom());
	}
}
