package Interface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.ClientDAO;
import Model.Client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FrameAjoutClient extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField tfNom;
	private JTextField tfPrenom;
	private JTextField tfAdresse;
	private JTextField tfVille;
	private JTextField tfTlphone;
	private JTextField tfCourriel;
	
	private JButton btnAjouter;
	private JButton btnAnnuler;
	private JButton btnQuitter;
	
	private Client client;
	private ClientDAO cDAO;
	
	private static PanelAccueil pa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAjoutClient frame = new FrameAjoutClient(pa);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameAjoutClient(PanelAccueil pa) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelWest = new JPanel();
		contentPane.add(panelWest, BorderLayout.WEST);
		
		JPanel panelNorth = new JPanel();
		contentPane.add(panelNorth, BorderLayout.NORTH);
		
		JPanel panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		
		btnAjouter = new JButton("Ajouter");
		panelSouth.add(btnAjouter);
		btnAjouter.addActionListener(this);
		
		btnAnnuler = new JButton("Annuler");
		panelSouth.add(btnAnnuler);
		btnAnnuler.addActionListener(this);
		
		btnQuitter = new JButton("Quitter");
		panelSouth.add(btnQuitter);
		btnQuitter.addActionListener(this);
		
		JPanel panelEast = new JPanel();
		contentPane.add(panelEast, BorderLayout.EAST);
		
		JPanel panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(6, 2, 0, 0));
		
		JLabel lblNom = new JLabel("Nom");
		panelCenter.add(lblNom);
		
		tfNom = new JTextField();
		panelCenter.add(tfNom);
		tfNom.setColumns(10);
		
		JLabel lblPrenom = new JLabel("Prenom");
		panelCenter.add(lblPrenom);
		
		tfPrenom = new JTextField();
		panelCenter.add(tfPrenom);
		tfPrenom.setColumns(10);
		
		JLabel lblAdresse = new JLabel("Adresse");
		panelCenter.add(lblAdresse);
		
		tfAdresse = new JTextField();
		panelCenter.add(tfAdresse);
		tfAdresse.setColumns(10);
		
		JLabel lblVille = new JLabel("Ville");
		panelCenter.add(lblVille);
		
		tfVille = new JTextField();
		panelCenter.add(tfVille);
		tfVille.setColumns(10);
		
		JLabel lblTlphone = new JLabel("T\u00E9l\u00E9phone");
		panelCenter.add(lblTlphone);
		
		tfTlphone = new JTextField();
		panelCenter.add(tfTlphone);
		tfTlphone.setColumns(10);
		
		JLabel lblCourriel = new JLabel("Courriel");
		panelCenter.add(lblCourriel);
		
		tfCourriel = new JTextField();
		panelCenter.add(tfCourriel);
		tfCourriel.setColumns(10);
		
		client = new Client();
		cDAO = new ClientDAO();
		this.pa = pa;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == btnAjouter){
			String nom = tfNom.getText();
			String prenom = tfPrenom.getText();
			String adresse = tfAdresse.getText();
			String ville = tfVille.getText();
			int tel = Integer.parseInt(tfTlphone.getText());
			String courriel = tfCourriel.getText();
			
			String gps = calculGPS(adresse, ville);
			
			boolean a = creationClient(nom, prenom, adresse, ville, tel, courriel, gps);
			
			if(a){
				JOptionPane.showMessageDialog(btnAjouter, "Le client a bien été ajouté !", "Validation", JOptionPane.INFORMATION_MESSAGE);
				pa.ActualiserListClient();
		}
			else
				JOptionPane.showMessageDialog(btnAjouter, "Le client n'a pas pu être ajouté !", "Erreur", JOptionPane.ERROR_MESSAGE);
			
			
		}
		if(e.getSource()==btnAnnuler){
			clearTextField();
		}
		if(e.getSource()==btnQuitter){
			this.dispose();
		}
		
	}

	private boolean creationClient(String nom, String prenom, String adresse, String ville, int tel, String courriel,
			String gps) {
		
		client.setNom(nom);
		client.setPrenom(prenom);
		client.setAdresse(adresse);
		client.setVille(ville);
		client.setTel(tel);
		client.setMail(courriel);
		client.setGps(gps);
		
		boolean verif=cDAO.create(client);
		return verif;
	}
	private void clearTextField(){
		tfNom.setText("");
		tfPrenom.setText("");
		tfAdresse.setText("");
		tfVille.setText("");
		tfTlphone.setText("");
		tfCourriel.setText("");
	}

	private String calculGPS(String adresse, String ville) {
		// TODO Auto-generated method stub
		return null;
	}

}
