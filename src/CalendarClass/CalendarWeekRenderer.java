package CalendarClass;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class CalendarWeekRenderer extends JLabel implements TableCellRenderer {
	 
    public CalendarWeekRenderer() {
        super();
        setFont(new Font(null, Font.PLAIN, 28));
        setVerticalAlignment(JLabel.TOP);
        setHorizontalAlignment(JLabel.LEFT);
        setOpaque(true);
    }
 
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        setText((String)value);
        return this;
    }
}
