package Model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class Table_Materiel extends AbstractTableModel {

	private ArrayList<Materiel> listMat = new ArrayList<>();

	private final String[] entete = { "nom", "type", "marque", "numéro de série" };

	public Table_Materiel(ArrayList<Materiel> listMat) {
		this.listMat = listMat;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listMat.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return entete.length;
	}

	public String getColumnName(int columnIndex) {
		return entete[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
		case 0:
			return listMat.get(rowIndex).getNom();
		case 1:
			return listMat.get(rowIndex).getType();
		case 2:
			return listMat.get(rowIndex).getMarque();
		case 3:
			return listMat.get(rowIndex).getNumSerie();
		default:
			return null;
		}
		
	}

}
