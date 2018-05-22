package Interface;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import DAO.ClientDAO;
import Model.Client;
import Model.Table_Client;

public class PanelAccueil extends JPanel implements ActionListener{
	private Table_Client tClient;
	private JTable table;
	
	private JButton btnRecherche;
	private JButton btnPlanning;
	private JTextField jtfRecherche;
	
	private PanelResultat panelRes;

	private ClientDAO cDAO;
	/**
	 * Create the panel.
	 */
	public PanelAccueil() {
		
		
		
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
		

		cDAO = new ClientDAO();
		tClient = new Table_Client(cDAO.getListAccueil());
		table = new JTable(tClient);
		panelCenter.add(table);
		
		

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==btnRecherche){
			String recherche = jtfRecherche.getText();
			LancerRecherche(recherche);
		}
		
	}

	private void LancerRecherche(String recherche) {
		//lancer la recherche en ouvrant le panel recherche 
		ArrayList<Client> listClient = createListClient(recherche);
		panelRes = new PanelResultat();
		
	}

	private ArrayList<Client> createListClient(String recherche) {
		ArrayList<Client> listClient = new ArrayList<>();
		return listClient;
	}

}
