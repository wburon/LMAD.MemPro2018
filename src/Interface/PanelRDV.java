package Interface;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.SwingConstants;

import Model.Client;
import Model.Methode;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class PanelRDV extends JPanel implements ActionListener {
	
	// JTextField définissant les dates de rendez-vous
	private JTextField jtfJour, jtfMois,jtfAn, jtfH1, jtfH2;
	// Stockage des dates de début et de fin
	private Date deb, fin;

	// Les différents boutons
	private JButton btnValider, btnAnnuler, btnOptimiser;
	private PanelClient panelClient;
	
	// MainFrame
	private MainFrame mf;
	
	// Autres champs plus spécifiques
	private JEditorPane dtrpnAjouteUnCommentaire;
	private JPanel panelCommentaire;
	private JPanel panel_1;
	private JPanel panel_3;
	private JPanel panelSud;
	private GridBagConstraints gbc_panelSud;
	private PanelPlanning panelNord;


	// Quelques getteurs utiles
	public MainFrame getMf() {
		return mf;
	}
	public Client getClient() {
		return panelClient.getClient();
	}
	public PanelClient getPanelClient() {
		return panelClient;
	}
	public Date getDeb() {
		return deb;
	}
	public Date getFin() {
		return fin;
	}
	public String getCommentaire() {
		return dtrpnAjouteUnCommentaire.getText();
	}

	/**
	 * Create the panel.
	 */
	public PanelRDV(PanelClient client) {
		this.panelClient = client;
		this.mf = client.getMf();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 450, 0 };
		gridBagLayout.rowHeights = new int[] { 150, 150, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		panelNord = new PanelPlanning();
		GridBagConstraints gbc_panelNord = new GridBagConstraints();
		gbc_panelNord.weightx = 100.0;
		gbc_panelNord.weighty = 90.0;
		gbc_panelNord.fill = GridBagConstraints.BOTH;
		gbc_panelNord.insets = new Insets(0, 0, 5, 0);
		gbc_panelNord.gridx = 0;
		gbc_panelNord.gridy = 0;
		add(panelNord, gbc_panelNord);

		panelSud = new JPanel();
		gbc_panelSud = new GridBagConstraints();
		gbc_panelSud.insets = new Insets(0, 0, 5, 0);
		gbc_panelSud.weightx = 100.0;
		gbc_panelSud.weighty = 10.0;
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

		dtrpnAjouteUnCommentaire = new JEditorPane();
		dtrpnAjouteUnCommentaire.setText("ajoute un commentaire ici");
		dtrpnAjouteUnCommentaire.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCommentaire.add(dtrpnAjouteUnCommentaire, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 344, 0 };
		gbl_panel_3.rowHeights = new int[] { 120, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JPanel panelInfoRdV = new JPanel();
		GridBagConstraints gbc_panelInfoRdV = new GridBagConstraints();
		gbc_panelInfoRdV.weightx = 80.0;
		gbc_panelInfoRdV.fill = GridBagConstraints.BOTH;
		gbc_panelInfoRdV.gridx = 0;
		gbc_panelInfoRdV.gridy = 0;
		panel_3.add(panelInfoRdV, gbc_panelInfoRdV);
		GridBagLayout gbl_panelInfoRdV = new GridBagLayout();
		gbl_panelInfoRdV.columnWidths = new int[] {89, 89, 89, 89, 89, 89, 0};
		gbl_panelInfoRdV.rowHeights = new int[] { 60, 60, 0 };
		gbl_panelInfoRdV.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelInfoRdV.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelInfoRdV.setLayout(gbl_panelInfoRdV);

		JLabel lblJour = new JLabel("Jour (exemple : 10)");
		lblJour.setHorizontalAlignment(SwingConstants.CENTER);
		lblJour.setHorizontalTextPosition(SwingConstants.CENTER);
		lblJour.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_lblJour = new GridBagConstraints();
		gbc_lblJour.fill = GridBagConstraints.BOTH;
		gbc_lblJour.insets = new Insets(0, 0, 5, 5);
		gbc_lblJour.gridx = 0;
		gbc_lblJour.gridy = 0;
		panelInfoRdV.add(lblJour, gbc_lblJour);

		jtfJour = new JTextField();
		GridBagConstraints gbc_jtfJour = new GridBagConstraints();
		gbc_jtfJour.fill = GridBagConstraints.BOTH;
		gbc_jtfJour.insets = new Insets(0, 0, 5, 5);
		gbc_jtfJour.gridx = 1;
		gbc_jtfJour.gridy = 0;
		panelInfoRdV.add(jtfJour, gbc_jtfJour);
		jtfJour.setColumns(10);

		JLabel lblMois = new JLabel("Mois (exemple : 01)");
		lblMois.setHorizontalAlignment(SwingConstants.CENTER);
		lblMois.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_lblMois = new GridBagConstraints();
		gbc_lblMois.fill = GridBagConstraints.BOTH;
		gbc_lblMois.insets = new Insets(0, 0, 5, 5);
		gbc_lblMois.gridx = 2;
		gbc_lblMois.gridy = 0;
		panelInfoRdV.add(lblMois, gbc_lblMois);

		jtfMois = new JTextField();
		GridBagConstraints gbc_jtfMois = new GridBagConstraints();
		gbc_jtfMois.fill = GridBagConstraints.BOTH;
		gbc_jtfMois.insets = new Insets(0, 0, 5, 5);
		gbc_jtfMois.gridx = 3;
		gbc_jtfMois.gridy = 0;
		panelInfoRdV.add(jtfMois, gbc_jtfMois);
		jtfMois.setColumns(10);

		JLabel lblAnnee = new JLabel("Annee (exemple : 2018)");
		lblAnnee.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblAnnee = new GridBagConstraints();
		gbc_lblAnnee.fill = GridBagConstraints.BOTH;
		gbc_lblAnnee.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnnee.gridx = 4;
		gbc_lblAnnee.gridy = 0;
		panelInfoRdV.add(lblAnnee, gbc_lblAnnee);

		jtfAn = new JTextField();
		GridBagConstraints gbc_jtfAn = new GridBagConstraints();
		gbc_jtfAn.insets = new Insets(0, 0, 5, 5);
		gbc_jtfAn.fill = GridBagConstraints.BOTH;
		gbc_jtfAn.gridx = 5;
		gbc_jtfAn.gridy = 0;
		panelInfoRdV.add(jtfAn, gbc_jtfAn);
		jtfAn.setColumns(10);

		JLabel lblDe = new JLabel("<html>De<br>(exemple : 10h15)</html>");
		lblDe.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblDe = new GridBagConstraints();
		gbc_lblDe.fill = GridBagConstraints.BOTH;
		gbc_lblDe.insets = new Insets(0, 0, 0, 5);
		gbc_lblDe.gridx = 0;
		gbc_lblDe.gridy = 1;
		panelInfoRdV.add(lblDe, gbc_lblDe);

		jtfH1 = new JTextField();
		GridBagConstraints gbc_jtfH1 = new GridBagConstraints();
		gbc_jtfH1.fill = GridBagConstraints.BOTH;
		gbc_jtfH1.insets = new Insets(0, 0, 0, 5);
		gbc_jtfH1.gridx = 1;
		gbc_jtfH1.gridy = 1;
		panelInfoRdV.add(jtfH1, gbc_jtfH1);
		jtfH1.setColumns(10);

		JLabel lblA = new JLabel("\u00E0");
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.fill = GridBagConstraints.BOTH;
		gbc_lblA.insets = new Insets(0, 0, 0, 5);
		gbc_lblA.gridx = 2;
		gbc_lblA.gridy = 1;
		panelInfoRdV.add(lblA, gbc_lblA);

		jtfH2 = new JTextField();
		GridBagConstraints gbc_jtfH2 = new GridBagConstraints();
		gbc_jtfH2.insets = new Insets(0, 0, 0, 5);
		gbc_jtfH2.fill = GridBagConstraints.BOTH;
		gbc_jtfH2.gridx = 3;
		gbc_jtfH2.gridy = 1;
		panelInfoRdV.add(jtfH2, gbc_jtfH2);
		jtfH2.setColumns(10);

		// on rend les boutons utilisables
		btnValider.addActionListener(this);
		btnAnnuler.addActionListener(this);
		btnOptimiser.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// On valide cette étape et passe à la suivante
		if (arg0.getSource() == btnValider) {
			String[] h1 = jtfH1.getText().split("h");
			String[] h2 = jtfH2.getText().split("h");
			Calendar cal = new GregorianCalendar(Locale.FRANCE);
			cal.set(Integer.parseInt(jtfAn.getText()), Integer.parseInt(jtfMois.getText())-1, Integer.parseInt(jtfJour.getText()), Integer.parseInt(h1[0]),Integer.parseInt(h1[1]));
			this.deb = cal.getTime();
			cal.set(Integer.parseInt(jtfAn.getText()), Integer.parseInt(jtfMois.getText())-1, Integer.parseInt(jtfJour.getText()), Integer.parseInt(h2[0]), Integer.parseInt(h2[1]));
			this.fin = cal.getTime();
			remove(this.panelSud);
			this.panelSud = new Panel_RdvInfo(this);
			add(panelSud, gbc_panelSud);
			validate();
		// Annulation de la prise de rendez-vous et retour a l'écran client
		} else if (arg0.getSource() == btnAnnuler) {
			this.mf.changePanel(this.panelClient);
		// Montre les rendez-déjà pris ce situant dans la même ville/rue que le client actuel
		} else if (arg0.getSource() == btnOptimiser) {
			String positionClient = this.getClient().getGps();
			// TODO trouve les trois rendez-vous déjà prit les plus proche de ce
			// client, les affiche en pop-up avec la distance en km et temps
			String troisClient = Methode.composeTroisClient(positionClient);
			JOptionPane.showMessageDialog(this, troisClient);
		}

	}

}
