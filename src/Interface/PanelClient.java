package Interface;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.SwingConstants;

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

public class PanelClient extends JPanel implements ActionListener {

	// Champ de construction client
	private JLabel lblNom, lblPrenom, lblAdresse, lblVille, lblTelephone, lblMail;
	private JTextField jtfnom, jtfPrenom, jtfAdresse, jtfVille, jtfTelephone, jtfCourriel;
	private JLabel lblNomI, lblPrenomI, lblAdresseI, lblVilleI, lblTelephoneI, lblMailI;
	private JPanel panelInfoClient;
	private GridBagConstraints gbc_lblNomI, gbc_lblPrenomI, gbc_lblAdresseI, gbc_lblVilleI, gbc_lblTelephoneI,
			gbc_lblMailI;

	// Champ de construction matériel
	private JLabel lblNomMat, lblTypeMat, lblNumSerieMat;
	private JPanel panelMateriel;
	private JLabel lblNomMatI, lblTypeMatI, lblNumSerieI;
	private JTextField jtfNomMat, jtfTypeMat, jtfNumSerieMat, jtfMarqueMat;
	private GridBagConstraints gbc_lblNomMatI, gbc_lblTypeMatI, gbc_lblNumSerieI;

	// Button
	private JButton btnPrendreRdV, btnModification, btnAddMateriel;
	private ArrayList<JButton> btnModifMateriel, btnVoirPlus;

	// Autres champs
	private ArrayList<Materiel> listMateriel;

	private ClientDAO clientDAO;
	private Client client;
	private MaterielDAO materielDAO;
	private Materiel materiel;
	private MainFrame mf;
	private int nbMatofThisClient;
	private InterventionDAO interventionDAO;
	private JLabel lblHistoriqueIntervention;
	private GridBagConstraints gbc_lblHistorique;

	private JLabel lblMarqueMat;
	private JLabel lblMarqueMatI;
	private GridBagConstraints gbc_lblMarqueMatI;
	

	public Client getClient() {
		return createClient();
	}

	/**
	 * Create the panel.
	 */
	public PanelClient(MainFrame mf) {
		this.mf = mf;
		this.client = mf.getClient();
		clientDAO = new ClientDAO();
		materielDAO = new MaterielDAO();
		listMateriel = clientDAO.getListMateriel(client);
		nbMatofThisClient = clientDAO.getListMateriel(this.client).size();
		interventionDAO = new InterventionDAO();
		btnVoirPlus = Methode.createButtonVP(this.listMateriel.size());
		btnModifMateriel = Methode.createButtonModifMat(this.listMateriel.size());
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0 };
		setLayout(gridBagLayout);

		jtfnom = new JTextField();
		jtfnom.setText("" + client.getNom());
		jtfnom.setColumns(10);

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
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panelInfoClient.add(lblNom, gbc_lblNom);

		lblNomI = new JLabel("" + client.getNom());
		lblNomI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblNomI = new GridBagConstraints();
		gbc_lblNomI.fill = GridBagConstraints.BOTH;
		gbc_lblNomI.insets = new Insets(0, 0, 5, 0);
		gbc_lblNomI.gridx = 1;
		gbc_lblNomI.gridy = 0;
		panelInfoClient.add(lblNomI, gbc_lblNomI);

		lblPrenom = new JLabel("Pr\u00E9nom : ");
		lblPrenom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.fill = GridBagConstraints.BOTH;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 0;
		gbc_lblPrenom.gridy = 1;
		panelInfoClient.add(lblPrenom, gbc_lblPrenom);

		lblPrenomI = new JLabel("" + client.getPrenom());
		lblPrenomI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblPrenomI = new GridBagConstraints();
		gbc_lblPrenomI.fill = GridBagConstraints.BOTH;
		gbc_lblPrenomI.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrenomI.gridx = 1;
		gbc_lblPrenomI.gridy = 1;
		panelInfoClient.add(lblPrenomI, gbc_lblPrenomI);

		lblAdresse = new JLabel("Adresse : ");
		lblAdresse.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblAdresse = new GridBagConstraints();
		gbc_lblAdresse.fill = GridBagConstraints.BOTH;
		gbc_lblAdresse.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdresse.gridx = 0;
		gbc_lblAdresse.gridy = 2;
		panelInfoClient.add(lblAdresse, gbc_lblAdresse);

		lblAdresseI = new JLabel("" + client.getAdresse());
		lblAdresseI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblAdresseI = new GridBagConstraints();
		gbc_lblAdresseI.fill = GridBagConstraints.BOTH;
		gbc_lblAdresseI.insets = new Insets(0, 0, 5, 0);
		gbc_lblAdresseI.gridx = 1;
		gbc_lblAdresseI.gridy = 2;
		panelInfoClient.add(lblAdresseI, gbc_lblAdresseI);

		lblVille = new JLabel("Ville : ");
		lblVille.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblVille = new GridBagConstraints();
		gbc_lblVille.fill = GridBagConstraints.BOTH;
		gbc_lblVille.insets = new Insets(0, 0, 5, 5);
		gbc_lblVille.gridx = 0;
		gbc_lblVille.gridy = 3;
		panelInfoClient.add(lblVille, gbc_lblVille);

		lblVilleI = new JLabel("" + client.getVille());
		lblVilleI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblVilleI = new GridBagConstraints();
		gbc_lblVilleI.fill = GridBagConstraints.BOTH;
		gbc_lblVilleI.insets = new Insets(0, 0, 5, 0);
		gbc_lblVilleI.gridx = 1;
		gbc_lblVilleI.gridy = 3;
		panelInfoClient.add(lblVilleI, gbc_lblVilleI);

		lblTelephone = new JLabel("T\u00E9l\u00E9phone : ");
		lblTelephone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblTelephone = new GridBagConstraints();
		gbc_lblTelephone.fill = GridBagConstraints.BOTH;
		gbc_lblTelephone.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelephone.gridx = 0;
		gbc_lblTelephone.gridy = 4;
		panelInfoClient.add(lblTelephone, gbc_lblTelephone);

		lblTelephoneI = new JLabel("" + client.getTel());
		lblTelephoneI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblTelephoneI = new GridBagConstraints();
		gbc_lblTelephoneI.fill = GridBagConstraints.BOTH;
		gbc_lblTelephoneI.insets = new Insets(0, 0, 5, 0);
		gbc_lblTelephoneI.gridx = 1;
		gbc_lblTelephoneI.gridy = 4;
		panelInfoClient.add(lblTelephoneI, gbc_lblTelephoneI);

		lblMail = new JLabel("Courriel : ");
		lblMail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMail = new GridBagConstraints();
		gbc_lblMail.fill = GridBagConstraints.BOTH;
		gbc_lblMail.insets = new Insets(0, 0, 0, 5);
		gbc_lblMail.gridx = 0;
		gbc_lblMail.gridy = 5;
		panelInfoClient.add(lblMail, gbc_lblMail);

		lblMailI = new JLabel("" + client.getMail());
		lblMailI.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gbc_lblMailI = new GridBagConstraints();
		gbc_lblMailI.fill = GridBagConstraints.BOTH;
		gbc_lblMailI.gridx = 1;
		gbc_lblMailI.gridy = 5;
		panelInfoClient.add(lblMailI, gbc_lblMailI);

		JPanel panelDescriptionClient = new JPanel();
		panelDescriptionClient.setPreferredSize(new Dimension(10, 40));
		panelGauche.add(panelDescriptionClient, BorderLayout.NORTH);

		JLabel lblInformationClient = new JLabel("INFORMATION CLIENT");
		lblInformationClient.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblInformationClient.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDescriptionClient.add(lblInformationClient);

		this.btnModification.addActionListener(this);
		
		JPanel panelDroit = new JPanel();
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

		panelMateriel = new JPanel();
		panel_1.add(panelMateriel, BorderLayout.CENTER);
		GridBagLayout gbl_panelMateriel = new GridBagLayout();
		gbl_panelMateriel.rowHeights = new int[] { 101, 101, 101, 0 };
		gbl_panelMateriel.columnWeights = new double[] {};
		gbl_panelMateriel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelMateriel.setLayout(gbl_panelMateriel);

		for (int i = 0; i < this.nbMatofThisClient; i++) {

			lblNomMat = new JLabel("Nom : ");
			lblNomMat.setFont(new Font("Tahoma", Font.PLAIN, 20));
			GridBagConstraints gbc_lblNomMat = new GridBagConstraints();
			gbc_lblNomMat.fill = GridBagConstraints.BOTH;
			gbc_lblNomMat.gridx = 0;
			gbc_lblNomMat.gridy = i;
			gbc_lblNomMat.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblNomMat, gbc_lblNomMat);

			lblNomMatI = new JLabel(listMateriel.get(i).getNom());
			lblNomMatI.setFont(new Font("Tahoma", Font.PLAIN, 20));
			gbc_lblNomMatI = new GridBagConstraints();
			gbc_lblNomMatI.fill = GridBagConstraints.BOTH;
			gbc_lblNomMatI.gridx = 1;
			gbc_lblNomMatI.gridy = i;
			gbc_lblNomMatI.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblNomMatI, gbc_lblNomMatI);

			lblTypeMat = new JLabel("Type : ");
			lblTypeMat.setFont(new Font("Tahoma", Font.PLAIN, 20));
			GridBagConstraints gbc_lblTypeMat = new GridBagConstraints();
			gbc_lblTypeMat.fill = GridBagConstraints.BOTH;
			gbc_lblTypeMat.gridx = 2;
			gbc_lblTypeMat.gridy = i;
			gbc_lblTypeMat.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblTypeMat, gbc_lblTypeMat);

			lblTypeMatI = new JLabel(listMateriel.get(i).getType());
			lblTypeMatI.setFont(new Font("Tahoma", Font.PLAIN, 20));
			gbc_lblTypeMatI = new GridBagConstraints();
			gbc_lblTypeMatI.fill = GridBagConstraints.BOTH;
			gbc_lblTypeMatI.gridx = 3;
			gbc_lblTypeMatI.gridy = i;
			gbc_lblTypeMatI.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblTypeMatI, gbc_lblTypeMatI);

			lblNumSerieMat = new JLabel("Num\u00E9ro de s\u00E9rie : ");
			lblNumSerieMat.setFont(new Font("Tahoma", Font.PLAIN, 20));
			GridBagConstraints gbc_lblNumSerieMat = new GridBagConstraints();
			gbc_lblNumSerieMat.fill = GridBagConstraints.BOTH;
			gbc_lblNumSerieMat.gridx = 4;
			gbc_lblNumSerieMat.gridy = i;
			gbc_lblNumSerieMat.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblNumSerieMat, gbc_lblNumSerieMat);

			lblNumSerieI = new JLabel(listMateriel.get(i).getNumSerie());
			lblNumSerieI.setFont(new Font("Tahoma", Font.PLAIN, 20));
			gbc_lblNumSerieI = new GridBagConstraints();
			gbc_lblNumSerieI.fill = GridBagConstraints.BOTH;
			gbc_lblNumSerieI.gridx = 5;
			gbc_lblNumSerieI.gridy = i;
			gbc_lblNumSerieI.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblNumSerieI, gbc_lblNumSerieI);

			lblMarqueMat = new JLabel("Marque : ");
			lblMarqueMat.setFont(new Font("Tahoma", Font.PLAIN, 20));
			GridBagConstraints gbc_lblMarqueMat = new GridBagConstraints();
			gbc_lblMarqueMat.fill = GridBagConstraints.BOTH;
			gbc_lblMarqueMat.gridx = 6;
			gbc_lblMarqueMat.gridy = i;
			gbc_lblMarqueMat.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblMarqueMat, gbc_lblMarqueMat);

			lblMarqueMatI = new JLabel(listMateriel.get(i).getMarque());
			lblMarqueMatI.setFont(new Font("Tahoma", Font.PLAIN, 20));
			gbc_lblMarqueMatI = new GridBagConstraints();
			gbc_lblMarqueMatI.fill = GridBagConstraints.BOTH;
			gbc_lblMarqueMatI.gridx = 7;
			gbc_lblMarqueMatI.gridy = i;
			gbc_lblMarqueMatI.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblMarqueMatI, gbc_lblMarqueMatI);

			String historique = Methode
					.toString3(interventionDAO.getListIntervention(listMateriel.get(i).getId_materiel()));
			lblHistoriqueIntervention = new JLabel("<html>" + historique + "</html>");
			lblHistoriqueIntervention.setFont(new Font("Tahoma", Font.PLAIN, 16));
			gbc_lblHistorique = new GridBagConstraints();
			gbc_lblHistorique.fill = GridBagConstraints.BOTH;
			gbc_lblHistorique.gridx = 8;
			gbc_lblHistorique.gridy = i;
			gbc_lblHistorique.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(lblHistoriqueIntervention, gbc_lblHistorique);

			GridBagConstraints gbc_btnVP = new GridBagConstraints();
			gbc_btnVP.fill = GridBagConstraints.BOTH;
			gbc_btnVP.gridx = 9;
			gbc_btnVP.gridy = i;
			gbc_btnVP.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(btnVoirPlus.get(i), gbc_btnVP);
			
			GridBagConstraints gbc_btnModifMat = new GridBagConstraints();
			gbc_btnModifMat.fill = GridBagConstraints.BOTH;
			gbc_btnModifMat.gridx = 10;
			gbc_btnModifMat.gridy = i;
			gbc_btnModifMat.insets = new Insets(5, 5, 5, 5);
			panelMateriel.add(btnModifMateriel.get(i), gbc_btnModifMat);
		}

		JPanel panelSudMateriel = new JPanel();
		panelSudMateriel.setPreferredSize(new Dimension(10, 120));
		panelDroit.add(panelSudMateriel, BorderLayout.SOUTH);
		panelSudMateriel.setLayout(new GridLayout(3, 1, 0, 0));

		btnAddMateriel = new JButton("Ajouter Mat\u00E9riel");
		btnAddMateriel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSudMateriel.add(btnAddMateriel);

		this.btnAddMateriel.addActionListener(this);

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
		for(JButton e : this.btnModifMateriel){
			e.addActionListener(this);
		}
		for (JButton e : this.btnVoirPlus) {
			e.addActionListener(this);
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnModification) {
			if (jtfnom.isVisible()) {
				clientDAO.update(createClient());
				updatelblClient();
				changeVisibilityOfClient(true);
			} else
				changeVisibilityOfClient(false);
		} else if (arg0.getSource() == btnAddMateriel) {
			FrameAjoutMateriel frame = new FrameAjoutMateriel(this.client);
			frame.setVisible(true);
		} else if (arg0.getSource() == btnPrendreRdV) {
			this.mf.setActivePanel(new PanelRDV(this));
			this.mf.repaint();
		} else if (this.btnVoirPlus.contains(arg0.getSource())){
			Materiel matSelect = this.listMateriel.get(this.btnVoirPlus.indexOf(arg0.getSource()));
			String listInterDeCeMat = Methode.toStringInterventionList(interventionDAO.getListIntervention(matSelect.getId_materiel()));
			JOptionPane.showMessageDialog(this, "<html>"+listInterDeCeMat+"</html>", "Historique des interventions", 0, new ImageIcon("images/icon-832005_960_720.png"));
		} else if(this.btnModifMateriel.contains(arg0.getSource())){
			if(jtfNomMat.isVisible()){
				materielDAO.update(createMaterielbyJtf(this.listMateriel.get(this.btnModifMateriel.indexOf(arg0.getSource())).getId_materiel()));
				updatelblMateriel();
				changeVisibilityOfMateriel(true, this.btnModifMateriel.indexOf(arg0.getSource()));
			} else 
				changeVisibilityOfMateriel(false, this.btnModifMateriel.indexOf(arg0.getSource()));
			
		}

	}
	

	/**
	 * Met à jour les labels informatifs du materiel lors qu'il y a eut une
	 * modification
	 */
	private void updatelblMateriel() {
		// TODO
		
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
