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
import java.util.Date;

import javax.swing.JTextField;

import DAO.Rendez_VousDAO;
import Model.Intervention;
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

	/**
	 * Create the panel.
	 */
	public Panel_RdvInfo(PanelRDV rdv) {
		this.rdv = rdv;
		rdvDAO = new Rendez_VousDAO();
		
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

		JPanel panelEcriture = new JPanel();
		add(panelEcriture, BorderLayout.CENTER);
		panelEcriture.setLayout(new GridLayout(4, 2, 0, 0));

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
		comboBoxTI.setModel(new DefaultComboBoxModel(new String[] { "ENTRETIENT", "AUTRE" }));
		panelEcriture.add(comboBoxTI);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==btnTerminer){
			rdvDAO.create(createRdV());
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
		inter.setClient(rdv.getClient());
		inter.setMateriel(rdv.getMateriel());
		inter.setNumBL(Integer.parseInt(jtfNumBL.getText()));
		inter.setNumFacture(Integer.parseInt(jtfNumFact.getText()));
		inter.setRefPiece(Integer.parseInt(jtfRefPiece.getText()));
		inter.setType_intervention(null); //TODO A VOIR SI LE TYPE PASSE EN ENUM
		return inter;
	}

}
