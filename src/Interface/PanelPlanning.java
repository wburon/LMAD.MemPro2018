package Interface;

import javax.swing.JPanel;



import java.awt.BorderLayout;

public class PanelPlanning extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelPlanning() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

	}

}
