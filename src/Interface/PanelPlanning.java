package Interface;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import DAO.InterventionDAO;
import DAO.Rendez_VousDAO;
import Model.Methode;
import Model.Rendez_Vous;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PanelPlanning extends JPanel implements ActionListener {

	// Element de gestion des dates du planning
	private HashMap<Date, Date[]> neinCurrentWeek;
	private Date[] currentWeek;
	private Date[] neinCurrentMonday;
	private Date selectedWeekMonday;
	
	// Panel/Label(titre) des différents jours de la semaine (sauf dimanche)
	private JPanel panel_Lundi_event, panel_Mardi_event, panel_Mercredi_event, panel_Jeudi_event, panel_Vendredi_event, panel_Samedi_event;
	private JLabel lblLundi, lblMardi, lblMercredi, lblJeudi, lblVendredi, lblSamedi;

	// DAO
	private Rendez_VousDAO rdvDAO;
	private InterventionDAO interDAO;
	
	// Les boutons à nombre variable
	private ArrayList<JButton> btnLunEvent = new ArrayList<>();
	private ArrayList<JButton> btnMarEvent = new ArrayList<>();
	private ArrayList<JButton> btnMerEvent = new ArrayList<>();
	private ArrayList<JButton> btnJeuEvent = new ArrayList<>();
	private ArrayList<JButton> btnVenEvent = new ArrayList<>();
	private ArrayList<JButton> btnSamEvent = new ArrayList<>();
	private ArrayList<ArrayList<JButton>> listPanelEvent;
	private JButton btnPreviousWeek, btnWeek1, btnWeek2, btnWeek3, btnWeek4, btnWeek5, btnWeek6, btnWeek7, btnWeek8, btnWeek9, btnNextWeek;
	private JButton[] btn9Week;

	// Quelques getteurs utiles
	public Date[] getCurrentWeek() {
		return currentWeek;
	}
	
	/**
	 * Create the panel.
	 */
	public PanelPlanning() {
		neinCurrentWeek = Methode.findCurrentWeekInit(new GregorianCalendar(Locale.FRANCE));
		Set<Date> nCWKeySet = neinCurrentWeek.keySet();
		neinCurrentMonday = nCWKeySet.toArray(new Date[nCWKeySet.size()]);
		Arrays.sort(neinCurrentMonday);
		this.selectedWeekMonday = neinCurrentMonday[0];

		rdvDAO = new Rendez_VousDAO();
		interDAO = new InterventionDAO();
		listPanelEvent = new ArrayList<>();
		btn9Week = new JButton[9];

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(30, 10));
		add(panel, BorderLayout.WEST);

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 30));
		add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(1, 11, 0, 0));

		btnPreviousWeek = new JButton("<");
		btnPreviousWeek.setPreferredSize(new Dimension(30, 23));
		panel_2.add(btnPreviousWeek);

		btnWeek1 = new JButton(Methode.toStringDate(neinCurrentMonday[0]));
		btnWeek1.setBackground(Color.GREEN);
		this.btn9Week[0] = (JButton) btnWeek1;
		panel_2.add(btnWeek1);

		btnWeek2 = new JButton(Methode.toStringDate(neinCurrentMonday[1]));
		this.btn9Week[1] = (JButton) btnWeek2;
		panel_2.add(btnWeek2);

		btnWeek3 = new JButton(Methode.toStringDate(neinCurrentMonday[2]));
		this.btn9Week[2] = (JButton) btnWeek3;
		panel_2.add(btnWeek3);

		btnWeek4 = new JButton(Methode.toStringDate(neinCurrentMonday[3]));
		this.btn9Week[3] = (JButton) btnWeek4;
		panel_2.add(btnWeek4);

		btnWeek5 = new JButton(Methode.toStringDate(neinCurrentMonday[4]));
		this.btn9Week[4] = (JButton) btnWeek5;
		panel_2.add(btnWeek5);

		btnWeek6 = new JButton(Methode.toStringDate(neinCurrentMonday[5]));
		this.btn9Week[5] = (JButton) btnWeek6;
		panel_2.add(btnWeek6);

		btnWeek7 = new JButton(Methode.toStringDate(neinCurrentMonday[6]));
		this.btn9Week[6] = (JButton) btnWeek7;
		panel_2.add(btnWeek7);

		btnWeek8 = new JButton(Methode.toStringDate(neinCurrentMonday[7]));
		this.btn9Week[7] = (JButton) btnWeek8;
		panel_2.add(btnWeek8);

		btnWeek9 = new JButton(Methode.toStringDate(neinCurrentMonday[8]));
		this.btn9Week[8] = (JButton) btnWeek9;
		panel_2.add(btnWeek9);

		btnNextWeek = new JButton(">");
		panel_2.add(btnNextWeek);

		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 6, 0, 0));

		JPanel panel_Lundi = new JPanel();
		panel_3.add(panel_Lundi);
		panel_Lundi.setLayout(new BorderLayout(0, 0));

		lblLundi = new JLabel("Lundi ");
		lblLundi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Lundi.add(lblLundi, BorderLayout.NORTH);

		panel_Lundi_event = new JPanel();
		panel_Lundi.add(panel_Lundi_event, BorderLayout.CENTER);

		JPanel panel_Mardi = new JPanel();
		panel_3.add(panel_Mardi);
		panel_Mardi.setLayout(new BorderLayout(0, 0));

		lblMardi = new JLabel("Mardi");
		lblMardi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Mardi.add(lblMardi, BorderLayout.NORTH);

		panel_Mardi_event = new JPanel();
		panel_Mardi.add(panel_Mardi_event, BorderLayout.CENTER);

		JPanel panel_Mercredi = new JPanel();
		panel_3.add(panel_Mercredi);
		panel_Mercredi.setLayout(new BorderLayout(0, 0));

		lblMercredi = new JLabel("Mercredi");
		lblMercredi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Mercredi.add(lblMercredi, BorderLayout.NORTH);

		panel_Mercredi_event = new JPanel();
		panel_Mercredi.add(panel_Mercredi_event, BorderLayout.CENTER);

		JPanel panel_Jeudi = new JPanel();
		panel_3.add(panel_Jeudi);
		panel_Jeudi.setLayout(new BorderLayout(0, 0));

		lblJeudi = new JLabel("Jeudi");
		lblJeudi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Jeudi.add(lblJeudi, BorderLayout.NORTH);

		panel_Jeudi_event = new JPanel();
		panel_Jeudi.add(panel_Jeudi_event, BorderLayout.CENTER);

		JPanel panel_Vendredi = new JPanel();
		panel_3.add(panel_Vendredi);
		panel_Vendredi.setLayout(new BorderLayout(0, 0));

		lblVendredi = new JLabel("Vendredi");
		lblVendredi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Vendredi.add(lblVendredi, BorderLayout.NORTH);

		panel_Vendredi_event = new JPanel();
		panel_Vendredi.add(panel_Vendredi_event, BorderLayout.CENTER);

		JPanel panel_Samedi = new JPanel();
		panel_3.add(panel_Samedi);
		panel_Samedi.setLayout(new BorderLayout(0, 0));

		lblSamedi = new JLabel("Samedi");
		lblSamedi.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Samedi.add(lblSamedi, BorderLayout.NORTH);

		panel_Samedi_event = new JPanel();
		panel_Samedi.add(panel_Samedi_event, BorderLayout.CENTER);

		remplissageEvent(this.neinCurrentWeek.get(this.selectedWeekMonday));
		
		for(int i=0; i<this.btn9Week.length; i++){
			this.btn9Week[i].addActionListener(this);
		}
		this.btnNextWeek.addActionListener(this);
		this.btnPreviousWeek.addActionListener(this);

	}

	private void remplissageEvent(Date[] currentWeek) {
		
		clearEvent();
		for (int i = 0; i < 6; i++) {
			switch (i) {
			case 0:
				// lundi
				this.lblLundi.setText("Lundi " + Methode.toStringDate(this.selectedWeekMonday));
				btnLunEvent = Methode.createButton(rdvDAO.getRdvInDate(this.selectedWeekMonday).length, "");
				int j = 0;
				for (Rendez_Vous r : rdvDAO.getRdvInDate(this.selectedWeekMonday)) {
					btnLunEvent.get(j).setText(r.toString());
					this.panel_Lundi_event.add(btnLunEvent.get(j));
					j++;
				}
				listPanelEvent.add(this.btnLunEvent);
				this.panel_Lundi_event.repaint();
				this.panel_Lundi_event.revalidate();
				break;
			case 1:
				// mardi
				this.lblMardi.setText("Mardi " + Methode.toStringDate(currentWeek[0]));
				btnMarEvent = Methode.createButton(rdvDAO.getRdvInDate(currentWeek[i-1]).length, "");
				j = 0;
				for (Rendez_Vous r : rdvDAO.getRdvInDate(currentWeek[i-1])) {
					btnMarEvent.get(j).setText(r.toString());
					this.panel_Mardi_event.add(btnMarEvent.get(j));
					j++;
				}
				listPanelEvent.add(this.btnMarEvent);
				this.panel_Mardi_event.repaint();
				this.panel_Mardi_event.revalidate();
				break;
			case 2:
				// mercredi
				this.lblMercredi.setText("Mercredi " + Methode.toStringDate(currentWeek[1]));
				btnMerEvent = Methode.createButton(rdvDAO.getRdvInDate(currentWeek[i-1]).length, "");
				j = 0;
				for (Rendez_Vous r : rdvDAO.getRdvInDate(currentWeek[i-1])) {
					btnMerEvent.get(j).setText(r.toString());
					this.panel_Mercredi_event.add(btnMerEvent.get(j));
					j++;
				}
				listPanelEvent.add(this.btnMerEvent);
				this.panel_Mercredi_event.repaint();
				this.panel_Mercredi_event.revalidate();
				break;
			case 3:
				// jeudi
				this.lblJeudi.setText("Jeudi " + Methode.toStringDate(currentWeek[2]));
				btnJeuEvent = Methode.createButton(rdvDAO.getRdvInDate(currentWeek[i-1]).length, "");
				j = 0;
				for (Rendez_Vous r : rdvDAO.getRdvInDate(currentWeek[i-1])) {
					btnJeuEvent.get(j).setText(r.toString());
					this.panel_Jeudi_event.add(btnJeuEvent.get(j));
					j++;
				}
				listPanelEvent.add(this.btnJeuEvent);
				this.panel_Jeudi_event.repaint();
				this.panel_Jeudi_event.revalidate();
				break;
			case 4:
				// vendredi
				this.lblVendredi.setText("Vendredi " + Methode.toStringDate(currentWeek[3]));
				btnVenEvent = Methode.createButton(rdvDAO.getRdvInDate(currentWeek[i-1]).length, "");
				j = 0;
				
				for (Rendez_Vous r : rdvDAO.getRdvInDate(currentWeek[i-1])) {
					btnVenEvent.get(j).setText(r.toString());
					this.panel_Vendredi_event.add(btnVenEvent.get(j));
					j++;
				}
				listPanelEvent.add(this.btnVenEvent);
				this.panel_Vendredi_event.repaint();
				this.panel_Vendredi_event.revalidate();
				break;
			default:
				// samedi
				this.lblSamedi.setText("Samedi " + Methode.toStringDate(currentWeek[4]));
				btnSamEvent = Methode.createButton(rdvDAO.getRdvInDate(currentWeek[i-1]).length, "");
				j = 0;
				for (Rendez_Vous r : rdvDAO.getRdvInDate(currentWeek[i-1])) {
					btnSamEvent.get(j).setText(r.toString());
					this.panel_Samedi_event.add(btnSamEvent.get(j));
					j++;
				}
				listPanelEvent.add(this.btnSamEvent);
				this.panel_Samedi_event.repaint();
				this.panel_Samedi_event.revalidate();
			}
		}
		for (ArrayList<JButton> a : this.listPanelEvent) {
			for (JButton e : a) {
				e.addActionListener(this);
			}
		}
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// change les btnWeek pour pouvoir cliqué sur d'autre semaines que ces neufs là
		if (arg0.getSource() == this.btnPreviousWeek) {
			GregorianCalendar gC = new GregorianCalendar();
			gC.setTime(this.neinCurrentMonday[0]);
			gC.add(Calendar.DATE, -7);
			neinCurrentWeek = Methode.findCurrentWeekInit(gC);
			Set<Date> nCWKeySet = neinCurrentWeek.keySet();
			neinCurrentMonday = nCWKeySet.toArray(new Date[nCWKeySet.size()]);
			Arrays.sort(neinCurrentMonday);
			setBtnWeek();			
		} else if (arg0.getSource() == this.btnNextWeek) {
			GregorianCalendar gC = new GregorianCalendar();
			gC.setTime(this.neinCurrentMonday[1]);
			neinCurrentWeek = Methode.findCurrentWeekInit(gC);
			Set<Date> nCWKeySet = neinCurrentWeek.keySet();
			neinCurrentMonday = nCWKeySet.toArray(new Date[nCWKeySet.size()]);
			Arrays.sort(neinCurrentMonday);
			setBtnWeek();
		} else {
			// changement de semaine
			for (int i = 0; i < this.btn9Week.length; i++) {
				if (arg0.getSource() == this.btn9Week[i]) {
					this.selectedWeekMonday = this.neinCurrentMonday[i];
					for (int j = 0; j < this.btn9Week.length; j++) {
						this.btn9Week[j].setBackground(null);
					}
					this.btn9Week[i].setBackground(Color.GREEN);
					remplissageEvent(this.neinCurrentWeek.get(this.selectedWeekMonday));
					
				}
			}
			// viewMore sur un rendez-vous
			for (ArrayList<JButton> a : this.listPanelEvent) {
				if (a.contains(arg0.getSource())) {
					lauchViewMore(a.get(a.indexOf(arg0.getSource())).getText().split("<br>")[0].split(":")[1]);
				}
			}
		}
	}

	/**
	 * Actualise les btnWeek
	 */
	private void setBtnWeek() {
		this.btnWeek1.setText(Methode.toStringDate(neinCurrentMonday[0]));
		this.btnWeek2.setText(Methode.toStringDate(neinCurrentMonday[1]));
		this.btnWeek3.setText(Methode.toStringDate(neinCurrentMonday[2]));
		this.btnWeek4.setText(Methode.toStringDate(neinCurrentMonday[3]));
		this.btnWeek5.setText(Methode.toStringDate(neinCurrentMonday[4]));
		this.btnWeek6.setText(Methode.toStringDate(neinCurrentMonday[5]));
		this.btnWeek7.setText(Methode.toStringDate(neinCurrentMonday[6]));
		this.btnWeek8.setText(Methode.toStringDate(neinCurrentMonday[7]));
		this.btnWeek9.setText(Methode.toStringDate(neinCurrentMonday[8]));
	}
	
	/**
	 * Ouvre la JOptionPane permettant d'en apprendre plus sur le rendez-vous 
	 * @param string
	 */
	private void lauchViewMore(String string) {
		int id_rdv = Integer.parseInt(string);
		Rendez_Vous rdv = rdvDAO.find(id_rdv);
		
		int choice = JOptionPane.showConfirmDialog(this, rdv.fullToSting(), "Information du le rendez-vous !", JOptionPane.OK_CANCEL_OPTION);
		System.out.println(choice);
		if(choice == 2){
			this.rdvDAO.delete(rdv);
			this.interDAO.delete(rdv.getIntervention());
			this.remplissageEvent(this.neinCurrentWeek.get(this.selectedWeekMonday));
		}
	}
	
	/**
	 * Vide les panel_Event
	 */
	private void clearEvent(){
		this.listPanelEvent.clear();
		this.panel_Lundi_event.removeAll();
		this.panel_Mardi_event.removeAll();
		this.panel_Mercredi_event.removeAll();
		this.panel_Jeudi_event.removeAll();
		this.panel_Vendredi_event.removeAll();
		this.btnVenEvent.clear();
		this.panel_Vendredi_event.validate();
		this.panel_Samedi_event.removeAll();
	}

}
