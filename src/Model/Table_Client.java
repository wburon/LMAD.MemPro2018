package Model;

import javax.swing.table.AbstractTableModel;

public class Table_Client extends AbstractTableModel{
	
	private final String[] entete={"nom","prenom","adresse","téléphone"}; 
	
	public Table_Client (){
		
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
