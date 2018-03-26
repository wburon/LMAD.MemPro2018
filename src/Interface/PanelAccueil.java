package Interface;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JButton;

public class PanelAccueil extends JPanel {
	private JTable table;
	private JButton btnRecherche;
	private JButton btnPlanning;

	/**
	 * Create the panel.
	 */
	public PanelAccueil() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		btnRecherche = new JButton("Recherche");
		panelNorth.add(btnRecherche);
		
		btnPlanning = new JButton("Planning");
		panelNorth.add(btnPlanning);
		
		JPanel panelWest = new JPanel();
		add(panelWest, BorderLayout.WEST);
		
		JPanel panelSouth = new JPanel();
		add(panelSouth, BorderLayout.SOUTH);
		
		JPanel panelEast = new JPanel();
		add(panelEast, BorderLayout.EAST);
		
		JPanel panelCenter = new JPanel();
		add(panelCenter, BorderLayout.CENTER);
		
		table = new JTable();
		panelCenter.add(table);
		
		//Il me faut la liste Client, un bouton recherche, un bouton planning

	}

}
