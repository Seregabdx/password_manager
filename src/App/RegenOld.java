package App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * find old passwords and regen it
 * Created by HOME on 04.12.2020.
 */
public class RegenOld {
    DefaultTableModel mainTableModel;
    DefaultTableModel dateTableModel;
    DefaultTableModel histTableModel;
    DefaultTableModel histTableModelInit;

    public RegenOld (DefaultTableModel mTM,DefaultTableModel dTM,
                   DefaultTableModel hTM, DefaultTableModel hTMI) {
        mainTableModel = mTM;
        dateTableModel = dTM;
        histTableModel = hTM;
        histTableModelInit = hTMI;
    }

    /**
     * @param PSW_LENGTH password length
     * @throws Exception no outdated passwords
     * find old passwords and regen it
     */
public void regenerator (int PSW_LENGTH) throws Exception {
    int confirm = JOptionPane.showOptionDialog(
            null, "Are You Sure to regenerate old password?", "Changing old password", JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, null, null);
    if (confirm == 0) {

        int rowCount = mainTableModel.getRowCount();
        ArrayList<Integer> indexArr = new ArrayList<Integer>();
        ArrayList<String> oldPass = new ArrayList<String>();
        for (int i = 0; i < rowCount; i++) {
            SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy");
            Calendar cal = new GregorianCalendar();
            String cDate = form.format(cal.getTime());
            Date curDate = form.parse(cDate);
            Date checkDate = null;
            for (int k = 0; k < rowCount; k++) {
                checkDate = form.parse(dateTableModel.getValueAt(k, 2).toString());
                if (curDate.compareTo(checkDate) == 1) {
                    indexArr.add(i);
                    oldPass.add(mainTableModel.getValueAt(i, 2).toString());
                    new PasGen(mainTableModel,dateTableModel,histTableModel, histTableModelInit).createNew(i, PSW_LENGTH);
                }
            }
        }
        if (indexArr.size() == 0) {
            JOptionPane.showMessageDialog(null, "There is no outdated password", "Nothing to change!", JOptionPane.ERROR_MESSAGE);
        } else {
            regenOldDlg regDlg = new regenOldDlg(indexArr, mainTableModel, oldPass);
        }
    }
}

}
