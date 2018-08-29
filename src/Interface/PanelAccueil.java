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
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import DAO.ClientDAO;
import Model.Client;
import Model.Materiel;
import Model.ResBrut;
import Model.ResFinal;
//import Model.Resultat;
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
		
		panelNorth.add(jtfRechercheClient);
		jtfRechercheClient.setColumns(10);
		
		btnRechercheClient = new JButton("Recherche Client");
		panelNorth.add(btnRechercheClient);
		btnRechercheClient.addActionListener(this);
		
		jtfRechercheOutil = new JTextField();
		jtfRechercheOutil.setText("ex : marque, num\u00E9ro de s\u00E9rie");
		panelNorth.add(jtfRechercheOutil);
		jtfRechercheOutil.setColumns(10);
		
		btnRechercheOutil = new JButton("Recherche Outil");
		panelNorth.add(btnRechercheOutil);
		btnRechercheOutil.addActionListener(this);
		
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
			
		}/*
		if(e.getSource()==btnRechercheOutil){
			String rechercheO = jtfRechercheOutil.getText();
			ArrayList<Materiel> listMateriel = createListMat(rechercheO);
		}*/
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
		ArrayList<ResBrut> listRes1 = new ArrayList<>();
		ArrayList<ResBrut> listRes2 = new ArrayList<>();
		
		//on récupère les mots séparés par un espace dans la barre de recherche dans listMot
		ArrayList<String> listMot = createListMot(recherche);
		
		//On déclare les champs qu'on va rechercher dans la base de données dans ce tableau
		String[] champs = {"nom", "prenom", "tel", "adresse"};
		
		//On parcours tous les mots taper dans la liste de recherche
		for(String mot : listMot){
			for(int i=0; i<champs.length; i++){
				listRes2 = cDAO.getResultat(mot, champs[i]);
				if (listRes2!=null){
					listRes1=append(listRes1, listRes2);
				}
			}
		}
		return classement(listRes1);
	}
	
	/**
	 * Permet d'assembler les élément de listRes2 qui ne sont pas dans listRes1
	 * @param listRes1
	 * @param listRes2
	 * @return listRes1
	 */
	private ArrayList<ResBrut> append(ArrayList<ResBrut> listRes1, ArrayList<ResBrut> listRes2){
		for(ResBrut res2 : listRes2)
			if(!listRes1.contains(res2))
				listRes1.add(res2);	
		return listRes1;		
	}
	
	/**
	 * Cette méthode permet de classer les résultats par pertinence. 
	 * Lorsque plusieurs mots ont "matché" ils sont mis en avant.
	 * @param listResBrut liste des résultat "brut" mots par mots
	 * @return listClient la liste client trier par pertinence
	 */
	private ArrayList<Client> classement(ArrayList<ResBrut> listResBrut){
		ArrayList<Integer> IdRes = new ArrayList<>();
		ArrayList<Integer> MultipleId = new ArrayList<>();
		ArrayList<ResFinal> listResFinal = new ArrayList<>();
		ArrayList<ResBrut> listResToDelete = new ArrayList<>();
		ArrayList<Client> listClient = new ArrayList<>();
		
		int id=0;
		for(ResBrut res : listResBrut){
			id = res.getId();
			if(IdRes.contains(id) && !MultipleId.contains(id))
				MultipleId.add(id);
			else
				IdRes.add(id);
		}
		
		double note = 0.0;
		for(int mid : MultipleId){
			for(ResBrut res : listResBrut){
				if(res.getId()==mid){
					note+=res.getNote();
					listResToDelete.add(res);
				}
			}
			listResFinal.add(new ResFinal(mid,note));
			note=1;
		}
		
		listResBrut = delete(listResBrut, listResToDelete);
		
		Comparator<ResFinal> comparatorF = Comparator.comparing(ResFinal::getNote);
		listResFinal.sort(comparatorF);
		Comparator<ResBrut> comparatorB = Comparator.comparing(ResBrut::getNote);
		listResBrut.sort(comparatorB);
		
		for(ResFinal rf : listResFinal)
			listClient.add(cDAO.find(rf.getId()));
		for(ResBrut rb : listResBrut)
			listClient.add(cDAO.find(rb.getId()));
		
		return listClient;
	}
	
	private ArrayList<ResBrut> delete(ArrayList<ResBrut> listResBrut, ArrayList<ResBrut> listResToDelete){
		for(ResBrut rb : listResToDelete)
			listResBrut.remove(rb);
		return listResBrut;
	}
	/*
	private ArrayList<Client> researchPlus(ArrayList<String> listMot, ArrayList<Resultat> listRes, String[] champs) {
		ArrayList<Client> listClient = new ArrayList<>();

		for(Resultat res : listRes){
			Client c = cDAO.find(res.getId());
			if(matchInfo(listMot, c, res.getChamps()))
				listClient.add(c);
			
		}
		
		return listClient;
	}*/

	/*
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
	*/
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
