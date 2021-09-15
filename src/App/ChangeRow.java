package App;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * changing row in a main table with other tables
 * Created by HOME on 04.12.2020.
 */
public class ChangeRow {

    DefaultTableModel mainTableModelChange;
    DefaultTableModel dateTableModelChange;
    DefaultTableModel histTableModelChange;
    int chRow;
    String[] dataChange;

    /**
     * copy three main table models, selected row and string to change from mainframe
     */
    public ChangeRow (DefaultTableModel mTM,DefaultTableModel dTM, DefaultTableModel hTM, int cR, String[] dC){
        mainTableModelChange = mTM;
        dateTableModelChange = dTM;
        histTableModelChange = hTM;
        chRow = cR;
        dataChange = dC;
        }
    /**
     * recompile new row from source row and put it into models
     */
    public void changeRow() {
        if (dataChange != null) {
            dataChange[0] = mainTableModelChange.getValueAt(chRow, 0).toString();
            mainTableModelChange.removeRow(chRow);
            mainTableModelChange.insertRow(chRow, new Object[]{dataChange[0], dataChange[1], dataChange[2]});
            SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy");
            Calendar cal = new GregorianCalendar();
            String curDateStr = form.format(cal.getTime());
            cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(dataChange[3]));
            String nextDateStr = form.format(cal.getTime());
            for (int i = 0; i < dateTableModelChange.getRowCount(); i++) {
                if (dateTableModelChange.getValueAt(i, 0).equals(mainTableModelChange.getValueAt(chRow,0))) {
                    dateTableModelChange.removeRow(i);
                    dateTableModelChange.insertRow(i, new Object[]{dataChange[0], curDateStr, nextDateStr, dataChange[3]});
                }
            }
//            dateTableModelChange.addRow(new Object[]{dataChange[0], curDateStr, nextDateStr, dataChange[3]});
            histTableModelChange.addRow(new Object[]{dataChange[0], dataChange[2]});

        }
    }
}
