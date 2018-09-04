package Interface;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;

import DAO.ClientDAO;
import DAO.InterventionDAO;
import DAO.MaterielDAO;
import Model.Materiel;
import Model.Methode;
import Model.Client;

import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class PanelClient extends JPanel implements ActionListener {

	// Champ de construction client
	private JLabel lblNom, lblPrenom, lblAdresse, lblVille, lblTelephone, lblMail;
	private JTextField jtfnom, jtfPrenom, jtfAdresse, jtfVille, jtfTelephone, jtfCourriel;
	private JLabel lblNomI, lblPrenomI, lblAdresseI, lblVilleI, lblTelephoneI, lblMailI;
	private JPanel panelInfoClient;
	private GridBagConstraints gbc_lblNomI, gbc_lblPrenomI, gbc_lblAdresseI, gbc_lblVilleI, gbc_lblTelephoneI,
			gbc_lblMailI;

	// Champ de construction matériel
	private JPanel panelMateriel;
	private JLabel lblNomMatI, lblTypeMatI, lblNumSerieI, lblMarqueMatI, lblHistoriqueIntervention;
	private JTextField jtfNomMat, jtfTypeMat, jtfNumSerieMat, jtfMarqueMat;
	private GridBagConstraints gbc_lblNomMatI, gbc_lblTypeMatI, gbc_lblNumSerieI, gbc_lblMarqueMatI, gbc_lblHistorique;

	// Button
	private JButton btnPrendreRdV, btnModification, btnAddMateriel;
	private ArrayList<JButton> btnModifMateriel, btnVoirPlus, btnSupprMateriel;

	// Autres champs
	private ArrayList<Materiel> listMateriel;
	private int nbMatofThisClient;

	// DAO et model
	private ClientDAO clientDAO;
	private Client client;
	private MaterielDAO materielDAO;
	private InterventionDAO interventionDAO;

	// main frame
	private MainFrame mf;

	// Quelques getteurs utiles
	public MainFrame getMf() {
		return mf;
	}
	public Client getClient() {
		return createClient();
	}
	public int getNbMatofThisClient() {
		return nbMatofThisClient;
	}
	public ArrayList<Materiel> getListMateriel() {
		return listMateriel;
	}

	/**
	 * Create the panel.
	 */
	public PanelClient(MainFrame mf) {
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		this.mf = mf;
		this.client = mf.getClient();
		clientDAO = new ClientDAO();
		materielDAO = new MaterielDAO();
		listMateriel = clientDAO.getListMateriel(client);
		nbMatofThisClient = clientDAO.getListMateriel(this.client).size();
		interventionDAO = new InterventionDAO();
		btnVoirPlus = Methode.createButton(this.listMateriel.size(), "Voir Plus");
		btnModifMateriel = Methode.createButton(this.listMateriel.size(), "Modifier");
		btnSupprMateriel = Methode.createButton(this.listMateriel.size(), "Supprimer");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0 };
		setLayout(gridBagLayout);

		jtfnom = new JTextField();
		jtfnom.setText("" + client.getNom());
		jtfnom.setColumns(10);
		jtfnom.setVisible(false);

		jtfPrenom = new JTextField();
		jtfPrenom.setText("" + client.getPrenom());
		jtfPrenom.setColumns(10);

		jtfAdresse = new JTextField();
		jtfAdresse.setText("" + client.getAdresse());
		jtfAdresse.setColumns(10);

		jtfVille = new JTextField();
		jtfVille.setText("" + client.getVille());
		jtfVille.setColumns(10);

		jtfTelephone = new JTextField();
		jtfTelephone.setText("" + client.getTel());
		jtfTelephone.setColumns(10);

		jtfCourriel = new JTextField();
		jtfCourriel.setText("" + client.getMail());
		jtfCourriel.setColumns(10);

		JPanel panelGauche = new JPanel();
		panelGauche.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panelGauche = new GridBagConstraints();
		gbc_panelGauche.weighty = 100.0;
		gbc_panelGauche.weightx = 5.0;
		gbc_panelGauche.anchor = GridBagConstraints.LINE_START;
		gbc_panelGauche.fill = GridBagConstraints.BOTH;
		gbc_panelGauche.gridx = 0;
		gbc_panelGauche.gridy = 0;
		add(panelGauche, gbc_panelGauche);
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
		gbl_panelInfoClient.columnWidths = new int[] { 220, 220, 0 };
		gbl_panelInfoClient.rowHeights = new int[] { 60, 60, 60, 60, 60, 60, 0 };
		gbl_panelInfoClient.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelInfoClient.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelInfoClient.setLayout(gbl_panelInfoClient);

		lblNom = new JLabel("Nom : ");
		lblNom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.fill = GridBagConstraints.BOTH;
		gbc_lblNom.insets = new Insets(5, 5, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panelInfoClient.add(lblNom, gbc_lblNom);

		lblNomI = new JLabel("" + client.getNom());
		lblNomI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblNomI = new GridBagConstraints();
		gbc_lblNomI.fill = GridBagConstraints.BOTH;
		gbc_lblNomI.insets = new Insets(5, 5, 5, 5);
		gbc_lblNomI.gridx = 1;
		gbc_lblNomI.gridy = 0;
		panelInfoClient.add(lblNomI, gbc_lblNomI);

		lblPrenom = new JLabel("Pr\u00E9nom : ");
		lblPrenom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.fill = GridBagConstraints.BOTH;
		gbc_lblPrenom.insets = new Insets(5, 5, 5, 5);
		gbc_lblPrenom.gridx = 0;
		gbc_lblPrenom.gridy = 1;
		panelInfoClient.add(lblPrenom, gbc_lblPrenom);

		lblPrenomI = new JLabel("" + client.getPrenom());
		lblPrenomI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblPrenomI = new GridBagConstraints();
		gbc_lblPrenomI.fill = GridBagConstraints.BOTH;
		gbc_lblPrenomI.insets = new Insets(5, 5, 5, 5);
		gbc_lblPrenomI.gridx = 1;
		gbc_lblPrenomI.gridy = 1;
		panelInfoClient.add(lblPrenomI, gbc_lblPrenomI);

		lblAdresse = new JLabel("Adresse : ");
		lblAdresse.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblAdresse = new GridBagConstraints();
		gbc_lblAdresse.fill = GridBagConstraints.BOTH;
		gbc_lblAdresse.insets = new Insets(5, 5, 5, 5);
		gbc_lblAdresse.gridx = 0;
		gbc_lblAdresse.gridy = 2;
		panelInfoClient.add(lblAdresse, gbc_lblAdresse);

		lblAdresseI = new JLabel("" + client.getAdresse());
		lblAdresseI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblAdresseI = new GridBagConstraints();
		gbc_lblAdresseI.fill = GridBagConstraints.BOTH;
		gbc_lblAdresseI.insets = new Insets(5, 5, 5, 5);
		gbc_lblAdresseI.gridx = 1;
		gbc_lblAdresseI.gridy = 2;
		panelInfoClient.add(lblAdresseI, gbc_lblAdresseI);

		lblVille = new JLabel("Ville : ");
		lblVille.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblVille = new GridBagConstraints();
		gbc_lblVille.fill = GridBagConstraints.BOTH;
		gbc_lblVille.insets = new Insets(5, 5, 5, 5);
		gbc_lblVille.gridx = 0;
		gbc_lblVille.gridy = 3;
		panelInfoClient.add(lblVille, gbc_lblVille);

		lblVilleI = new JLabel("" + client.getVille());
		lblVilleI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblVilleI = new GridBagConstraints();
		gbc_lblVilleI.fill = GridBagConstraints.BOTH;
		gbc_lblVilleI.insets = new Insets(5, 5, 5, 5);
		gbc_lblVilleI.gridx = 1;
		gbc_lblVilleI.gridy = 3;
		panelInfoClient.add(lblVilleI, gbc_lblVilleI);

		lblTelephone = new JLabel("T\u00E9l\u00E9phone : ");
		lblTelephone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblTelephone = new GridBagConstraints();
		gbc_lblTelephone.fill = GridBagConstraints.BOTH;
		gbc_lblTelephone.insets = new Insets(5, 5, 5, 5);
		gbc_lblTelephone.gridx = 0;
		gbc_lblTelephone.gridy = 4;
		panelInfoClient.add(lblTelephone, gbc_lblTelephone);

		lblTelephoneI = new JLabel("0" + client.getTel());
		lblTelephoneI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblTelephoneI = new GridBagConstraints();
		gbc_lblTelephoneI.fill = GridBagConstraints.BOTH;
		gbc_lblTelephoneI.insets = new Insets(5, 5, 5, 5);
		gbc_lblTelephoneI.gridx = 1;
		gbc_lblTelephoneI.gridy = 4;
		panelInfoClient.add(lblTelephoneI, gbc_lblTelephoneI);

		lblMail = new JLabel("Courriel : ");
		lblMail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMail = new GridBagConstraints();
		gbc_lblMail.fill = GridBagConstraints.BOTH;
		gbc_lblMail.insets = new Insets(5, 5, 5, 5);
		gbc_lblMail.gridx = 0;
		gbc_lblMail.gridy = 5;
		panelInfoClient.add(lblMail, gbc_lblMail);

		lblMailI = new JLabel("" + client.getMail());
		lblMailI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblMailI = new GridBagConstraints();
		gbc_lblMailI.fill = GridBagConstraints.BOTH;
		gbc_lblMailI.insets = new Insets(5, 5, 5, 5);
		gbc_lblMailI.gridx = 1;
		gbc_lblMailI.gridy = 5;
		panelInfoClient.add(lblMailI, gbc_lblMailI);

		JPanel panelDescriptionClient = new JPanel();
		panelDescriptionClient.setPreferredSize(new Dimension(10, 40));
		panelGauche.add(panelDescriptionClient, BorderLayout.NORTH);

		JLabel lblInformationClient = new JLabel("INFORMATIONS CLIENT");
		lblInformationClient.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblInformationClient.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDescriptionClient.add(lblInformationClient);

		this.btnModification.addActionListener(this);

		JPanel panelDroit = new JPanel();
		panelDroit.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panelDroit = new GridBagConstraints();
		gbc_panelDroit.weighty = 100.0;
		gbc_panelDroit.weightx = 40.0;
		gbc_panelDroit.fill = GridBagConstraints.BOTH;
		gbc_panelDroit.gridx = 1;
		gbc_panelDroit.gridy = 0;
		add(panelDroit, gbc_panelDroit);
		panelDroit.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panelDroit.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		int[] rowH = new int[this.nbMatofThisClient + 1];
		double[] rowW = new double[this.nbMatofThisClient + 1];
		for (int i = 0; i < this.nbMatofThisClient; i++) {
			rowH[i] = 101;
			rowW[i] = 0.0;
		}
		rowH[rowH.length - 1] = 0;
		rowW[rowW.length - 1] = Double.MIN_VALUE;

		panelMateriel = new JPanel();
		panel_1.add(panelMateriel, BorderLayout.CENTER);
		GridBagLayout gbl_panelMateriel = new GridBagLayout();
		gbl_panelMateriel.rowHeights = rowH;
		gbl_panelMateriel.columnWeights = new double[] {};
		gbl_panelMateriel.rowWeights = rowW;
		panelMateriel.setLayout(gbl_panelMateriel);

		updatePanelMateriel();

		JPanel panelSudMateriel = new JPanel();
		panelSudMateriel.setPreferredSize(new Dimension(10, 120));
		panelDroit.add(panelSudMateriel, BorderLayout.SOUTH);
		panelSudMateriel.setLayout(new GridLayout(3, 1, 0, 0));

		btnAddMateriel = new JButton("Ajouter Mat\u00E9riel");
		btnAddMateriel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSudMateriel.add(btnAddMateriel);

		btnPrendreRdV = new JButton("Prendre rendez-vous");
		btnPrendreRdV.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSudMateriel.add(btnPrendreRdV);

		jtfNomMat = new JTextField();
		jtfNomMat.setColumns(10);
		jtfNomMat.setVisible(false);

		jtfTypeMat = new JTextField();
		jtfTypeMat.setColumns(10);

		jtfNumSerieMat = new JTextField();
		jtfNumSerieMat.setColumns(10);

		jtfMarqueMat = new JTextField();
		jtfMarqueMat.setColumns(10);

		JPanel panelDescriptionMat = new JPanel();
		panelDescriptionMat.setPreferredSize(new Dimension(10, 40));
		panelDroit.add(panelDescriptionMat, BorderLayout.NORTH);

		JLabel lblMateriel = new JLabel("MATERIELS");
		lblMateriel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelDescriptionMat.add(lblMateriel);
		
		this.btnPrendreRdV.addActionListener(this);
		this.btnAddMateriel.addActionListener(this);
		for (JButton e : this.btnModifMateriel) {
			e.addActionListener(this);
		}
		for (JButton e : this.btnVoirPlus) {
			e.addActionListener(this);
		}
		for (JButton e : this.btnSupprMateriel) {
			e.addActionListener(this);
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Action du bouton de modification des informations clients
		if (arg0.getSource() == btnModification) {
			if (jtfnom.isVisible()) {
				clientDAO.update(createClient());
				updatelblClient();
				changeVisibilityOfClient(true);
			} else
				changeVisibilityOfClient(false);
			// Action du bouton d'ajout d'un matériels
		} else if (arg0.getSource() == btnAddMateriel) {
			FrameAjoutMateriel frame = new FrameAjoutMateriel(this);
			frame.setVisible(true);
			this.mf.dispose();
		} else if (arg0.getSource() == btnPrendreRdV) {
			this.mf.changePanel(new PanelRDV(this));
			// Action des boutons "Voir Plus", affichage en pop-up de
			// l'historique des interventions
		} else if (this.btnVoirPlus.contains(arg0.getSource())) {
			Materiel matSelect = this.listMateriel.get(this.btnVoirPlus.indexOf(arg0.getSource()));
			String listInterDeCeMat = Methode
					.toStringInterventionList(interventionDAO.getListIntervention(matSelect.getId_materiel()), false);
			JOptionPane.showMessageDialog(this, "<html>" + listInterDeCeMat + "</html>", "Historique des interventions",
					0, new ImageIcon("images/icon-832005_960_720.png"));
			// Action des boutons de modification du matériels d'un clients
		} else if (this.btnModifMateriel.contains(arg0.getSource())) {
			if (jtfNomMat.isVisible()) {
				materielDAO.update(createMaterielbyJtf(
						this.listMateriel.get(this.btnModifMateriel.indexOf(arg0.getSource())).getId_materiel()));
				changeVisibilityOfMateriel(true, this.btnModifMateriel.indexOf(arg0.getSource()));
				updatePanelMateriel();
			} else
				changeVisibilityOfMateriel(false, this.btnModifMateriel.indexOf(arg0.getSource()));
			// Action des boutons de suppression du matériels
		} else if (this.btnSupprMateriel.contains(arg0.getSource())) {
			materielDAO.delete(this.listMateriel.get(this.btnSupprMateriel.indexOf(arg0.getSource())));
			MainFrame frame = new MainFrame();
			frame.setClient(this.client);
			frame.setActivePanel(new PanelClient(frame));
			frame.init();
			frame.setVisible(true);
			this.mf.dispose();
		}

	}

	private void updatePanelMateriel() {
		this.panelMateriel.removeAll();
		
		for (int i = 0; i < this.nbMatofThisClient; i++) {

			lblNomMatI = new JLabel(listMateriel.get(i).getNom());
			lblNomMatI.setFont(new Font("Tahoma", Font.PLAIN, 20));
			gbc_lblNomMatI = new GridBagConstraints();
			gbc_lblNomMatI.fill = GridBagConstraints.BOTH;
			gbc_lblNomMatI.gridx = 0; 
			gbc_lblNomMatI.gridy = i;
			gbc_lblNomMatI.weightx = 15;
			gbc_lblNomMatI.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblNomMatI, gbc_lblNomMatI);

			lblTypeMatI = new JLabel(listMateriel.get(i).getType());
			lblTypeMatI.setFont(new Font("Tahoma", Font.PLAIN, 20));
			gbc_lblTypeMatI = new GridBagConstraints();
			gbc_lblTypeMatI.fill = GridBagConstraints.BOTH;
			gbc_lblTypeMatI.gridx = 1; 
			gbc_lblTypeMatI.gridy = i;
			gbc_lblTypeMatI.weightx = 15;
			gbc_lblTypeMatI.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblTypeMatI, gbc_lblTypeMatI);

			lblNumSerieI = new JLabel(listMateriel.get(i).getNumSerie());
			lblNumSerieI.setFont(new Font("Tahoma", Font.PLAIN, 20));
			gbc_lblNumSerieI = new GridBagConstraints();
			gbc_lblNumSerieI.fill = GridBagConstraints.BOTH;
			gbc_lblNumSerieI.gridx = 2; 
			gbc_lblNumSerieI.gridy = i;
			gbc_lblNumSerieI.weightx = 15;
			gbc_lblNumSerieI.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblNumSerieI, gbc_lblNumSerieI);

			lblMarqueMatI = new JLabel(listMateriel.get(i).getMarque());
			lblMarqueMatI.setFont(new Font("Tahoma", Font.PLAIN, 20));
			gbc_lblMarqueMatI = new GridBagConstraints();
			gbc_lblMarqueMatI.fill = GridBagConstraints.BOTH;
			gbc_lblMarqueMatI.gridx = 3;
			gbc_lblMarqueMatI.gridy = i;
			gbc_lblMarqueMatI.weightx = 15;
			gbc_lblMarqueMatI.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblMarqueMatI, gbc_lblMarqueMatI);

			String historique = Methode
					.toString3(interventionDAO.getListIntervention(listMateriel.get(i).getId_materiel()));
			lblHistoriqueIntervention = new JLabel("<html>" + historique + "</html>");
			lblHistoriqueIntervention.setFont(new Font("Tahoma", Font.PLAIN, 16));
			gbc_lblHistorique = new GridBagConstraints();
			gbc_lblHistorique.fill = GridBagConstraints.BOTH;
			gbc_lblHistorique.gridx = 4;
			gbc_lblHistorique.gridy = i;
			gbc_lblHistorique.weightx = 30;
			gbc_lblHistorique.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblHistoriqueIntervention, gbc_lblHistorique);

			JPanel panelBtn = new JPanel();
			panelBtn.setLayout(new GridLayout(3, 1));
			panelBtn.add(this.btnVoirPlus.get(i));
			panelBtn.add(this.btnModifMateriel.get(i));
			panelBtn.add(this.btnSupprMateriel.get(i));

			GridBagConstraints gbc_btn = new GridBagConstraints();
			gbc_btn.fill = GridBagConstraints.BOTH;
			gbc_btn.gridx = 5;
			gbc_btn.gridy = i;
			gbc_btn.weightx = 10;
			gbc_btn.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(panelBtn, gbc_btn);
		}
		this.panelMateriel.validate();
	}

	/**
	 * Met à jour les labels informatifs du client lors qu'il y a eut une
	 * modification
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
	 * 
	 * @return c : Client
	 */
	public Client createClient() {
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
	 *
	 * @return mat : Materiel
	 */
	private Materiel createMaterielbyJtf(int id) {
		Materiel mat = new Materiel();
		mat.setId_materiel(id);
		mat.setNom(jtfNomMat.getText());
		mat.setNumSerie(jtfNumSerieMat.getText());
		mat.setType(jtfTypeMat.getText());
		mat.setMarque(jtfMarqueMat.getText());
		mat.setClient(this.client);
		return mat;
	}

	/**
	 * switch panel client
	 * 
	 * @param a
	 */
	public void changeVisibilityOfClient(boolean a) {
		lblNomI.setVisible(a);
		lblPrenomI.setVisible(a);
		lblAdresseI.setVisible(a);
		lblVilleI.setVisible(a);
		lblTelephoneI.setVisible(a);
		lblMailI.setVisible(a);
		if (a) {
			a = false;
		} else {
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
	 * 
	 * @param b
	 */
	private void changeVisibilityOfMateriel(boolean b, int matIndex) {
		Materiel matSelect = this.listMateriel.get(matIndex);
		lblNomMatI.setVisible(b);
		lblTypeMatI.setVisible(b);
		lblNumSerieI.setVisible(b);
		lblMarqueMatI.setVisible(b);
		if (b) {
			b = false;
		} else {
			b = true;
		}
		GridBagConstraints gbc_jtfNomMat = new GridBagConstraints();
		gbc_jtfNomMat.fill = GridBagConstraints.BOTH;
		gbc_jtfNomMat.gridx = 1;
		gbc_jtfNomMat.gridy = matIndex;
		gbc_jtfNomMat.insets = new Insets(5, 5, 5, 5);
		jtfNomMat.setText(matSelect.getNom());
		jtfNomMat.setVisible(b);
		panelMateriel.add(jtfNomMat, gbc_jtfNomMat);
		//
		GridBagConstraints gbc_jtfTypeMat = new GridBagConstraints();
		gbc_jtfTypeMat.fill = GridBagConstraints.BOTH;
		gbc_jtfTypeMat.gridx = 3;
		gbc_jtfTypeMat.gridy = matIndex;
		gbc_jtfTypeMat.insets = new Insets(5, 5, 5, 5);
		jtfTypeMat.setText(matSelect.getType());
		jtfTypeMat.setVisible(b);
		panelMateriel.add(jtfTypeMat, gbc_jtfTypeMat);
		//
		GridBagConstraints gbc_jtfNumSerie = new GridBagConstraints();
		gbc_jtfNumSerie.fill = GridBagConstraints.BOTH;
		gbc_jtfNumSerie.gridx = 5;
		gbc_jtfNumSerie.gridy = matIndex;
		gbc_jtfNumSerie.insets = new Insets(5, 5, 5, 5);
		jtfNumSerieMat.setText(matSelect.getNumSerie());
		jtfNumSerieMat.setVisible(b);
		panelMateriel.add(jtfNumSerieMat, gbc_jtfNumSerie);
		//
		GridBagConstraints gbc_jtfMarqueMat = new GridBagConstraints();
		gbc_jtfMarqueMat.fill = GridBagConstraints.BOTH;
		gbc_jtfMarqueMat.gridx = 7;
		gbc_jtfMarqueMat.gridy = matIndex;
		gbc_jtfMarqueMat.insets = new Insets(5, 5, 5, 5);
		jtfMarqueMat.setText(matSelect.getMarque());
		jtfMarqueMat.setVisible(b);
		panelMateriel.add(jtfMarqueMat, gbc_jtfMarqueMat);
	}
	
	

}
