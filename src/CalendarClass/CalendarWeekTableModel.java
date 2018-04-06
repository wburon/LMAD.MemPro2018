package CalendarClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

public class CalendarWeekTableModel extends AbstractTableModel {
	 
    private String[] dayNames;
    private Calendar calendar;
 
    public CalendarWeekTableModel(Calendar calendar) {
    	this.calendar = calendar;
        dayNames = getDayNames();
    }
 
    public int getColumnCount() {
    	// ramene les 7 jours de la semaine
        return 7;
    }
 
    public int getRowCount() {
    	// ramene le nombre de ligne
    	// nombre de demi journée 
    	return 2;
    }
 
    public String getColumnName(int col) {
        return dayNames[col];
    }
 
    public Object getValueAt(int row, int col) {
        int dayOfMonth = row * 7 + col - getFirstDayOfWeek() + 2;
        if (dayOfMonth < 1 || dayOfMonth > getLastDayOfMonth()) {
            return "";
        }
        else {
            return new Integer(dayOfMonth).toString();
        }
    }
 
    public boolean isCellEditable(int row, int col) {
        return true;
    }
 
    public void setValueAt(Object value, int row, int col) {
 
    }
 
    private String[] getDayNames() {
        String[] dayNames = new String[7];
        SimpleDateFormat fmt = new SimpleDateFormat("EEEE");
        Calendar cal = Calendar.getInstance();
 
        cal.set(Calendar.DAY_OF_MONTH, 1);
 
        for (int i=0; i < 7; ++i) {
            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
            dayNames[i] = fmt.format(cal.getTime());
        }
 
        return dayNames;
    }
 
    private int getFirstDayOfWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
 
    private int getLastDayOfMonth() {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}