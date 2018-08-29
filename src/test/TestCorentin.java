package test;

import java.util.ArrayList;

import DAO.ClientDAO;
import Model.Client;
import Model.ResBrut;

public class TestCorentin {

	public static void main (String[] args){
		ClientDAO cDAO = new ClientDAO();
		
		Client c = new Client();
		
		ArrayList<ResBrut> listResBrut1 = new ArrayList<>();
		ArrayList<ResBrut> listResBrut2 = new ArrayList<>();
		ArrayList<Integer> listNb = new ArrayList<>();
		
		if (c.getClass().getName()=="Model.Client")
			System.out.println(c.getClass().getSimpleName());
		
		
		
	}
}
