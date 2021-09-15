package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * create new change dialog
 * Created by HOME on 04.12.2020.
 */
public class changeDlg extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel changeLabel;
    private JLabel URLLabel;
    private JTextField URLField;
    private JLabel passwordLabel;
    private JTextField passwordField;
    private JLabel RegenLabel;
    private JSpinner regenFreqField;
    private JLabel daysLabel;
    private JLabel statusLabel;

    public changeDlg(String defURL, String defPassword, String defFreq) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Change record");
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(280,260));
        pack();
        setLocationRelativeTo(null);
        URLField.setText(defURL);
        passwordField.setText(defPassword);
        regenFreqField.setValue(Integer.parseInt(defFreq));
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
            if ((URLField.getText().equals("")) || (passwordField.getText().equals("")) || (regenFreqField.getValue().equals(0))) {
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
     * @return info from change form fields
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
}
