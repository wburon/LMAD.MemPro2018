package Interface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.ClientDAO;
import DAO.MaterielDAO;
import Model.Client;
import Model.Materiel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

public class FrameAjoutMateriel extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField jtfNom;
	private JTextField jtfType;
	private JTextField jtfNumSerie;
	private JButton btnAnnuler;
	private JButton btnValider;
	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientDAO clDAO = new ClientDAO();
					Client cl = clDAO.find(0);
					FrameAjoutMateriel frame = new FrameAjoutMateriel(cl);
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
	public FrameAjoutMateriel(Client client) {
		this.client = client;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_nord = new JPanel();
		panel_nord.setPreferredSize(new Dimension(10, 50));
		contentPane.add(panel_nord, BorderLayout.NORTH);
		
		JLabel lblAjoutonsDuMatriel = new JLabel("Ajoutons du mat\u00E9riel !");
		lblAjoutonsDuMatriel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel_nord.add(lblAjoutonsDuMatriel);
		
		JPanel panel_sud = new JPanel();
		panel_sud.setPreferredSize(new Dimension(10, 50));
		contentPane.add(panel_sud, BorderLayout.SOUTH);
		
		btnValider = new JButton("Valider");
		btnValider.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel_sud.add(btnValider);
		
		btnAnnuler = new JButton("Annuler");
		btnAnnuler.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel_sud.add(btnAnnuler);
		
		JPanel panel_center = new JPanel();
		contentPane.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblNom = new JLabel("Nom");
		panel_center.add(lblNom);
		
		jtfNom = new JTextField();
		panel_center.add(jtfNom);
		jtfNom.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		panel_center.add(lblType);
		
		jtfType = new JTextField();
		panel_center.add(jtfType);
		jtfType.setColumns(10);
		
		JLabel lblNumroDeSrie = new JLabel("Num\u00E9ro de s\u00E9rie");
		panel_center.add(lblNumroDeSrie);
		
		jtfNumSerie = new JTextField();
		panel_center.add(jtfNumSerie);
		jtfNumSerie.setColumns(10);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnValider) {
			MaterielDAO matDAO = new MaterielDAO();
			Materiel mat = new Materiel();
			mat.setClient(this.client);
			mat.setNom(this.jtfNom.getText());
			mat.setNumSerie(this.jtfNumSerie.getText());
			mat.setType(this.jtfType.getText());
			matDAO.create(mat);
		}else if(arg0.getSource() == btnAnnuler) {
			this.dispose();
		}
		
	}

}
