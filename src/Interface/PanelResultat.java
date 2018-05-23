package Interface;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Model.Client;
import Model.Table_Client;

import java.awt.BorderLayout;
import java.util.ArrayList;

public class PanelResultat extends JPanel{
	
	private Table_Client tClient;
	private JTable table;
	
	public PanelResultat(ArrayList<Client> listClient) {
		
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_north = new JPanel();
		add(panel_north, BorderLayout.NORTH);
		
		JPanel panel_west = new JPanel();
		add(panel_west, BorderLayout.WEST);
		
		JPanel panel_south = new JPanel();
		add(panel_south, BorderLayout.SOUTH);
		
		JPanel panel_east = new JPanel();
		add(panel_east, BorderLayout.EAST);
		
		JPanel panel_center = new JPanel();
		add(panel_center, BorderLayout.CENTER);
		
		tClient = new Table_Client(listClient);
		table = new JTable(tClient);
		panel_center.add(new JScrollPane(table));
		
	}

}
