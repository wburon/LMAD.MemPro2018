package Interface;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.SwingConstants;

import DAO.Rendez_VousDAO;
import Model.Client;
import Model.Materiel;
import Model.Rendez_Vous;

public class PanelRDV extends JPanel implements ActionListener{
	private JTextField jtfJour;
	private JTextField jtfMois;
	private JTextField jtfH1;
	private JTextField jtfH2;
	private JButton btnValider;
	private PanelClient client;
	private Date deb, fin;
	private JEditorPane editorPane;
	private JPanel panelCommentaire;

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
	
	public Materiel getMateriel(){
		return client.getMateriel();
	}

	public JPanel getPanelCommentaire() {
		return panelCommentaire;
	}

	public void setPanelCommentaire(JPanel panelCommentaire) {
		this.panelCommentaire = panelCommentaire;
	}

	/**
	 * Create the panel.
	 */
	public PanelRDV(PanelClient client) {
		this.client = client;
		
		setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panelNord = new PanelPlanning();
		add(panelNord);
		
		JPanel panelSud = new JPanel();
		add(panelSud);
		panelSud.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		panelSud.add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panelSud.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panelInfoRdV = new JPanel();
		panel_1.add(panelInfoRdV);
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
		
		panelCommentaire = new JPanel();
		panel_1.add(panelCommentaire);
		panelCommentaire.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 30));
		panelCommentaire.add(panel_2, BorderLayout.SOUTH);
		
		btnValider = new JButton("Valider");
		panel_2.add(btnValider);
		
		editorPane = new JEditorPane();
		panelCommentaire.add(editorPane, BorderLayout.CENTER);

		btnValider.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnValider){
			String[] h1 = jtfH1.getText().split(":");
			String[] h2 = jtfH2.getText().split(":");
			this.deb = new Date(Integer.parseInt(jtfJour.getText()),Integer.parseInt(jtfMois.getText()),Calendar.getInstance().get(Calendar.YEAR),Integer.parseInt(h1[0]),Integer.parseInt(h1[1]));
			this.fin = new Date(Integer.parseInt(jtfJour.getText()),Integer.parseInt(jtfMois.getText()),Calendar.getInstance().get(Calendar.YEAR),Integer.parseInt(h2[0]),Integer.parseInt(h2[1]));
			this.panelCommentaire = new Panel_RdvInfo(this);
			this.panelCommentaire.repaint();
		}
		
	}

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
