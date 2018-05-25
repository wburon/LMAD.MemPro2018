package Interface;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Model.Client;
import Model.Table_Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PanelResultat extends JPanel implements ActionListener, MouseListener{
	
	private Table_Client tClient;
	private JTable table;
	private JButton btnRetour;
	private MainFrame mf;
	
	public PanelResultat(ArrayList<Client> listClient, MainFrame mf) {
		
		this.mf = mf;
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_north = new JPanel();
		add(panel_north, BorderLayout.NORTH);
		
		JLabel lblConsignes = new JLabel("Voici les r\u00E9sultats de votre requ\u00EAtes, \n double-cliquez sur un client dans la liste si vous souhaitez voir sa fiche.");
		panel_north.add(lblConsignes);
		
		JPanel panel_west = new JPanel();
		add(panel_west, BorderLayout.WEST);
		
		JPanel panel_south = new JPanel();
		add(panel_south, BorderLayout.SOUTH);
		
		btnRetour = new JButton("Retour");
		panel_south.add(btnRetour);
		btnRetour.addActionListener(this);
		
		JPanel panel_east = new JPanel();
		add(panel_east, BorderLayout.EAST);
		
		JPanel panel_center = new JPanel();
		add(panel_center, BorderLayout.CENTER);
		
		tClient = new Table_Client(listClient);
		table = new JTable(tClient);
		panel_center.add(new JScrollPane(table));
		table.addMouseListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnRetour){
//			mf.setContentPane(mf.getPanelAccueil());
//			mf.repaint();
//			mf.revalidate();
			mf.changePanel(mf.getPanelAccueil());
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getClickCount()==2){
			mf.setClient(tClient.getClient(table.getSelectedRow()));
			System.out.println(mf.getClient().getNom());
			mf.changePanel(new PanelClient(mf));
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
