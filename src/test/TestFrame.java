package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class TestFrame extends JFrame implements MouseListener{

	private JPanel contentPane;
	private JTable table;
	private JTextArea textArea;

	private String[] entete = {"Col1", "Col2", "Col3"};
	
	private Object[][] data = {{1,2,3},{4,5,6},{7,8,9}};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
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
	public TestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		
		textArea = new JTextArea();
		panel_3.add(textArea);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, BorderLayout.CENTER);
		
		
		table = new JTable(data, entete);
		panel_4.add(new JScrollPane(table));
		
		table.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		textArea.setText("(mouseClic) nombre de clique : "+e.getClickCount()); 
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		textArea.setText("(mousePressed) nombre de clique : "+e.getClickCount());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		textArea.setText("chiffre sélectionné : "+data[table.getSelectedRow()][0]+", "+data[table.getSelectedRow()][1]+", "+data[table.getSelectedRow()][2]);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		textArea.setText("(mouseEntered) nombre de clique : "+e.getClickCount());
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		textArea.setText("(mouseExited) nombre de clique : "+e.getClickCount());
		
	}

}
