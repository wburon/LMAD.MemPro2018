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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;

import DAO.ClientDAO;
import DAO.InterventionDAO;
import DAO.Rendez_VousDAO;
import Model.Intervention;
import Model.Materiel;
import Model.Rendez_Vous;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Panel_RdvInfo extends JPanel implements ActionListener {
	private JTextField jtfNumFact;
	private JTextField jtfNumBL;
	private JTextField jtfRefPiece;
	private JComboBox comboBoxTI;
	private JButton btnTerminer;
	private PanelRDV rdv;
	private Rendez_VousDAO rdvDAO;
	private JButton btnAnnuler;
	private InterventionDAO interDAO;
	private MainFrame mf;
	private JComboBox comboBoxMateriel;
	private ClientDAO clDAO;
	private ArrayList<Materiel> listMateriel;

	/**
	 * Create the panel.
	 */
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
		if(arg0.getSource()==btnTerminer){
			rdvDAO.create(createRdV());
			this.rdv.rinitPanelCommentaire();
		}else if(arg0.getSource() == btnAnnuler){
			this.mf.changePanel(rdv);
		}

	}

	public Rendez_Vous createRdV() {
		Rendez_Vous r = new Rendez_Vous();
		r.setIntervention(createIntervention());
		r.setDateDeb(rdv.getDeb());
		r.setDateFin(rdv.getFin());
		return r;
	}
	
	public Intervention createIntervention(){
		Intervention inter = new Intervention();
		inter.setMateriel(listMateriel.get(comboBoxMateriel.getSelectedIndex()));
		inter.setNumBL(Integer.parseInt(jtfNumBL.getText()));
		inter.setNumFacture(Integer.parseInt(jtfNumFact.getText()));
		inter.setRefPiece(Integer.parseInt(jtfRefPiece.getText()));
		inter.setCommentaire(rdv.getCommentaire());
		inter.setDate(rdv.getDeb());
		inter.setType_intervention((String) comboBoxTI.getSelectedItem());
		inter.setId_intervention(interDAO.maxId());
		interDAO.create(inter);
		return inter;
	}

}
