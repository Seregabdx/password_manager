package App;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

/**
 * commit current base to SQL DB
 * Created by HOME on 04.12.2020.
 */
public class SaveSQL {
    MainForm.Database db;
    DefaultTableModel mainTableModel;
    DefaultTableModel dateTableModel;
    DefaultTableModel histTableModel;
    DefaultTableModel dateTableModelInit;
    DefaultTableModel histTableModelInit;

    public SaveSQL (MainForm.Database db1, DefaultTableModel mTM,DefaultTableModel dTM,
                    DefaultTableModel hTM, DefaultTableModel dTMI, DefaultTableModel hTMI) {
        db = db1;
        mainTableModel = mTM;
        dateTableModel = dTM;
        histTableModel = hTM;
        dateTableModelInit = dTMI;
        histTableModelInit = hTMI;
    }
    /**
     * clear Sql db and refresh it
     * @throws Exception connection to db failed
     */
    public void commitDB () throws Exception {
        db.connect();
        db.statement = db.con.createStatement();
        String query = "TRUNCATE TABLE passwords;";
        db.statement.executeUpdate(query);
        query = "DELETE FROM passdate;";
        db.statement.executeUpdate(query);
        query = "DELETE FROM passhist;";
        db.statement.executeUpdate(query);
        saveMainTableToSQL(db);
        saveDateTableToSQL(db);
        saveHistTableToSQL(db);
        db.close();
    }

    private void saveMainTableToSQL(MainForm.Database db) {
        try {
            String query;
            for (int i = 0; i < mainTableModel.getRowCount(); i++) {
                query = "INSERT INTO passwords (idpasswords, url, password) VALUES ('" +
                        mainTableModel.getValueAt(i, 0).toString() + "','" + mainTableModel.getValueAt(i, 1).toString() +
                        "','" + mainTableModel.getValueAt(i, 2).toString() + "');";
                db.statement.executeUpdate(query);
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private void saveDateTableToSQL(MainForm.Database db) {
        try {
            String query;
            for (int i = 0; i < dateTableModel.getRowCount(); i++) {
                query = "INSERT INTO passdate (idpassdate, createdate, regendate, regenfreq) VALUES ('" +
                        dateTableModel.getValueAt(i, 0).toString() + "' , '" + dateTableModel.getValueAt(i, 1).toString() +
                        "' , '" + dateTableModel.getValueAt(i, 2).toString() + "' , '" + dateTableModel.getValueAt(i, 3).toString() + "');";
                db.statement.executeUpdate(query);
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private void saveHistTableToSQL(MainForm.Database db) {
        try {
            String query;
            for (int i = 0; i < histTableModel.getRowCount(); i++) {
                query = "INSERT INTO passhist (idpassHist, oldpass) VALUES ('" +
                        histTableModel.getValueAt(i, 0).toString() + "','" + histTableModel.getValueAt(i, 1).toString() + "');";
                db.statement.executeUpdate(query);
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

}
