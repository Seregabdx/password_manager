package App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * connecting to db and get data
 * Created by HOME on 04.12.2020.
 */
public class OpenSQL {
    MainForm.Database db;
    DefaultTableModel mainTableModel;
    DefaultTableModel dateTableModel;
    DefaultTableModel histTableModel;
    DefaultTableModel dateTableModelInit;
    DefaultTableModel histTableModelInit;

    public OpenSQL (MainForm.Database db1, DefaultTableModel mTM,DefaultTableModel dTM,
                    DefaultTableModel hTM, DefaultTableModel dTMI, DefaultTableModel hTMI) {
        db = db1;
        mainTableModel = mTM;
        dateTableModel = dTM;
        histTableModel = hTM;
        dateTableModelInit = dTMI;
        histTableModelInit = hTMI;
    }




    /**
     * connect and get
     * @throws Exception connection failed
     */
public void connectDB () throws Exception {
    db.connect();

    mainTableModel.setRowCount(0);
    dateTableModel.setRowCount(0);
    dateTableModelInit.setRowCount(0);
    histTableModel.setRowCount(0);
    histTableModelInit.setRowCount(0);
    db.statement = db.con.createStatement();
    initMainTableFromSQL(db);
    initDateTableFromSQL(db);
    initHistTableFromSQL(db);



    db.close();
}

    private void initMainTableFromSQL(MainForm.Database db) {
        try {
            String selectTableSQL = "SELECT idpasswords, url, password from passwords";
            ResultSet rs = db.statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                String idpassword = rs.getString("idpasswords");
                String url = rs.getString("url");
                String password = rs.getString("password");
                mainTableModel.addRow(new Object[]{idpassword, url, password});
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private void initDateTableFromSQL(MainForm.Database db) {
        try {
            String selectTableSQL = "SELECT idpassdate, createdate, regendate, regenfreq from passdate";
            ResultSet rs = db.statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                String idpassword = rs.getString("idpassdate");
                String created = rs.getString("createdate");
                String regen = rs.getString("regendate");
                String freq = rs.getString("regenfreq");
                dateTableModel.addRow(new Object[]{idpassword, created, regen, freq});
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private void initHistTableFromSQL(MainForm.Database db) {
        try {
            String selectTableSQL = "SELECT idpassHist, oldpass from passhist";
            ResultSet rs = db.statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                String idpassword = rs.getString("idpassHist");
                String oldpass = rs.getString("oldpass");
                histTableModel.addRow(new Object[]{idpassword, oldpass});
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }
}
