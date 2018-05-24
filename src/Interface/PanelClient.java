package Interface;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;

import DAO.ClientDAO;
import DAO.MaterielDAO;
import Model.Materiel;

import Model.Client;

import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PanelClient extends JPanel implements ActionListener{

	// Champ de construction client
	private JLabel lblNom, lblPrenom, lblAdresse, lblVille, lblTelephone, lblMail;
	private JTextField jtfnom, jtfPrenom, jtfAdresse, jtfVille, jtfTelephone, jtfCourriel;
	private JLabel lblNomI, lblPrenomI, lblAdresseI, lblVilleI, lblTelephoneI, lblMailI;
	private JPanel panelInfoClient;
	private GridBagConstraints gbc_lblNomI, gbc_lblPrenomI, gbc_lblAdresseI, gbc_lblVilleI, gbc_lblTelephoneI, gbc_lblMailI;
	
	// Champ de construction matériel
	private JLabel lblNomMat, lblTypeMat, lblNumSerieMat;
	private JPanel panelMateriel;
	private JLabel lblNomMatI, lblTypeMatI, lblNumSerieI;
	private JTextField jtfNomMat, jtfTypeMat, jtfNumSerieMat;
	private GridBagConstraints gbc_lblNomMatI, gbc_lblTypeMatI, gbc_lblNumSerieI;
	
	// Button
	private JButton btnPrendreRdV, btnPreviousMat, btnNextMat, btnModification, btnAddMateriel, btnModifierCeMateriel;
	
	// Autres champs
	private ArrayList<Materiel> listMateriel;
	private int indiceMat;
	private ClientDAO clientDAO;
	private Client client;
	private MaterielDAO materielDAO;
	private Materiel materiel;
	private PanelAccueil panelAccueil;

	public Client getClient() {
		return createClient();
	}
	
	public Materiel getMateriel(){
		return this.listMateriel.get(this.indiceMat);
	}

	/**
	 * Create the panel.
	 */
	public PanelClient(PanelAccueil accueil) {
		this.panelAccueil = accueil;
		clientDAO = new ClientDAO();
		materielDAO = new MaterielDAO();
		listMateriel = clientDAO.getListMateriel(client);
		indiceMat = 0;
		this.client = accueil.getClient();
	
		
		setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panelGauche = new JPanel();
		add(panelGauche);
		panelGauche.setLayout(new BorderLayout(0, 0));
		
		JPanel panelGButton = new JPanel();
		panelGButton.setPreferredSize(new Dimension(10, 60));
		panelGauche.add(panelGButton, BorderLayout.SOUTH);
		
		btnModification = new JButton("Modification");
		btnModification.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelGButton.add(btnModification);
		
		panelInfoClient = new JPanel();
		panelGauche.add(panelInfoClient, BorderLayout.CENTER);
		GridBagLayout gbl_panelInfoClient = new GridBagLayout();
		gbl_panelInfoClient.columnWidths = new int[]{220, 220, 0};
		gbl_panelInfoClient.rowHeights = new int[]{60, 60, 60, 60, 60, 60, 0};
		gbl_panelInfoClient.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelInfoClient.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelInfoClient.setLayout(gbl_panelInfoClient);
		
		lblNom = new JLabel("Nom : ");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.fill = GridBagConstraints.BOTH;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panelInfoClient.add(lblNom, gbc_lblNom);
		
		lblNomI = new JLabel(""+client.getNom());
		gbc_lblNomI = new GridBagConstraints();
		gbc_lblNomI.fill = GridBagConstraints.BOTH;
		gbc_lblNomI.insets = new Insets(0, 0, 5, 0);
		gbc_lblNomI.gridx = 1;
		gbc_lblNomI.gridy = 0;
		panelInfoClient.add(lblNomI, gbc_lblNomI);
		
		lblPrenom = new JLabel("Pr\u00E9nom : ");
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.fill = GridBagConstraints.BOTH;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 0;
		gbc_lblPrenom.gridy = 1;
		panelInfoClient.add(lblPrenom, gbc_lblPrenom);
		
		lblPrenomI = new JLabel(""+client.getPrenom());
		gbc_lblPrenomI = new GridBagConstraints();
		gbc_lblPrenomI.fill = GridBagConstraints.BOTH;
		gbc_lblPrenomI.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrenomI.gridx = 1;
		gbc_lblPrenomI.gridy = 1;
		panelInfoClient.add(lblPrenomI, gbc_lblPrenomI);
		
		lblAdresse = new JLabel("Adresse : ");
		GridBagConstraints gbc_lblAdresse = new GridBagConstraints();
		gbc_lblAdresse.fill = GridBagConstraints.BOTH;
		gbc_lblAdresse.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdresse.gridx = 0;
		gbc_lblAdresse.gridy = 2;
		panelInfoClient.add(lblAdresse, gbc_lblAdresse);
		
		lblAdresseI = new JLabel(""+client.getAdresse());
		gbc_lblAdresseI = new GridBagConstraints();
		gbc_lblAdresseI.fill = GridBagConstraints.BOTH;
		gbc_lblAdresseI.insets = new Insets(0, 0, 5, 0);
		gbc_lblAdresseI.gridx = 1;
		gbc_lblAdresseI.gridy = 2;
		panelInfoClient.add(lblAdresseI, gbc_lblAdresseI);
		
		lblVille = new JLabel("Ville : ");
		GridBagConstraints gbc_lblVille = new GridBagConstraints();
		gbc_lblVille.fill = GridBagConstraints.BOTH;
		gbc_lblVille.insets = new Insets(0, 0, 5, 5);
		gbc_lblVille.gridx = 0;
		gbc_lblVille.gridy = 3;
		panelInfoClient.add(lblVille, gbc_lblVille);
		
		lblVilleI = new JLabel(""+client.getVille());
		gbc_lblVilleI = new GridBagConstraints();
		gbc_lblVilleI.fill = GridBagConstraints.BOTH;
		gbc_lblVilleI.insets = new Insets(0, 0, 5, 0);
		gbc_lblVilleI.gridx = 1;
		gbc_lblVilleI.gridy = 3;
		panelInfoClient.add(lblVilleI, gbc_lblVilleI);
		
		lblTelephone = new JLabel("T\u00E9l\u00E9phone : ");
		GridBagConstraints gbc_lblTelephone = new GridBagConstraints();
		gbc_lblTelephone.fill = GridBagConstraints.BOTH;
		gbc_lblTelephone.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelephone.gridx = 0;
		gbc_lblTelephone.gridy = 4;
		panelInfoClient.add(lblTelephone, gbc_lblTelephone);
		
		lblTelephoneI = new JLabel(""+client.getTel());
		gbc_lblTelephoneI = new GridBagConstraints();
		gbc_lblTelephoneI.fill = GridBagConstraints.BOTH;
		gbc_lblTelephoneI.insets = new Insets(0, 0, 5, 0);
		gbc_lblTelephoneI.gridx = 1;
		gbc_lblTelephoneI.gridy = 4;
		panelInfoClient.add(lblTelephoneI, gbc_lblTelephoneI);
		
		lblMail = new JLabel("Courriel : ");
		GridBagConstraints gbc_lblMail = new GridBagConstraints();
		gbc_lblMail.fill = GridBagConstraints.BOTH;
		gbc_lblMail.insets = new Insets(0, 0, 0, 5);
		gbc_lblMail.gridx = 0;
		gbc_lblMail.gridy = 5;
		panelInfoClient.add(lblMail, gbc_lblMail);
		
		lblMailI = new JLabel(""+client.getMail());
		gbc_lblMailI = new GridBagConstraints();
		gbc_lblMailI.fill = GridBagConstraints.BOTH;
		gbc_lblMailI.gridx = 1;
		gbc_lblMailI.gridy = 5;
		panelInfoClient.add(lblMailI, gbc_lblMailI);
		
		jtfnom = new JTextField();
		jtfnom.setText(""+client.getNom());
		jtfnom.setColumns(10);
		
		jtfPrenom = new JTextField();
		jtfPrenom.setText(""+client.getPrenom());
		jtfPrenom.setColumns(10);
		
		jtfAdresse = new JTextField();
		jtfAdresse.setText(""+client.getAdresse());
		jtfAdresse.setColumns(10);
		
		jtfVille = new JTextField();
		jtfVille.setText(""+client.getVille());
		jtfVille.setColumns(10);
		
		jtfTelephone = new JTextField();
		jtfTelephone.setText(""+client.getTel());
		jtfTelephone.setColumns(10);
		
		jtfCourriel = new JTextField();
		jtfCourriel.setText(""+client.getMail());
		jtfCourriel.setColumns(10);
		
		JPanel panelDescriptionClient = new JPanel();
		panelDescriptionClient.setPreferredSize(new Dimension(10, 40));
		panelGauche.add(panelDescriptionClient, BorderLayout.NORTH);
		
		JLabel lblInformationClient = new JLabel("INFORMATION CLIENT");
		lblInformationClient.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblInformationClient.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDescriptionClient.add(lblInformationClient);
		
		JPanel panelDroit = new JPanel();
		add(panelDroit);
		panelDroit.setLayout(new BorderLayout(0, 0));
		
		JPanel panelSudMateriel = new JPanel();
		panelSudMateriel.setPreferredSize(new Dimension(10, 120));
		panelDroit.add(panelSudMateriel, BorderLayout.SOUTH);
		panelSudMateriel.setLayout(new GridLayout(3, 1, 0, 0));
		
		btnModifierCeMateriel = new JButton("Modifier ce mat\u00E9riel");
		btnModifierCeMateriel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSudMateriel.add(btnModifierCeMateriel);
		
		btnAddMateriel = new JButton("Ajouter Mat\u00E9riel");
		btnAddMateriel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSudMateriel.add(btnAddMateriel);
		
		this.btnAddMateriel.addActionListener(this);
		
		btnPrendreRdV = new JButton("Prendre rendez-vous");
		btnPrendreRdV.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSudMateriel.add(btnPrendreRdV);
		
		JPanel panel_1 = new JPanel();
		panelDroit.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panelGaucheMat = new JPanel();
		panelGaucheMat.setPreferredSize(new Dimension(50, 10));
		panel_1.add(panelGaucheMat, BorderLayout.WEST);
		panelGaucheMat.setLayout(new BorderLayout(0, 0));
		
		btnPreviousMat = new JButton("<");
		btnPreviousMat.setPreferredSize(new Dimension(50, 30));
		btnPreviousMat.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPreviousMat.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelGaucheMat.add(btnPreviousMat);
		
		JPanel panelDroitMat = new JPanel();
		panelDroitMat.setPreferredSize(new Dimension(50, 10));
		panel_1.add(panelDroitMat, BorderLayout.EAST);
		panelDroitMat.setLayout(new BorderLayout(0, 0));
		
		btnNextMat = new JButton(">");
		btnNextMat.setPreferredSize(new Dimension(50, 30));
		panelDroitMat.add(btnNextMat, BorderLayout.CENTER);
		
		panelMateriel = new JPanel();
		panel_1.add(panelMateriel, BorderLayout.CENTER);
		GridBagLayout gbl_panelMateriel = new GridBagLayout();
		gbl_panelMateriel.columnWidths = new int[]{180, 180, 0};
		gbl_panelMateriel.rowHeights = new int[]{101, 101, 101, 0};
		gbl_panelMateriel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelMateriel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelMateriel.setLayout(gbl_panelMateriel);
		
		lblNomMat = new JLabel("Nom : ");
		GridBagConstraints gbc_lblNomMat = new GridBagConstraints();
		gbc_lblNomMat.fill = GridBagConstraints.BOTH;
		gbc_lblNomMat.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomMat.gridx = 0;
		gbc_lblNomMat.gridy = 0;
		panelMateriel.add(lblNomMat, gbc_lblNomMat);
		
		lblNomMatI = new JLabel(listMateriel.get(indiceMat).getNom());
		gbc_lblNomMatI = new GridBagConstraints();
		gbc_lblNomMatI.fill = GridBagConstraints.BOTH;
		gbc_lblNomMatI.insets = new Insets(0, 0, 5, 0);
		gbc_lblNomMatI.gridx = 1;
		gbc_lblNomMatI.gridy = 0;
		panelMateriel.add(lblNomMatI, gbc_lblNomMatI);
		
		lblTypeMat = new JLabel("Type : ");
		GridBagConstraints gbc_lblTypeMat = new GridBagConstraints();
		gbc_lblTypeMat.fill = GridBagConstraints.BOTH;
		gbc_lblTypeMat.insets = new Insets(0, 0, 5, 5);
		gbc_lblTypeMat.gridx = 0;
		gbc_lblTypeMat.gridy = 1;
		panelMateriel.add(lblTypeMat, gbc_lblTypeMat);
		
		lblTypeMatI = new JLabel(listMateriel.get(indiceMat).getType());
		gbc_lblTypeMatI = new GridBagConstraints();
		gbc_lblTypeMatI.fill = GridBagConstraints.BOTH;
		gbc_lblTypeMatI.insets = new Insets(0, 0, 5, 0);
		gbc_lblTypeMatI.gridx = 1;
		gbc_lblTypeMatI.gridy = 1;
		panelMateriel.add(lblTypeMatI, gbc_lblTypeMatI);
		
		lblNumSerieMat = new JLabel("Num\u00E9ro de s\u00E9rie : ");
		GridBagConstraints gbc_lblNumSerieMat = new GridBagConstraints();
		gbc_lblNumSerieMat.fill = GridBagConstraints.BOTH;
		gbc_lblNumSerieMat.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumSerieMat.gridx = 0;
		gbc_lblNumSerieMat.gridy = 2;
		panelMateriel.add(lblNumSerieMat, gbc_lblNumSerieMat);
		
		lblNumSerieI = new JLabel(listMateriel.get(indiceMat).getNumSerie());
		gbc_lblNumSerieI = new GridBagConstraints();
		gbc_lblNumSerieI.fill = GridBagConstraints.BOTH;
		gbc_lblNumSerieI.gridx = 1;
		gbc_lblNumSerieI.gridy = 2;
		panelMateriel.add(lblNumSerieI, gbc_lblNumSerieI);
		
		jtfNomMat = new JTextField();
		jtfNomMat.setText(listMateriel.get(indiceMat).getNom());
		jtfNomMat.setColumns(10);
		
		jtfTypeMat = new JTextField();
		jtfTypeMat.setText(listMateriel.get(indiceMat).getType());
		jtfTypeMat.setColumns(10);
		
		jtfNumSerieMat = new JTextField();
		jtfNumSerieMat.setText(listMateriel.get(indiceMat).getNumSerie());
		jtfNumSerieMat.setColumns(10);
		
		JPanel panelDescriptionMat = new JPanel();
		panelDescriptionMat.setPreferredSize(new Dimension(10, 40));
		panelDroit.add(panelDescriptionMat, BorderLayout.NORTH);
		
		JLabel lblMateriel = new JLabel("MATERIELS");
		lblMateriel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelDescriptionMat.add(lblMateriel);
		
		this.btnModification.addActionListener(this);
		this.btnNextMat.addActionListener(this);
		this.btnPrendreRdV.addActionListener(this);
		this.btnPreviousMat.addActionListener(this);
		this.btnModifierCeMateriel.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnNextMat){
			getIndiceMat("Next");
			updateMateriel();
		}else if(arg0.getSource() == btnPreviousMat){
			getIndiceMat("Prev");
			updateMateriel();
		}else if(arg0.getSource() == btnModification){
			if(lblNomI.isVisible()){
				changeVisibilityOfClient(false);
			}else{
				clientDAO.update(createClient());
				this.client = createClient();
				updatelblClient();
				changeVisibilityOfClient(true);
			}
		}else if(arg0.getSource() == btnPrendreRdV){
			this.panelAccueil.setActivePanel(new PanelRDV(this));
		}else if(arg0.getSource() == btnAddMateriel){
			JFrame fenAjoutMat = new FrameAjoutMateriel(client);
			fenAjoutMat.setVisible(true);
		}else if(arg0.getSource() == btnModifierCeMateriel){
			if(lblNomMatI.isVisible()){
				changeVisibilityOfMateriel(false);
			}else {
				materielDAO.update(createMaterielbyJtf());
				this.materiel = createMaterielbyJtf();
				updatelblMateriel();
				changeVisibilityOfMateriel(true);
			}
		}
		
	}

	/**
	 * Met a jour les labels informatifs du matériel à partir de listMateriel et indiceMat
	 */
	private void updateMateriel() {
		this.listMateriel = clientDAO.getListMateriel(client);
		lblNomMatI.setText(listMateriel.get(indiceMat).getNom());
		lblTypeMatI.setText(listMateriel.get(indiceMat).getType());
		lblNumSerieI.setText(listMateriel.get(indiceMat).getNumSerie());
		this.materiel = listMateriel.get(indiceMat);
	}

	/**
	 * Met à jour les labels informatifs du matériel lors qu'il y a eut une modification
	 */
	private void updatelblMateriel() {
		lblNomMatI.setText(jtfNomMat.getText());
		lblTypeMatI.setText(jtfTypeMat.getText());
		lblNumSerieI.setText(jtfNumSerieMat.getText());
	}

	/**
	 * Met à jour les labels informatifs du client lors qu'il y a eut une modification
	 */
	private void updatelblClient() {
		lblNomI.setText(jtfnom.getText());
		lblPrenomI.setText(jtfPrenom.getText());
		lblAdresseI.setText(jtfAdresse.getText());
		lblVilleI.setText(jtfVille.getText());
		lblTelephoneI.setText(jtfTelephone.getText());
		lblMailI.setText(jtfCourriel.getText());
	}

	/**
	 * creation d'un objet client a partir des infos des JTextField
	 * @return c : Client
	 */
	public Client createClient(){
		Client c = new Client();
		c.setId_client(this.client.getId_client());
		c.setNom(jtfnom.getText());
		c.setPrenom(jtfPrenom.getText());
		c.setAdresse(jtfAdresse.getText());
		c.setVille(jtfVille.getText());
		c.setTel(Integer.parseInt(jtfTelephone.getText()));
		c.setMail(jtfCourriel.getText());
		return c;
	}
	
	/**
	 * création d'un object Materiel à partir des JTextField
	 * @return mat : Materiel
	 */
	private Materiel createMaterielbyJtf() {
		Materiel mat = new Materiel();
		mat.setId_materiel(listMateriel.get(indiceMat).getId_materiel());
		mat.setNom(jtfNomMat.getText());
		mat.setNumSerie(jtfNumSerieMat.getText());
		mat.setType(jtfTypeMat.getText());
		mat.setClient(this.client);
		return mat;
	}
	
	/**
	 * switch panel client
	 * @param a
	 */
	public void changeVisibilityOfClient(boolean a){
		lblNomI.setVisible(a);
		lblPrenomI.setVisible(a);
		lblAdresseI.setVisible(a);
		lblVilleI.setVisible(a);
		lblTelephoneI.setVisible(a);
		lblMailI.setVisible(a);
		if(a){
			a = false;
		}else {
			a = true;
		}
		jtfnom.setVisible(a);
		panelInfoClient.add(jtfnom, gbc_lblNomI);
		jtfPrenom.setVisible(a);
		panelInfoClient.add(jtfPrenom, gbc_lblPrenomI);
		jtfAdresse.setVisible(a);
		panelInfoClient.add(jtfAdresse, gbc_lblAdresseI);
		jtfVille.setVisible(a);
		panelInfoClient.add(jtfVille, gbc_lblVilleI);
		jtfTelephone.setVisible(a);
		panelInfoClient.add(jtfTelephone, gbc_lblTelephoneI);
		jtfCourriel.setVisible(a);
		panelInfoClient.add(jtfCourriel, gbc_lblMailI);
		
	}
	
	/**
	 * switch panel materiel
	 * @param b
	 */
	private void changeVisibilityOfMateriel(boolean b) {
		lblNomMatI.setVisible(b);
		lblTypeMatI.setVisible(b);
		lblNumSerieI.setVisible(b);
		if(b){
			b = false;
		}else {
			b = true;
		}
		jtfNomMat.setVisible(b);
		panelMateriel.add(jtfNomMat, gbc_lblNomMatI);
		jtfTypeMat.setVisible(b);
		panelMateriel.add(jtfTypeMat, gbc_lblTypeMatI);
		jtfNumSerieMat.setVisible(b);
		panelMateriel.add(jtfNumSerieMat, gbc_lblNumSerieI);
	}
	
	/**
	 * réalise le changement de materiel (panel)
	 * @param a
	 */
	public void getIndiceMat(String a){
		switch(a){
		case "Next":
			if(this.indiceMat == listMateriel.size()-1)
				this.indiceMat = 0;
			else
				this.indiceMat++;
			break;
		case "Prev":
			if(this.indiceMat == 0)
				this.indiceMat = listMateriel.size()-1;
			else
				this.indiceMat--;
			break;
		default:
			System.out.println("Erreur : il n'est pas possible d'arrivé ici !");
		}
	}

}
