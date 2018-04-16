package Interface;

import javax.swing.JPanel;

import CalendarClass.CalendarWeekViewer;

import java.awt.BorderLayout;

public class PanelPlanning extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelPlanning() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new CalendarWeekViewer();
		add(panel, BorderLayout.CENTER);

	}

}
