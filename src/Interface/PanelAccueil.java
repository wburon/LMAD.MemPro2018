package Interface;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	private JButton btnAjoutClient;
	private JTextField jtfRecherche;
	
	private PanelResultat panelRes;
	private FrameAjoutClient fAC;
	private MainFrame mf;

	private ClientDAO cDAO;
	/**
	 * Create the panel.
	 */
	public PanelAccueil(MainFrame mf) {
		this.mf=mf;
		
		
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
		btnRecherche.addActionListener(this);
		
		panelNorth.add(jtfRecherche);
		jtfRecherche.setColumns(10);
		
		btnPlanning = new JButton("Planning");
		panelNorth.add(btnPlanning);
		btnPlanning.addActionListener(this);
		
		btnAjoutClient = new JButton("Ajout Client");
		panelNorth.add(btnAjoutClient);
		btnAjoutClient.addActionListener(this);
		
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
		panelCenter.add(new JScrollPane(table));
		
		

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==btnRecherche){
			String recherche = jtfRecherche.getText();
			LancerRecherche(recherche);
			mf.setContentPane(panelRes);
			mf.repaint();
			mf.revalidate();
			
		}
		if(e.getSource()==btnAjoutClient){
			fAC = new FrameAjoutClient();
			fAC.setVisible(true);
		}
		
	}

	private void LancerRecherche(String recherche) {
		//lancer la recherche en ouvrant le panel recherche 
		ArrayList<Client> listClient = createListClient(recherche);
		panelRes = new PanelResultat(listClient);
		
	}

	private ArrayList<Client> createListClient(String recherche) {
		int len=recherche.length();
		char c=' ';
		String mot="";
		ArrayList<String> listMot=new ArrayList<>();
		for(int i=0; i<len; i++){
			if(recherche.charAt(i)==c){
				listMot.add(mot);
				mot="";
			}
			else if(i==len-1){
				mot+=recherche.charAt(i);
				listMot.add(mot);
			}
			else
				mot+=recherche.charAt(i);
		}
		return cDAO.research(listMot);
	}

}
