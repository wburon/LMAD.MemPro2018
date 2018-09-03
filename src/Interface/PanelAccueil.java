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
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import DAO.ClientDAO;
import DAO.MaterielDAO;
import Model.Client;
import Model.Materiel;
import Model.Resultat;
import Model.Table_Client;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PanelAccueil extends JPanel implements ActionListener, MouseListener{
	
	private Table_Client tClient; private JTable table;
		
	private JButton btnRechercheClient, btnRechercheOutil, btnPlanning, btnAjoutClient;
	
	private JTextField jtfRechercheClient, jtfRechercheOutil;
	
	private PanelResultat panelRes; private PanelPlanning panelPlan; 
	private FrameAjoutClient fAC; private MainFrame mf;

	private ClientDAO cDAO; private MaterielDAO mDAO;
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
		//efface les propositions quand on clique sur la barre de recherche
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
		//Idem que pour la recherche client
		jtfRechercheOutil.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				jtfRechercheOutil.setText("");
			}
		});
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
		
		JLabel lblConsigne = new JLabel("Vous voici sur la page d'accueil du logiciel \" \" \n si vous souhaitez voir un client pr\u00E9sent dans la liste double-cliquez dessus.");
		panelSouth.add(lblConsigne);
		
		JPanel panelEast = new JPanel();
		add(panelEast, BorderLayout.EAST);
		
		JPanel panelCenter = new JPanel();
		add(panelCenter, BorderLayout.CENTER);
		

		cDAO = new ClientDAO(); mDAO = new MaterielDAO();
		tClient = new Table_Client(cDAO.getListAccueil()); table = new JTable(tClient);
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
			panelRes = new PanelResultat(listMateriel, mf);
			mf.changePanel(panelRes);
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
	 * Cette méthode est appelé lorsqu'on appuie sur le bouton recherche Client,
	 * pour créer la liste de client susceptible de répondre aux critères recherchés
	 * @param recherche un String regroupant tout les éléments taper dans la barre de recherche
	 * @return la liste de client qui ont une correspondance avec la recherche
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Client> createListClient(String recherche) {
		ArrayList<Resultat> listRes1 = new ArrayList<>();
		ArrayList<Resultat> listRes2 = new ArrayList<>();
		
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
		return (ArrayList<Client>) classement(listRes1, cDAO);
	}
	
	/**
	 * Cette méthode est appelé lorsqu'on appuie sur le bouton recherche Outil,
	 * pour créer la liste de Matériel susceptible de répondre aux critères recherchés
	 * @param recherche un String regroupant tout les éléments taper dans la barre de recherche
	 * @return la liste de Matériel qui ont une correspondance avec la recherche
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Materiel> createListMat(String recherche) {
		ArrayList<Resultat> listRes1 = new ArrayList<>();
		ArrayList<Resultat> listRes2 = new ArrayList<>();
		
		//on récupère les mots séparés par un espace dans la barre de recherche dans listMot
		ArrayList<String> listMot = createListMot(recherche);
		
		//On déclare les champs qu'on va rechercher dans la base de données dans ce tableau
		String[] champs = {"nom", "type", "marque"};
		
		//On parcours tous les mots taper dans la liste de recherche
		for(String mot : listMot){
			for(int i=0; i<champs.length; i++){
				
				listRes2 = mDAO.getResultat(mot, champs[i]);
				if (listRes2!=null){
					listRes1=append(listRes1, listRes2);
				}
			}
		}
		
		return (ArrayList<Materiel>) classement(listRes1,mDAO);
	}
	
	/**
	 * Permet d'assembler les élément de listRes2 qui ne sont pas dans listRes1
	 * @param listRes1
	 * @param listRes2
	 * @return listRes1
	 */
	private ArrayList<Resultat> append(ArrayList<Resultat> listRes1, ArrayList<Resultat> listRes2){
		for(Resultat res2 : listRes2)
			if(!listRes1.contains(res2))
				listRes1.add(res2);	
		return listRes1;		
	}
	
	/**
	 * Cette méthode permet de classer les résultats par pertinence. 
	 * Lorsque plusieurs mots ont "matché" ils sont mis en avant.
	 * @param listResBrut liste des résultat "brut" mots par mots
	 * @return listClient la liste trier par pertinence
	 */
	private ArrayList<?> classement(ArrayList<Resultat> listResBrut, Object o){
		ArrayList<Integer> IdRes = new ArrayList<>();
		ArrayList<Integer> MultipleId = new ArrayList<>();
		ArrayList<Resultat> listResFinal = new ArrayList<>();
		ArrayList<Resultat> listResToDelete = new ArrayList<>();
		ArrayList<Client> listClient = new ArrayList<>();
		ArrayList<Materiel> listMateriel = new ArrayList<>();
		
		int id=0;
		for(Resultat res : listResBrut){
			id = res.getId();
			if(IdRes.contains(id) && !MultipleId.contains(id))
				MultipleId.add(id);
			else
				IdRes.add(id);
		}
		
		double note = 0.0;
		for(int mid : MultipleId){
			for(Resultat res : listResBrut){
				if(res.getId()==mid){
					note+=res.getNote();
					listResToDelete.add(res);
				}
			}
			listResFinal.add(new Resultat(mid,note));
			note=1;
		}
		
		listResBrut = delete(listResBrut, listResToDelete);
		
		Comparator<Resultat> comparatorF = Comparator.comparing(Resultat::getNote);
		listResFinal.sort(comparatorF);
		Comparator<Resultat> comparatorB = Comparator.comparing(Resultat::getNote);
		listResBrut.sort(comparatorB);
		
		
		if(o.getClass().getName()=="DAO.ClientDAO"){
			for(Resultat rf : listResFinal)
				listClient.add(cDAO.find(rf.getId()));
			for(Resultat rb : listResBrut)
				listClient.add(cDAO.find(rb.getId()));
		
			return listClient;
		}else{
			for(Resultat rf : listResFinal)
				listMateriel.add(mDAO.find(rf.getId()));
			for(Resultat rb : listResBrut)
				listMateriel.add(mDAO.find(rb.getId()));
			
			return listMateriel;
		}
	}
	
	private ArrayList<Resultat> delete(ArrayList<Resultat> listResBrut, ArrayList<Resultat> listResToDelete){
		for(Resultat rb : listResToDelete)
			listResBrut.remove(rb);
		return listResBrut;
	}	
	
	/**
	 * Cette méthode permet d'implémenter dans une liste les mots séparés par un espace dans une chaine de caractere 
	 * @param chaine la chaine de caractère dont on veut séparer les mots
	 * @return une ArrayList<String> des mots séparés
	 */
	private ArrayList<String> createListMot(String chaine){
		int len=chaine.length();
		char c=' ';
		String mot="";
		ArrayList<String> listMot=new ArrayList<>();
		for(int i=0; i<len; i++){
			//si le caractère est un espace le mot se termine et on commence un nouveau mot
			if(chaine.charAt(i)==c){
				listMot.add(mot);
				mot="";
			}
			//si on est arrivé au dernier caractère le mot se termine
			else if(i==len-1){
				mot+=chaine.charAt(i);
				listMot.add(mot);
			}
			//on ajoute le caractère au mot en cours
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
