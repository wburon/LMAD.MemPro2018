package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;

import DAO.ClientDAO;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private PanelAccueil panelAccueil;
	private PanelClient panelClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		
		panelAccueil = new PanelAccueil(this);
		init();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
	}
	
	public void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
//		ClientDAO clDAO = new ClientDAO();
//		this.panelClient = new PanelClient(clDAO.find(0));
		setContentPane(panelAccueil);
	}

}
