package App;

import javax.swing.table.DefaultTableModel;

/**
 * remake tables when new password created
 * Created by HOME on 04.12.2020.
 */
public class PasGen {
    DefaultTableModel mainTableModel;
    DefaultTableModel dateTableModel;
    DefaultTableModel histTableModel;
    DefaultTableModel histTableModelInit;

    public PasGen (DefaultTableModel mTM,DefaultTableModel dTM,
                    DefaultTableModel hTM, DefaultTableModel hTMI) {
        mainTableModel = mTM;
        dateTableModel = dTM;
        histTableModel = hTM;
        histTableModelInit = hTMI;
    }
    /**
     * init models after password changing
     * @param selRow selected for regen row
     * @param PSW_LENGTH new password length
     */
    public void createNew (int selRow, int PSW_LENGTH) {
        String[] dataToChange = new String[]{mainTableModel.getValueAt(selRow,0).toString(),
                mainTableModel.getValueAt(selRow,1).toString(), new PassGen(PSW_LENGTH).generatePassword(),
                dateTableModel.getValueAt(selRow, 3).toString()};
        new ChangeRow(mainTableModel, dateTableModel, histTableModel, selRow, dataToChange).changeRow();

        histTableModelInit.setRowCount(0);

    }
}
