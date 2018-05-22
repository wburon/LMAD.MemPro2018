package Interface;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;

import DAO.ClientDAO;
import Model.Materiel;

import Model.Client;

import java.awt.FlowLayout;
import javax.swing.JTextField;

public class PanelClient extends JPanel implements ActionListener{

	private JLabel lblNom;
	private JLabel lblPrenom;
	private JLabel lblAdresse;
	private JLabel lblVille;
	private JLabel lblTelephone;
	private JLabel lblMail;
	private JButton btnPrendreRdV;
	private JButton btnPreviousMat;
	private JButton btnNextMat;
	private JLabel lblNomMat;
	private JLabel lblTypeMat;
	private JLabel lblNumSerieMat;
	private JPanel panelMateriel;
	private ArrayList<Materiel> listMateriel;
	private ClientDAO clientDAO;
	private int indiceMat;
	private JTextField jtfnom;
	private JTextField jtfPrenom;
	private JTextField jtfAdresse;
	private JTextField jtfVille;
	private JTextField jtfTelephone;
	private JTextField jtfCourriel;
	private JLabel lblTelephoneI;
	private JLabel lblVilleI;
	private JLabel lblMailI;
	private JLabel lblAdresseI;
	private JLabel lblPrenomI;
	private JLabel lblNomI;
	private JButton btnModification;

	public Client getClient() {
		return createClient();
	}
	
	public Materiel getMateriel(){
		return createMateriel();
	}

	/**
	 * Create the panel.
	 */
	public PanelClient(Client client) {
		clientDAO = new ClientDAO();
		listMateriel = clientDAO.getListMateriel(client);
		indiceMat = 0;
	
		
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
		
		JPanel panelInfoClient = new JPanel();
		panelGauche.add(panelInfoClient, BorderLayout.CENTER);
		panelInfoClient.setLayout(new GridLayout(6, 2, 0, 0));
		
		lblNom = new JLabel("Nom : ");
		panelInfoClient.add(lblNom);
		
		lblNomI = new JLabel(""+client.getNom());
		panelInfoClient.add(lblNomI);
		
		jtfnom = new JTextField();
		jtfnom.setText(""+client.getNom());
		jtfnom.setColumns(10);
		
		lblPrenom = new JLabel("Pr\u00E9nom : ");
		panelInfoClient.add(lblPrenom);
		
		lblPrenomI = new JLabel(""+client.getPrenom());
		panelInfoClient.add(lblPrenomI);
		
		jtfPrenom = new JTextField();
		jtfPrenom.setText(""+client.getPrenom());
		jtfPrenom.setColumns(10);
		
		lblAdresse = new JLabel("Adresse : ");
		panelInfoClient.add(lblAdresse);
		
		lblAdresseI = new JLabel(""+client.getAdresse());
		panelInfoClient.add(lblAdresseI);
		
		jtfAdresse = new JTextField();
		jtfAdresse.setText(""+client.getAdresse());
		jtfAdresse.setColumns(10);
		
		lblVille = new JLabel("Ville : ");
		panelInfoClient.add(lblVille);
		
		lblVilleI = new JLabel(""+client.getVille());
		panelInfoClient.add(lblVilleI);
		
		jtfVille = new JTextField();
		jtfVille.setText(""+client.getVille());
		jtfVille.setColumns(10);
		
		lblTelephone = new JLabel("T\u00E9l\u00E9phone : ");
		panelInfoClient.add(lblTelephone);
		
		lblTelephoneI = new JLabel(""+client.getTel());
		panelInfoClient.add(lblTelephoneI);
		
		jtfTelephone = new JTextField();
		jtfTelephone.setText(""+client.getTel());
		jtfTelephone.setColumns(10);
		
		lblMail = new JLabel("Courriel : ");
		panelInfoClient.add(lblMail);
		
		lblMailI = new JLabel(""+client.getMail());
		panelInfoClient.add(lblMailI);
		
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
		panelSudMateriel.setPreferredSize(new Dimension(10, 60));
		panelDroit.add(panelSudMateriel, BorderLayout.SOUTH);
		
		btnPrendreRdV = new JButton("Prendre rendez-vous");
		btnPrendreRdV.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelSudMateriel.add(btnPrendreRdV);
		
		JPanel panel_1 = new JPanel();
		panelDroit.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panelGaucheMat = new JPanel();
		panelGaucheMat.setPreferredSize(new Dimension(40, 10));
		panel_1.add(panelGaucheMat, BorderLayout.WEST);
		panelGaucheMat.setLayout(new BorderLayout(0, 0));
		
		btnPreviousMat = new JButton("<");
		btnPreviousMat.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPreviousMat.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelGaucheMat.add(btnPreviousMat);
		
		JPanel panelDroitMat = new JPanel();
		panelDroitMat.setPreferredSize(new Dimension(40, 10));
		panel_1.add(panelDroitMat, BorderLayout.EAST);
		panelDroitMat.setLayout(new BorderLayout(0, 0));
		
		btnNextMat = new JButton(">");
		panelDroitMat.add(btnNextMat, BorderLayout.CENTER);
		
		panelMateriel = new JPanel();
		panel_1.add(panelMateriel, BorderLayout.CENTER);
		panelMateriel.setLayout(new GridLayout(3, 1, 0, 0));
		
		lblNomMat = new JLabel("Nom : "+listMateriel.get(indiceMat).getNom());
		panelMateriel.add(lblNomMat);
		
		lblTypeMat = new JLabel("Type : "+listMateriel.get(indiceMat).getType());
		panelMateriel.add(lblTypeMat);
		
		lblNumSerieMat = new JLabel("Num\u00E9ro de s\u00E9rie : "+listMateriel.get(indiceMat).getNumSerie());
		panelMateriel.add(lblNumSerieMat);
		
		JPanel panelDescriptionMat = new JPanel();
		panelDescriptionMat.setPreferredSize(new Dimension(10, 40));
		panelDroit.add(panelDescriptionMat, BorderLayout.NORTH);
		
		JLabel lblMateriel = new JLabel("MATERIELS");
		lblMateriel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelDescriptionMat.add(lblMateriel);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnNextMat){
			getIndiceMat("Next");
			panelMateriel.repaint();
		}else if(arg0.getSource() == btnPreviousMat){
			getIndiceMat("Prev");
			panelMateriel.repaint();
		}else if(arg0.getSource() == btnModification){
			if(lblNomI.isEnabled())
				changeVisibilityOfClient(false);
			else
				clientDAO.update(createClient());
		}else if(arg0.getSource() == btnPrendreRdV){
			// do something
		}
		
	}
	
	/**
	 * creation d'un objet client a partir des infos des JTextField
	 * @return
	 */
	public Client createClient(){
		Client c = new Client();
		c.setNom(jtfnom.getText());
		c.setPrenom(jtfPrenom.getText());
		c.setAdresse(jtfAdresse.getText());
		c.setVille(jtfVille.getText());
		c.setTel(Integer.parseInt(jtfTelephone.getText()));
		c.setMail(jtfCourriel.getText());
		return c;
	}
	
	public Materiel createMateriel(){
		Materiel m = new Materiel();
		m.setNom(listMateriel.get(indiceMat).getNom());
		m.setNumSerie(listMateriel.get(indiceMat).getNumSerie());
		m.setType(listMateriel.get(indiceMat).getType());
		return m;
	}
	
	/**
	 * switch panel client
	 * @param a
	 */
	public void changeVisibilityOfClient(boolean a){
		lblNomI.setEnabled(a);
		lblPrenomI.setEnabled(a);
		lblAdresseI.setEnabled(a);
		lblVilleI.setEnabled(a);
		lblTelephoneI.setEnabled(a);
		lblMailI.setEnabled(a);
		if(a){
			a = false;
		}else {
			a = true;
		}
		jtfnom.setEnabled(a);
		jtfPrenom.setEnabled(a);
		jtfAdresse.setEnabled(a);
		jtfVille.setEnabled(a);
		jtfTelephone.setEnabled(a);
		jtfCourriel.setEnabled(a);
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
