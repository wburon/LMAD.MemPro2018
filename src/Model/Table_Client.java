package Model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import DAO.ClientDAO;

/**
 * Classe permettant de créer la liste client 
 * J'envisage de la renommer en Table_Client parce que ça va être totalement du résultat de recherche.
 * @author Baron
 *
 */
public class Table_Client extends AbstractTableModel{
	
	private ArrayList<Client> listClient = new ArrayList<>();
	
	private final String[] entete={"nom","prenom","adresse","téléphone"}; 
	
	public Table_Client (){
		ClientDAO cDAO = new ClientDAO();
		listClient = cDAO.getListAccueil();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
