package App;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;


/**
 * Created by HOME on 04.12.2020.
 */
public class MainForm extends JFrame {
    private JPanel root;
    private JButton saveXMLButton;
    private JButton exitButton;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JToolBar.Separator separator2;
    private JButton regenActiveButton;
    private JButton regenOldButton;
    private JButton regenAllButton;
    private JToolBar menuToolBar;
    private JTable dbTable;
    private JScrollPane tableScroll;
    private JTable dateTable;
    private JTable histTable;
    private JScrollPane histScroll;
    private JButton savePDFButton;
    private JButton saveButton;
    private JButton openButton;
    private JButton openXMLButton;
    private JButton clearHistoryButton;
    private JPanel mainToolPanel;
    private JPanel mainTablePannel;
    private JPanel histPannel;
    private JPanel datePannel;
    private JButton newButton;
    private JButton MThreadButton;

    private DefaultTableModel mainTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private DefaultTableModel dateTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel dateTableModelInit = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private DefaultTableModel histTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel histTableModelInit = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private static String userPassword;

    private static final int PSW_LENGTH = 10;

    public class Database {

        public Connection con;
        Statement statement = null;

        public void connect() throws Exception {
            if (con != null) return;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/passwords", "root", userPassword.toString());
            } catch (SQLException sql) {
                System.out.println("SQLException: " + sql.getMessage());
                System.out.println("SQLState: " + sql.getSQLState());
                System.out.println("Erro: " + sql.getErrorCode());
                System.out.println("StackTrace: " + sql.getStackTrace());
                throw new Exception("No database");
            } catch (ClassNotFoundException e) {
                throw new Exception("No database");
            }
        }

        public void close() {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static Logger log = Logger.getLogger(Main.class.getName());

    public MainForm() {
        super("PasMan");
        log.info("Start app");
        setPreferredSize(new Dimension(1280, 720));
        setMinimumSize(new Dimension(480, 320));
        mainToolPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        mainTablePannel.setBorder(new EmptyBorder(2, 2, 2, 2));
        histPannel.setBorder(new EmptyBorder(2, 2, 2, 2));
        datePannel.setBorder(new EmptyBorder(2, 2, 2, 2));
        changeButton.setEnabled(false);
        deleteButton.setEnabled(false);
        clearHistoryButton.setEnabled(false);
        regenActiveButton.setEnabled(false);
        regenAllButton.setEnabled(false);
        regenOldButton.setEnabled(false);
        pack();
        setLocationRelativeTo(null);
        setContentPane(root);
        setVisible(true);
        PasswordBox startBox = new PasswordBox();
        startBox.pack();
        startBox.setVisible(true);
        userPassword = startBox.getPassword();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        ArrayList<String> mainTableCols = new ArrayList<String>();
        mainTableCols.add("ID");
        mainTableCols.add("URL");
        mainTableCols.add("Password");
        initTableModel(mainTableModel, mainTableCols);
        dbTable.setModel(mainTableModel);
        dbTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ArrayList<String> dateTableCols = new ArrayList<String>();
        dateTableCols.add("ID");
        dateTableCols.add("Create date");
        dateTableCols.add("ReGen date");
        dateTableCols.add("ReGen frequency");
        initTableModel(dateTableModel, dateTableCols);
        dateTableCols.remove(0);
        initTableModel(dateTableModelInit, dateTableCols);
        dateTable.setModel(dateTableModelInit);

        ArrayList<String> histTableCols = new ArrayList<String>();
        histTableCols.add("ID");
        histTableCols.add("Old password");
        initTableModel(histTableModel, histTableCols);
        histTableCols.remove(0);
        initTableModel(histTableModelInit, histTableCols);
        histTable.setModel(histTableModelInit);

        log.info("Empty tables created");
        /**
         * exit listener
         */
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to Close Application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    log.info("Exit program");
                    System.exit(0);
                }
            }
        };
        addWindowListener(exitListener);

        saveXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "XML Files", "xml");
                chooser.setFileFilter(filter);
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        log.info("Start saving XML");
                        File savedfile = new File(makeFileName(chooser.getSelectedFile().toString(), ".xml"));
                        DocumentBuilderFactory f = DocumentBuilderFactory
                                .newInstance();
                        DocumentBuilder builder = f.newDocumentBuilder();
                        Document doc = builder.newDocument();

                        Element root = doc.createElement("root");
                        Element tableEl1 = doc.createElement("mainTable");
                        doc = new  SaveXML(mainTableModel,tableEl1,doc).saveTableToFXML();
                        Element tableEl2 = doc.createElement("dateTable");
                        doc = new  SaveXML(dateTableModel,tableEl2,doc).saveTableToFXML();
                        Element tableEl3 = doc.createElement("histTable");
                        doc = new  SaveXML(histTableModel,tableEl3,doc).saveTableToFXML();

                        root.appendChild(tableEl1);
                        root.appendChild(tableEl2);
                        root.appendChild(tableEl3);
                        doc.appendChild(root);

                        TransformerFactory tFactory = TransformerFactory
                                .newInstance();
                        Transformer transformer = tFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(savedfile);
                        log.info("Try to put info into XML file");
                        transformer.transform(source, result);
                    }
                    catch (Exception exc) {
//                        JOptionPane.showMessageDialog(null, "Unable to open file.", "Error", JOptionPane.ERROR_MESSAGE);
                        exc.printStackTrace();
                        log.debug("Error saving XML",exc);
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to Close Application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    log.info("Exit program");
                    System.exit(0);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Added new element");
                addDlg addrecord = new addDlg();
                addrecord.pack();
                addrecord.setVisible(true);
                String[] dataToAdd = addrecord.getRecord();
                if (dataToAdd != null) {
                    dataToAdd[0] = UUID.randomUUID().toString();
                    mainTableModel.addRow(new Object[]{dataToAdd[0], dataToAdd[1], dataToAdd[2]});
                    SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy");
                    Calendar cal = new GregorianCalendar();
                    String curDateStr = form.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(dataToAdd[3]));
                    String nextDateStr = form.format(cal.getTime());
                    dateTableModel.addRow(new Object[]{dataToAdd[0], curDateStr, nextDateStr, dataToAdd[3]});
                    histTableModel.addRow(new Object[]{dataToAdd[0], dataToAdd[2]});
                }
                if (dbTable.getRowCount() > 0) {
                    dbTable.setRowSelectionInterval(dbTable.getRowCount() - 1, dbTable.getRowCount() - 1);
                }
            }
        });

        openXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "XML Files", "xml");
                chooser.setFileFilter(filter);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        log.info("Try to open XML");
                        mainTableModel.setRowCount(0);
                        dateTableModel.setRowCount(0);
                        histTableModel.setRowCount(0);
                        File openfile = chooser.getSelectedFile();
                        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = f.newDocumentBuilder();
                        Document doc = builder.parse(openfile);

                        NodeList nlist = doc.getElementsByTagName("mainTable");
                        mainTableModel = new OpenXML(mainTableModel,nlist.item(0),0).initTable();
                        nlist = doc.getElementsByTagName("dateTable");
                        dateTableModel = new OpenXML(dateTableModel,nlist.item(0),1).initTable();
                        nlist = doc.getElementsByTagName("histTable");
                        histTableModel = new OpenXML(histTableModel,nlist.item(0),2).initTable();

                        if (dbTable.getRowCount() > 0) {
                            int selRow = 0;
                            dbTable.setRowSelectionInterval(selRow, selRow);
                            setSecondaryTable(dateTableModel, dateTableModelInit, dbTable.getValueAt(selRow, 0).toString(), 0);
                            setSecondaryTable(histTableModel, histTableModelInit, dbTable.getValueAt(selRow, 0).toString(), 1);
                        }
                    } catch (SAXException  exc) {
                        JOptionPane.showMessageDialog(null, "Unable to open file.", "Error", JOptionPane.ERROR_MESSAGE);
                        log.debug("XML parcer down",exc);
                    }
                    catch (Exception  exc) {
                        JOptionPane.showMessageDialog(null, "Unable to open file.", "Error", JOptionPane.ERROR_MESSAGE);
                        log.debug("Error opening XML",exc);
                    }
                }
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                class SelectEmptyException extends Exception {
                }
                try {
                    log.info("One row changed");
                    int changingRow = dbTable.getSelectedRow();
                    if (changingRow == -1) {
                        throw new SelectEmptyException();
                    }
                    int dateRow = 0;
                    for (int i = 0; i < dateTableModel.getRowCount(); i++) {
                        if (dateTableModel.getValueAt(i, 0).equals(dbTable.getSelectedRow())) {
                            dateRow = i;
                            break;
                        }
                    }
                    changeDlg changerecord = new changeDlg(mainTableModel.getValueAt(changingRow, 1).toString(),
                            mainTableModel.getValueAt(changingRow, 2).toString(), dateTableModel.getValueAt(dateRow, 3).toString());
                    changerecord.pack();
                    changerecord.setVisible(true);
                    String[] dataToChange = changerecord.getRecord();
                    new ChangeRow(mainTableModel, dateTableModel, histTableModel, changingRow, dataToChange).changeRow();

                    histTableModelInit.setRowCount(0);
                    setSecondaryTable(histTableModel, histTableModelInit, dbTable.getValueAt(changingRow, 0).toString(), 1);

                    dbTable.setRowSelectionInterval(changingRow, changingRow);

                } catch (SelectEmptyException exc) {
                    JOptionPane.showMessageDialog(null, "Select a Row", "No row selected!", JOptionPane.ERROR_MESSAGE);
                    log.debug("One row not changed. Empty selection");
                }
            }
        });

        ListSelectionModel selModel = dbTable.getSelectionModel();
        /**
         * select table row listener
         */
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (dbTable.getSelectedRow() != -1) {
                    changeButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    regenActiveButton.setEnabled(true);
                    clearHistoryButton.setEnabled(true);
                    regenAllButton.setEnabled(true);
                    regenOldButton.setEnabled(true);
                    setSecondaryTable(dateTableModel, dateTableModelInit, dbTable.getValueAt(dbTable.getSelectedRow(), 0).toString(), 0);
                    setSecondaryTable(histTableModel, histTableModelInit, dbTable.getValueAt(dbTable.getSelectedRow(), 0).toString(), 1);
                } else {
                    changeButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    regenActiveButton.setEnabled(false);
                    clearHistoryButton.setEnabled(false);
                    regenAllButton.setEnabled(false);
                    regenOldButton.setEnabled(false);
                }
            }
        });



        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to Delete element?", "Dalete Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                int selRow = dbTable.getSelectedRow();
                if (confirm == 0) {
                    deleteRow(selRow);
                }
                if (dbTable.getRowCount() > 0) {
                    selRow = 0;
                    dbTable.setRowSelectionInterval(0, 0);
                    setSecondaryTable(dateTableModel, dateTableModelInit, dbTable.getValueAt(selRow, 0).toString(), 0);
                    setSecondaryTable(histTableModel, histTableModelInit, dbTable.getValueAt(selRow, 0).toString(), 1);
                }
                log.info("One row deleted");
            }
        });

        clearHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to clear history?", "Clear Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    String[] selRow = {dbTable.getValueAt((dbTable.getSelectedRow()), 0).toString(),
                            dbTable.getValueAt((dbTable.getSelectedRow()), 2).toString()};
                    histTableModelInit.setRowCount(0);
                    deleteSecondaryRow(histTableModel, dbTable.getSelectedRow());
                    histTableModel.addRow(new Object[]{selRow[0], selRow[1]});
                    histTableModelInit.addRow(new Object[]{selRow[1]});
                }
                if (dbTable.getRowCount() < 0) {
                    dbTable.setRowSelectionInterval(dbTable.getSelectedRow(), dbTable.getSelectedRow());
                }
                log.info("History for selected row cleared");
            }

        });

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to create new doc without saving?", "Save Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    histTableModel.setRowCount(0);
                    histTableModelInit.setRowCount(0);
                    dateTableModel.setRowCount(0);
                    dateTableModelInit.setRowCount(0);
                    mainTableModel.setRowCount(0);
                }
                log.info("New doc opened");
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Try to connect SQL database");
                try {
                    MainForm.Database db = new MainForm.Database();
                    new OpenSQL(db,mainTableModel,dateTableModel,histTableModel,dateTableModelInit,histTableModelInit).connectDB();
                }catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Connection to database faled.", "Connection error!", JOptionPane.ERROR_MESSAGE);
                    log.debug("Connection error", exc);
                }
                if (dbTable.getRowCount() > 0) {
                    dbTable.setRowSelectionInterval(0, 0);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Try to connect SQL database");
                Database db = new Database();
                int selRow = dbTable.getSelectedRow();
                try {
                    new SaveSQL(db,mainTableModel,dateTableModel,histTableModel,dateTableModelInit,histTableModelInit).commitDB();
                    if (dbTable.getRowCount() > 0) {
                        dbTable.setRowSelectionInterval(selRow, selRow);
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                    log.debug("Error downloading data into SQL db", exc);
                }
                db.close();
            }
        });

        savePDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
                chooser.setFileFilter(filter);
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        log.info("Making PDF report");
                        File savedfile = chooser.getSelectedFile();
                        new PdfOut().output(new File(makeFileName(savedfile.toString(), ".pdf")), mainTableModel, dateTableModel, histTableModel);


                    } catch (Exception exc) {
                        exc.printStackTrace();
                        log.debug("Error making PDF report", exc);
                    }
                    if (dbTable.getRowCount() > 0) {
                        dbTable.setRowSelectionInterval(dbTable.getSelectedRow(), dbTable.getSelectedRow());
                    }
                }
            }
        });

        regenActiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dbTable.getRowCount() > 0) {
                    log.info("Regenerating selected password");
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Are You Sure to generate new password?", "Changing password", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        int selRow = dbTable.getSelectedRow();
                        new PasGen(mainTableModel,dateTableModel,histTableModel, histTableModelInit).createNew(selRow, PSW_LENGTH);
                        setSecondaryTable(histTableModel, histTableModelInit, mainTableModel.getValueAt(selRow, 0).toString(), 1);
                    }
                }
            }
        });

        regenAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dbTable.getRowCount() > 0) {
                    log.info("Regenerating all passwords");
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Are You Sure to regenerate all password?", "Changing all password", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        for (int i = 0; i<dbTable.getRowCount(); i++) {
                            new PasGen(mainTableModel,dateTableModel,histTableModel, histTableModelInit).createNew(i, PSW_LENGTH);
                        }
                    }
                }
            }
        });

        regenOldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (dbTable.getRowCount() > 0) {
                        log.info("Regenerating old passwords");
                        new RegenOld(mainTableModel, dateTableModel,histTableModel, histTableModelInit).regenerator(PSW_LENGTH);
                        dbTable.setRowSelectionInterval(0, 0);
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                    log.debug("Regenerating old passwords error",exc);
                }
            }
        });
        MThreadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MultiThread();
            }
        });
    }



//    private String getPswFromXML(File dbfile){
//        String idealPsw;
//        try {
//            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = f.newDocumentBuilder();
//            Document doc = builder.parse(dbfile);
//
//            NodeList nlist = doc.getElementsByTagName("password");
//            Node elem = nlist.item(0);
//            idealPsw = elem.getTextContent();
//            return idealPsw;
//        }
//        catch (Exception e) {e.printStackTrace(); return "Err";}
//
//    }
    /**
     * @param initTableModel model to initialize
     * @param tableCols</> initializing data
     */
    private void initTableModel(DefaultTableModel initTableModel, ArrayList<String> tableCols)  {
        for (int i = 0; i < tableCols.size(); i++) {
            initTableModel.addColumn(tableCols.get(i));
        }

    }
    /**
     * @param rowCount num of deleting row
     */
    private void deleteRow(int rowCount) {
        histTableModelInit.setRowCount(0);
        deleteSecondaryRow(histTableModel, rowCount);
        dateTableModelInit.setRowCount(0);
        deleteSecondaryRow(dateTableModel, rowCount);

        mainTableModel.removeRow(rowCount);
    }
    /**
     * @param destModel model to delete row
     * @param rcount row in a model to delete
     */
    private void deleteSecondaryRow(DefaultTableModel destModel, int rcount) {
        String key = dbTable.getValueAt(rcount, 0).toString();
        int maxRows = destModel.getRowCount();
        for (int i = maxRows - 1; i >= 0; i--) {
            if (destModel.getValueAt(i, 0).equals(key)) {
                destModel.removeRow(i);
            }
        }
    }
    /**
     * @param source name with or without ext
     * @param extension extension
     */
    private String makeFileName (String source, String extension) {
        int slen = source.length();
        int elen = extension.length();
        if (slen<=elen) {return source+extension;}
        char[] cmpr = new char[elen];
        source.getChars(slen-elen,slen,cmpr,0);
        String cmprS = new String(cmpr);
        if (extension.equals(cmprS)) {return source;}
        return (source+extension);
    }
    /**
     * @param sourceModel model for initialize may be Date or Hist
     * @param destModel model to out on a screen
     * @param key UID for searching
     * @param type Date 0 or Hist 1
     */
    private void setSecondaryTable(DefaultTableModel sourceModel, DefaultTableModel destModel, String key, int type) {
        destModel.setRowCount(0);
        for (int i = 0; i < sourceModel.getRowCount(); i++) {
            if (sourceModel.getValueAt(i, 0).equals(key)) {
                switch (type) {
                    case 0:
                        destModel.addRow(new Object[]{sourceModel.getValueAt(i, 1), sourceModel.getValueAt(i, 2), sourceModel.getValueAt(i, 3)});
                        break;
                    case 1:
                        destModel.addRow(new Object[]{sourceModel.getValueAt(i, 1)});
                        break;
                }
            }
        }
    }

}
