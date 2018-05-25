package Interface;

import javax.swing.JPanel;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class PanelPlanning extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelPlanning() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(30, 10));
		add(panel, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 30));
		add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(1, 11, 0, 0));
		
		JButton btnPreviousWeek = new JButton("<");
		btnPreviousWeek.setPreferredSize(new Dimension(30, 23));
		panel_2.add(btnPreviousWeek);
		
		JButton btnWeek1 = new JButton("New button");
		panel_2.add(btnWeek1);
		
		JButton btnWeek2 = new JButton("New button");
		panel_2.add(btnWeek2);
		
		JButton btnWeek3 = new JButton("New button");
		panel_2.add(btnWeek3);
		
		JButton btnWeek4 = new JButton("New button");
		panel_2.add(btnWeek4);
		
		JButton btnWeek5 = new JButton("New button");
		panel_2.add(btnWeek5);
		
		JButton btnWeek6 = new JButton("New button");
		panel_2.add(btnWeek6);
		
		JButton btnWeek7 = new JButton("New button");
		panel_2.add(btnWeek7);
		
		JButton btnWeek8 = new JButton("New button");
		panel_2.add(btnWeek8);
		
		JButton btnWeek9 = new JButton("New button");
		panel_2.add(btnWeek9);
		
		JButton btnNextWeek = new JButton(">");
		panel_2.add(btnNextWeek);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 6, 0, 0));
		
		JPanel panel_Lundi = new JPanel();
		panel_3.add(panel_Lundi);
		panel_Lundi.setLayout(new BorderLayout(0, 0));
		
		JLabel lblLundi = new JLabel("Lundi");
		lblLundi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Lundi.add(lblLundi, BorderLayout.NORTH);
		
		JPanel panel_Lundi_event = new JPanel();
		panel_Lundi.add(panel_Lundi_event, BorderLayout.CENTER);
		
		JPanel panel_Mardi = new JPanel();
		panel_3.add(panel_Mardi);
		panel_Mardi.setLayout(new BorderLayout(0, 0));
		
		JLabel lblMardi = new JLabel("Mardi");
		lblMardi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Mardi.add(lblMardi, BorderLayout.NORTH);
		
		JPanel panel_Mardi_event = new JPanel();
		panel_Mardi.add(panel_Mardi_event, BorderLayout.CENTER);
		
		JPanel panel_Mercredi = new JPanel();
		panel_3.add(panel_Mercredi);
		panel_Mercredi.setLayout(new BorderLayout(0, 0));
		
		JLabel lblMercredi = new JLabel("Mercredi");
		lblMercredi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Mercredi.add(lblMercredi, BorderLayout.NORTH);
		
		JPanel panel_Mercredi_event = new JPanel();
		panel_Mercredi.add(panel_Mercredi_event, BorderLayout.CENTER);
		
		JPanel panel_Jeudi = new JPanel();
		panel_3.add(panel_Jeudi);
		panel_Jeudi.setLayout(new BorderLayout(0, 0));
		
		JLabel lblJeudi = new JLabel("Jeudi");
		lblJeudi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Jeudi.add(lblJeudi, BorderLayout.NORTH);
		
		JPanel panel_Jeudi_event = new JPanel();
		panel_Jeudi.add(panel_Jeudi_event, BorderLayout.CENTER);
		
		JPanel panel_Vendredi = new JPanel();
		panel_3.add(panel_Vendredi);
		panel_Vendredi.setLayout(new BorderLayout(0, 0));
		
		JLabel lblVendredi = new JLabel("Vendredi");
		lblVendredi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Vendredi.add(lblVendredi, BorderLayout.NORTH);
		
		JPanel panel_Vendredi_event = new JPanel();
		panel_Vendredi.add(panel_Vendredi_event, BorderLayout.CENTER);
		
		JPanel panel_Samedi = new JPanel();
		panel_3.add(panel_Samedi);
		panel_Samedi.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSamedi = new JLabel("Samedi");
		lblSamedi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Samedi.add(lblSamedi, BorderLayout.NORTH);
		
		JPanel panel_Samedi_event = new JPanel();
		panel_Samedi.add(panel_Samedi_event, BorderLayout.CENTER);

	}

}
