package Interface;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import DAO.ClientDAO;
import Model.Client;
import Model.Materiel;
import Model.Resultat;
import Model.Table_Client;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PanelAccueil extends JPanel implements ActionListener, MouseListener{
	private Table_Client tClient;
	private JTable table;
	
	private JButton btnRechercheClient;
	private JButton btnRechercheOutil;
	private JButton btnPlanning;
	private JButton btnAjoutClient;
	private JTextField jtfRechercheClient;
	
	private PanelResultat panelRes;
	private PanelPlanning panelPlan;
	private FrameAjoutClient fAC;
	private MainFrame mf;

	private ClientDAO cDAO;
	private JTextField jtfRechercheOutil;
	/**
	 * Create the panel.
	 */
	public PanelAccueil(MainFrame mf) {
		this.mf=mf;
		
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		jtfRechercheClient = new JTextField();
		jtfRechercheClient.setText("ex : nom, pr\u00E9nom ou lieu");
		jtfRechercheClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				jtfRechercheClient.setText("");
			}
			});
		
		btnRechercheClient = new JButton("Recherche Client");
		panelNorth.add(btnRechercheClient);
		btnRechercheClient.addActionListener(this);
		
		panelNorth.add(jtfRechercheClient);
		jtfRechercheClient.setColumns(10);
		
		btnRechercheOutil = new JButton("Recherche Outil");
		panelNorth.add(btnRechercheOutil);
		btnRechercheOutil.addActionListener(this);
		
		jtfRechercheOutil = new JTextField();
		jtfRechercheOutil.setText("ex : marque, num\u00E9ro de s\u00E9rie");
		panelNorth.add(jtfRechercheOutil);
		jtfRechercheOutil.setColumns(10);
		
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
		
		JLabel lblConsigne = new JLabel("Vous voici sur la page d'accueil du logiciel \" \" \n si vous souhaitez voir client pr\u00E9sent dans la liste double-cliquez dessus.");
		panelSouth.add(lblConsigne);
		
		JPanel panelEast = new JPanel();
		add(panelEast, BorderLayout.EAST);
		
		JPanel panelCenter = new JPanel();
		add(panelCenter, BorderLayout.CENTER);
		

		cDAO = new ClientDAO();
		tClient = new Table_Client(cDAO.getListAccueil());
		table = new JTable(tClient);
		panelCenter.add(new JScrollPane(table));
		table.addMouseListener(this);
		

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==btnRechercheClient){
			String rechercheC = jtfRechercheClient.getText();
			ArrayList<Client> listClient = createListClient(rechercheC);
			panelRes = new PanelResultat(listClient, mf);
			mf.changePanel(panelRes);
			
		}
		if(e.getSource()==btnRechercheOutil){
			String rechercheO = jtfRechercheOutil.getText();
			ArrayList<Materiel> listMateriel = createListMat(rechercheO);
		}
		if(e.getSource()==btnAjoutClient){
			fAC = new FrameAjoutClient();
			fAC.setVisible(true);
		}
		if(e.getSource()==btnPlanning){
			panelPlan = new PanelPlanning();
			mf.changePanel(panelPlan);
		}
		
	}


	/**
	 * Cette méthode est appelé lorsqu'on appuie sur le bouton recherche,
	 * pour créer la liste de client susceptible de répondre aux critères recherchés
	 * @param recherche un String regroupant tout les éléments taper dans la barre de recherche
	 * @return la liste de client qui ont une correspondance avec la recherche
	 */
	private ArrayList<Client> createListClient(String recherche) {
		ArrayList<Client> listClient = new ArrayList<>();
		ArrayList<Resultat> listRes = new ArrayList<>();
		ArrayList<String> listMot = createListMot(recherche);
		
		String MotDepart=listMot.get(0);
		String[] champs = {"nom", "prenom", "tel", "adresse"};
		for(int i=0; i<champs.length; i++){
			if (cDAO.getResultat(MotDepart, champs[i])!=null){
				//concaténer les listes
			}
				
		}
		if(listMot.size()>1)
			return researchPlus(listMot, listRes, champs);
		else
			return listClient;
	}
	
	private ArrayList<Client> researchPlus(ArrayList<String> listMot, ArrayList<Resultat> listRes, String[] champs) {
		ArrayList<Client> listClient = new ArrayList<>();

		for(Resultat res : listRes){
			Client c = cDAO.find(res.getId());
			if(matchInfo(listMot, c, res.getChamps()))
				listClient.add(c);
			
		}
		
		return listClient;
	}

	private boolean matchInfo(ArrayList<String> listMot, Client c, String champs){
		switch(champs){
		case "prenom":
			for(int i=1; i<listMot.size(); i++){
				
			}
		}
		return false;
	}
	private ArrayList<Materiel> createListMat(String recherche) {
		
		
		return null;
	}
	
	private ArrayList<String> createListMot(String chaine){
		int len=chaine.length();
		char c=' ';
		String mot="";
		ArrayList<String> listMot=new ArrayList<>();
		for(int i=0; i<len; i++){
			if(chaine.charAt(i)==c){
				listMot.add(mot);
				mot="";
			}
			else if(i==len-1){
				mot+=chaine.charAt(i);
				listMot.add(mot);
			}
			else
				mot+=chaine.charAt(i);
		}
		return listMot;
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getClickCount()==2){
			mf.setClient(tClient.getClient(table.getSelectedRow()));
			System.out.println(mf.getClient().getNom());
			mf.changePanel(new PanelClient(mf));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
