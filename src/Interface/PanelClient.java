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
		
		JButton btnModification = new JButton("Modification");
		btnModification.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelGButton.add(btnModification);
		
		JPanel panelInfoClient = new JPanel();
		panelGauche.add(panelInfoClient, BorderLayout.CENTER);
		panelInfoClient.setLayout(new GridLayout(6, 1, 0, 0));
		
		lblNom = new JLabel("Nom : "+client.getNom());
		panelInfoClient.add(lblNom);
		
		lblPrenom = new JLabel("Pr\u00E9nom : "+client.getPrenom());
		panelInfoClient.add(lblPrenom);
		
		lblAdresse = new JLabel("Adresse : "+client.getAdresse());
		panelInfoClient.add(lblAdresse);
		
		lblVille = new JLabel("Ville : "+client.getVille());
		panelInfoClient.add(lblVille);
		
		lblTelephone = new JLabel("T\u00E9l\u00E9phone : "+client.getTel());
		panelInfoClient.add(lblTelephone);
		
		lblMail = new JLabel("Courriel : "+client.getMail());
		panelInfoClient.add(lblMail);
		
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
		}
		
	}
	
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
