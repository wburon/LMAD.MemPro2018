package Interface;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import Model.Table_Client;

public class PanelAccueil extends JPanel {
	private Table_Client tClient;
	private JTable table;
	
	private JButton btnRecherche;
	private JButton btnPlanning;
	private JTextField jtfRecherche;

	/**
	 * Create the panel.
	 */
	public PanelAccueil() {
		
		tClient = new Table_Client();
		
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		jtfRecherche = new JTextField();
		jtfRecherche.setText("ex : nom, pr\u00E9nom ou lieu");
		jtfRecherche.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				jtfRecherche.setText("");
			}
			});
		
		btnRecherche = new JButton("Recherche");
		panelNorth.add(btnRecherche);
		panelNorth.add(jtfRecherche);
		jtfRecherche.setColumns(10);
		
		btnPlanning = new JButton("Planning");
		panelNorth.add(btnPlanning);
		
		JButton btnAjoutClient = new JButton("Ajout Client");
		panelNorth.add(btnAjoutClient);
		
		JPanel panelWest = new JPanel();
		add(panelWest, BorderLayout.WEST);
		
		JPanel panelSouth = new JPanel();
		add(panelSouth, BorderLayout.SOUTH);
		
		JPanel panelEast = new JPanel();
		add(panelEast, BorderLayout.EAST);
		
		JPanel panelCenter = new JPanel();
		add(panelCenter, BorderLayout.CENTER);
		
		table = new JTable(tClient);
		panelCenter.add(table);
		//Il me faut la liste Client, un bouton recherche, un bouton planning

	}

}
