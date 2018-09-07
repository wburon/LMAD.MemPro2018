package Interface;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import DAO.ClientDAO;
import Model.Client;
import Model.Methode;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener{

	private Client client;
	private JPanel activePanel;
	private PanelAccueil panelAccueil;
	private PanelClient panelClient;
	private JMenuItem mntmAccueil;
	private JMenuItem mntmImpression;
	private String fileName;

	

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
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mntmAccueil = new JMenuItem("Accueil");
		menuBar.add(mntmAccueil);
		
		mntmImpression = new JMenuItem("Impression");
		menuBar.add(mntmImpression);
		
		mntmImpression.addActionListener(this);
		mntmAccueil.addActionListener(this);
			
		setContentPane(this.activePanel);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == mntmAccueil){
			this.changePanel(new PanelAccueil(this));
		}
		if(arg0.getSource() == mntmImpression) {
			JPanel p = this.getActivePanel();
			BufferedImage bI = new BufferedImage(p.getWidth(),p.getHeight(),BufferedImage.TYPE_BYTE_INDEXED); 
			p.paint(bI.getGraphics()); 
			try { 
			 

			JFileChooser jfc = new JFileChooser();

			int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());
				File f = new File(selectedFile.getAbsolutePath());
				FileOutputStream fichier = new FileOutputStream(f); 
				ImageIO.write(bI, "jpg", fichier);
				fichier.close();
			}
			 
			} catch (Exception e) { 
			System.out.println(e.getMessage()); 
			} 
			
		}
		
	}


}
