package CalendarClass;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class CalendarWeekViewer extends JPanel implements ActionListener {
	 
    Calendar calendar;
    DateFormat dateFormatter;
 
    CalendarWeekTableModel tableModel;
 
    JLabel monthLabel;
    JTable table;
    JButton prevButton;
    JButton nextButton;
 
 
    public CalendarWeekViewer() {
        super();
 
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
 
        dateFormatter = new SimpleDateFormat("MMMM yyyy");
 
        createComponents();
    }
 
    private void createComponents() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
 
        monthLabel = new JLabel(dateFormatter.format(calendar.getTime()));
        monthLabel.setFont(new Font(null, Font.PLAIN, 36));
        monthLabel.setVerticalAlignment(JLabel.CENTER);
        monthLabel.setHorizontalAlignment(JLabel.CENTER);
 
        tableModel = new CalendarWeekTableModel(calendar);
        table = new JTable((TableModel)tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(700, 600));
        table.setDefaultRenderer(String.class, new CalendarDayRenderer());
        table.setRowHeight(200);
        TableColumnModel tableColumnModel = table.getColumnModel();
        for (int i=0; i < tableColumnModel.getColumnCount(); ++i) {
            TableColumn tableColumn = tableColumnModel.getColumn(i);
            tableColumn.setPreferredWidth(200);
        }
 
        JScrollPane tableScrollPane = new JScrollPane(table);
 
        prevButton = new JButton("<<");
        prevButton.addActionListener(this);
 
        nextButton = new JButton(">>");
        nextButton.addActionListener(this);
 
        JPanel btnPanel = new JPanel();
        btnPanel.add(prevButton);
        btnPanel.add(nextButton);
 
        getRootPane().add(monthLabel, BorderLayout.NORTH);
        getRootPane().add(tableScrollPane, BorderLayout.CENTER);
        getRootPane().add(btnPanel, BorderLayout.SOUTH);
    }
 
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevButton) {
            int curMonth = calendar.get(Calendar.MONTH);
            if (curMonth == Calendar.JANUARY) {
                int curYear = calendar.get(Calendar.YEAR);
                calendar.set(Calendar.YEAR, curYear - 1);
                calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            }
            else {
                calendar.set(Calendar.MONTH, curMonth - 1);
            }
            monthLabel.setText(dateFormatter.format(calendar.getTime()));
            tableModel.fireTableDataChanged();
        }
        else if (e.getSource() == nextButton) {
            int curMonth = calendar.get(Calendar.MONTH);
            if (curMonth == Calendar.DECEMBER) {
                int curYear = calendar.get(Calendar.YEAR);
                calendar.set(Calendar.YEAR, curYear + 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
            }
            else {
                calendar.set(Calendar.MONTH, curMonth + 1);
            }
            monthLabel.setText(dateFormatter.format(calendar.getTime()));
            tableModel.fireTableDataChanged();
        }
    }

}
