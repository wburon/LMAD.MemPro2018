package Interface;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import DAO.ClientDAO;
import DAO.InterventionDAO;
import DAO.Rendez_VousDAO;
import Model.Intervention;
import Model.Materiel;
import Model.Rendez_Vous;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class Panel_RdvInfo extends JPanel implements ActionListener {
	
	// JTextField et ComboBox de construction
	private JTextField jtfNumFact, jtfNumBL, jtfRefPiece;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxTI, comboBoxMateriel;
	
	// Les boutons
	private JButton btnTerminer, btnAnnuler;
	
	// PanelRdv
	private PanelRDV rdv;
	
	// DAO
	private Rendez_VousDAO rdvDAO;
	private InterventionDAO interDAO;
	private ClientDAO clDAO;
	
	// MainFrame
	private MainFrame mf;
	
	// Liste de materiel du client
	private ArrayList<Materiel> listMateriel;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Panel_RdvInfo(PanelRDV rdv) {
		this.rdv = rdv;
		this.mf = rdv.getMf();
		rdvDAO = new Rendez_VousDAO();
		clDAO = new ClientDAO();
		interDAO = new InterventionDAO();
		
		setLayout(new BorderLayout(0, 0));

		JPanel panelTitre = new JPanel();
		panelTitre.setPreferredSize(new Dimension(10, 40));
		add(panelTitre, BorderLayout.NORTH);

		JLabel lblInfoEntr = new JLabel("Informations Compl\u00E9mentaires");
		lblInfoEntr.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelTitre.add(lblInfoEntr);

		JPanel panelBoutton = new JPanel();
		panelBoutton.setPreferredSize(new Dimension(10, 40));
		add(panelBoutton, BorderLayout.SOUTH);

		btnTerminer = new JButton("TERMINER");
		btnTerminer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelBoutton.add(btnTerminer);
		
		btnAnnuler = new JButton("ANNULER");
		btnAnnuler.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelBoutton.add(btnAnnuler);

		JPanel panelEcriture = new JPanel();
		add(panelEcriture, BorderLayout.CENTER);
		panelEcriture.setLayout(new GridLayout(5, 2, 0, 0));

		JLabel lblNumeroDeFacture = new JLabel("Numero de facture");
		panelEcriture.add(lblNumeroDeFacture);

		jtfNumFact = new JTextField();
		panelEcriture.add(jtfNumFact);
		jtfNumFact.setColumns(10);

		JLabel lblNumeroDeBl = new JLabel("Numero de BL");
		panelEcriture.add(lblNumeroDeBl);

		jtfNumBL = new JTextField();
		panelEcriture.add(jtfNumBL);
		jtfNumBL.setColumns(10);

		JLabel lblRefPiece = new JLabel("Reference piece");
		panelEcriture.add(lblRefPiece);

		jtfRefPiece = new JTextField();
		panelEcriture.add(jtfRefPiece);
		jtfRefPiece.setColumns(10);

		JLabel lblTypeIntervention = new JLabel("Type d'intervention");
		panelEcriture.add(lblTypeIntervention);

		comboBoxTI = new JComboBox();
		comboBoxTI.setModel(new DefaultComboBoxModel(new String[] {"ENTRETIENT", "OCCASIONNEL"}));
		panelEcriture.add(comboBoxTI);
		
		JLabel lblMateriel = new JLabel("Materiel");
		panelEcriture.add(lblMateriel);
		
		comboBoxMateriel = new JComboBox();
		this.listMateriel = clDAO.getListMateriel(rdv.getClient());
		comboBoxMateriel.setModel(new DefaultComboBoxModel(getListMatNameNumSerie(this.listMateriel)));
		panelEcriture.add(comboBoxMateriel);
		
		this.btnTerminer.addActionListener(this);
		this.btnAnnuler.addActionListener(this);
	}

	/**
	 * Renvoie une liste compos� de (NomMat et NumSerieMat) � partir de la listMateriel2
	 * @param listMateriel2
	 * @return tableau de type String[]
	 */
	private String[] getListMatNameNumSerie(ArrayList<Materiel> listMateriel2) {
		String[] tabName = new String[listMateriel2.size()];
		int i = 0;
		for(Materiel mat : listMateriel2){
			tabName[i] = mat.getNom() + " " + mat.getNumSerie();
			i++;
		}
		return tabName;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Validation terminal de la prise de rendez-vous
		if(arg0.getSource()==btnTerminer){
			rdvDAO.create(createRdV());
			this.rdv.getMf().changePanel(this.rdv.getPanelClient());
		// Annulation retour au panelRdv (premi�re etape)
		}else if(arg0.getSource() == btnAnnuler){
			this.mf.changePanel(rdv);
		}

	}
	
	/**
	 * Cr�e un objet Rendez_Vous
	 * @return
	 */
	public Rendez_Vous createRdV() {
		Rendez_Vous r = new Rendez_Vous();
		r.setIntervention(createIntervention());
		r.setDateDeb(rdv.getDeb());
		r.setDateFin(rdv.getFin());
		return r;
	}
	
	/**
	 * Cr�e une intervention dans la bdd et renvoie l'obget Intervention
	 * @return
	 */
	public Intervention createIntervention(){
		Intervention inter = new Intervention();
		inter.setMateriel(listMateriel.get(comboBoxMateriel.getSelectedIndex()));
		if(jtfNumBL.getText().equals(""))
			inter.setNumBL(0);
		else
			inter.setNumBL(Integer.parseInt(jtfNumBL.getText()));
		if(jtfNumFact.getText().equals(""))
			inter.setNumFacture(0);
		else
			inter.setNumFacture(Integer.parseInt(jtfNumFact.getText()));
		if(jtfRefPiece.getText().equals(""))
			inter.setRefPiece(0);
		else
			inter.setRefPiece(Integer.parseInt(jtfRefPiece.getText()));
		inter.setCommentaire(rdv.getCommentaire());
		inter.setDate(rdv.getDeb());
		inter.setType_intervention((String) comboBoxTI.getSelectedItem());
		inter.setId_intervention(interDAO.maxId());
		interDAO.create(inter);
		return inter;
	}

}
