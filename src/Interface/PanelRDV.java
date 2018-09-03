package Interface;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.SwingConstants;

import DAO.Rendez_VousDAO;
import Model.Client;
import Model.Materiel;
import Model.Methode;
import Model.Rendez_Vous;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;

public class PanelRDV extends JPanel implements ActionListener {
	private JTextField jtfJour;
	private JTextField jtfMois;
	private JTextField jtfH1;
	private JTextField jtfH2;
	private JButton btnValider;
	private PanelClient client;
	private Date deb, fin;
	private JEditorPane editorPane;
	private JPanel panelCommentaire;
	private JButton btnAnnuler;
	private JButton btnOptimiser;
	private JPanel panel_1;
	private JPanel panel_3;
	private JPanel panel_4;
	private ArrayList<JRadioButton> listRadioButton;
	private MainFrame mf;

	public MainFrame getMf() {
		return mf;
	}

	public Client getClient() {
		return client.getClient();
	}

	public Date getDeb() {
		return deb;
	}

	public Date getFin() {
		return fin;
	}

	public String getCommentaire() {
		return editorPane.getText();
	}

	public JPanel getPanelCommentaire() {
		return panelCommentaire;
	}


	/**
	 * Create the panel.
	 */
	public PanelRDV(PanelClient client) {
		this.client = client;
		this.mf = client.getMf();
		this.listRadioButton = new ArrayList<>();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 450, 0 };
		gridBagLayout.rowHeights = new int[] { 150, 150, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panelNord = new PanelPlanning();
		GridBagConstraints gbc_panelNord = new GridBagConstraints();
		gbc_panelNord.weightx = 100.0;
		gbc_panelNord.weighty = 98.0;
		gbc_panelNord.fill = GridBagConstraints.BOTH;
		gbc_panelNord.insets = new Insets(0, 0, 5, 0);
		gbc_panelNord.gridx = 0;
		gbc_panelNord.gridy = 0;
		add(panelNord, gbc_panelNord);

		JPanel panelSud = new JPanel();
		GridBagConstraints gbc_panelSud = new GridBagConstraints();
		gbc_panelSud.weightx = 100.0;
		gbc_panelSud.weighty = 2.0;
		gbc_panelSud.fill = GridBagConstraints.BOTH;
		gbc_panelSud.gridx = 0;
		gbc_panelSud.gridy = 1;
		add(panelSud, gbc_panelSud);
		panelSud.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		panelSud.add(panel, BorderLayout.NORTH);

		btnOptimiser = new JButton("Optimiser");
		panel.add(btnOptimiser);

		panel_1 = new JPanel();
		panelSud.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));

		panel_3 = new JPanel();
		panel_1.add(panel_3);

		panelCommentaire = new JPanel();
		panel_1.add(panelCommentaire);
		panelCommentaire.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 30));
		panelCommentaire.add(panel_2, BorderLayout.SOUTH);

		btnValider = new JButton("Valider");
		panel_2.add(btnValider);

		btnAnnuler = new JButton("Annuler");
		panel_2.add(btnAnnuler);

		editorPane = new JEditorPane();
		panelCommentaire.add(editorPane, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 344, 0 };
		gbl_panel_3.rowHeights = new int[] { 120, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.weightx = 20.0;
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 0;
		panel_3.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0 };
		gbl_panel_4.rowHeights = new int[] { 0 };
		gbl_panel_4.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JPanel panelInfoRdV = new JPanel();
		GridBagConstraints gbc_panelInfoRdV = new GridBagConstraints();
		gbc_panelInfoRdV.weightx = 80.0;
		gbc_panelInfoRdV.fill = GridBagConstraints.BOTH;
		gbc_panelInfoRdV.gridx = 0;
		gbc_panelInfoRdV.gridy = 0;
		panel_3.add(panelInfoRdV, gbc_panelInfoRdV);
		panelInfoRdV.setLayout(new GridLayout(2, 4, 0, 0));

		JLabel lblJour = new JLabel("Jour");
		lblJour.setHorizontalAlignment(SwingConstants.CENTER);
		lblJour.setHorizontalTextPosition(SwingConstants.CENTER);
		lblJour.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelInfoRdV.add(lblJour);

		jtfJour = new JTextField();
		panelInfoRdV.add(jtfJour);
		jtfJour.setColumns(10);

		JLabel lblMois = new JLabel("Mois");
		lblMois.setHorizontalAlignment(SwingConstants.CENTER);
		lblMois.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelInfoRdV.add(lblMois);

		jtfMois = new JTextField();
		panelInfoRdV.add(jtfMois);
		jtfMois.setColumns(10);

		JLabel lblDe = new JLabel("De");
		lblDe.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoRdV.add(lblDe);

		jtfH1 = new JTextField();
		panelInfoRdV.add(jtfH1);
		jtfH1.setColumns(10);

		JLabel lblA = new JLabel("\u00E0");
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoRdV.add(lblA);

		jtfH2 = new JTextField();
		panelInfoRdV.add(jtfH2);
		jtfH2.setColumns(10);

		ArrayList<Materiel> listMatOfClient = this.client.getListMateriel();
		for (int i = 0; i < listMatOfClient.size(); i++) {
			JRadioButton rdbtnRadioButton = new JRadioButton(listMatOfClient.get(i).getNom());
			GridBagConstraints gbc_Radio = new GridBagConstraints();
			gbc_Radio.fill = GridBagConstraints.BOTH;
			gbc_Radio.gridx = 0;
			gbc_Radio.gridy = i;
			gbc_Radio.insets = new Insets(5, 5, 5, 5);
			this.listRadioButton.add(rdbtnRadioButton);
			panel_4.add(rdbtnRadioButton);
		}

		btnValider.addActionListener(this);
		btnAnnuler.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnValider) {
			String[] h1 = jtfH1.getText().split("h");
			String[] h2 = jtfH2.getText().split("h");
			this.deb = new Date(Integer.parseInt(jtfJour.getText()), Integer.parseInt(jtfMois.getText()),
					Calendar.getInstance().get(Calendar.YEAR), Integer.parseInt(h1[0]), Integer.parseInt(h1[1]));
			this.fin = new Date(Integer.parseInt(jtfJour.getText()), Integer.parseInt(jtfMois.getText()),
					Calendar.getInstance().get(Calendar.YEAR), Integer.parseInt(h2[0]), Integer.parseInt(h2[1]));
			this.panel_1 = new Panel_RdvInfo(this);
			this.panel_1.repaint();
		} else if (arg0.getSource() == btnAnnuler) {
			// TODO
		} else if (arg0.getSource() == btnOptimiser) {
			String positionClient = this.client.getClient().getGps();
			// TODO trouve les trois rendez-vous déjà prit les plus proche de ce
			// client, les affiche en pop-up avec la distance en km et temps
			String troisClient = Methode.composeTroisClient(positionClient);
			JOptionPane.showMessageDialog(this, troisClient);
		}

	}

	/**
	 * Repaint la partit RDV pour reafficher l'editor pane de commentaire
	 */
	public void rinitPanelCommentaire() {

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 30));
		this.panelCommentaire.add(panel_2, BorderLayout.SOUTH);

		btnValider = new JButton("Valider");
		panel_2.add(btnValider);

		editorPane = new JEditorPane();
		this.panelCommentaire.add(editorPane, BorderLayout.CENTER);

		this.panelCommentaire.repaint();
	}

}
