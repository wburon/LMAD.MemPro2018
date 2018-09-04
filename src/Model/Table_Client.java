package Model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


/**
 * Classe permettant de cr�er la liste client 
 * J'envisage de la renommer en Table_Client parce que �a va �tre totalement du r�sultat de recherche.
 * @author Baron
 *
 */
@SuppressWarnings("serial")
public class Table_Client extends AbstractTableModel{
	
	private ArrayList<Client> listClient = new ArrayList<>();
	
	private final String[] entete={"nom","prenom","adresse","ville","t�l�phone","mail"}; 
	
	public Table_Client (ArrayList<Client> listClient){
		this.listClient = listClient;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listClient.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return entete.length;
	}
	public String getColumnName(int columnIndex){
		return entete[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch(columnIndex){
		case 0:
			return listClient.get(rowIndex).getNom();
		case 1:
			return listClient.get(rowIndex).getPrenom();
		case 2:
			return listClient.get(rowIndex).getAdresse();
		case 3: 
			return listClient.get(rowIndex).getVille();
		case 4:
			return listClient.get(rowIndex).getTel();
		case 5: 
			return listClient.get(rowIndex).getMail();
		default:
			return null;
		}
	}
	public Client getClient(int i){
		return listClient.get(i);
	}

}
