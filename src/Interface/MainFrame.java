package Interface;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DAO.ClientDAO;
import Model.Client;
import Model.Methode;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private Client client;
	private JPanel activePanel;
	private PanelAccueil panelAccueil;
	private PanelClient panelClient;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final MainFrame frame = new MainFrame();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter(){
			            public void windowClosing(WindowEvent e){
			                  int reponse = JOptionPane.showConfirmDialog(frame,
			                                       "Voulez-vous quitter l'application ?",
			                                       "Confirmation",
			                                       JOptionPane.YES_NO_OPTION,
			                                       JOptionPane.QUESTION_MESSAGE);
			                  if (reponse==JOptionPane.YES_OPTION){
			                          try {
										Methode.sauvegarde();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
			                  }
			            }
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		panelAccueil = new PanelAccueil(this);
		ClientDAO cDAO = new ClientDAO();
		this.setClient(cDAO.find(1));
		panelClient = new PanelClient(this);
		
		this.activePanel = panelAccueil;
		
		
		
		
		init();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
		
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public JPanel getActivePanel() {
		return activePanel;
	}

	public void setActivePanel(JPanel activePanel) {
		this.activePanel = activePanel;
	}
	public PanelAccueil getPanelAccueil(){
		return panelAccueil;
	}
	public void changePanel(JPanel newPanel){
		setContentPane(newPanel);
		repaint();
		revalidate();
		setActivePanel(newPanel);
	}
	
	public void init(){
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
			
		setContentPane(this.activePanel);
	}

}
