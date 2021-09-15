package App;

import org.xml.sax.SAXParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * create new add dialog
 * Created by HOME on 04.12.2020.
 */
public class addDlg extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel addLabel;
    private JTextField URLField;
    private JTextField passwordField;
    private JLabel URLLabel;
    private JLabel passwordLabel;
    private JLabel RegenLabel;
    private JLabel statusLabel;
    private JLabel daysLabel;
    private JSpinner regenFreqField;
    private JLabel addPswLabel;


    public addDlg() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Add new record");
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(280,260));
        pack();
        setLocationRelativeTo(null);
        regenFreqField.setValue(30);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        class FieldEmptyException extends Exception{}

        try {
            if (Integer.parseInt(regenFreqField.getValue().toString()) < 0) {
                throw new FieldEmptyException();}
            if (isEmpty(URLField.getText(),passwordField.getText(),regenFreqField.getValue().toString())) {
                throw new FieldEmptyException();}
            dispose();
        }
        catch (FieldEmptyException e) {
            statusLabel.setText("One of field is empty.");}
        catch (Exception e) {
            e.printStackTrace();}


    }
    boolean isCancel = false;
    private void onCancel() {
        // add your code here if necessary
        isCancel = true;
        dispose();
    }
    /**
     * @return record from add form fields
     * Created by HOME on 04.12.2020.
     */
    public String[] getRecord () {
        if (isCancel) {
            return null;
        }
        else {
            String[] datastr = {"", URLField.getText(), passwordField.getText(), regenFreqField.getValue().toString()};
            return datastr;
        }
    }

    private boolean isEmpty (String p1, String p2, String p3) {
        return (p1.equals("") || p2.equals("") || p3.equals("0"));

    }
}
