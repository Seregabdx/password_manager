package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXParseException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * show authorisation box
 * Created by HOME on 04.12.2020.
 */
public class PasswordBox extends JDialog {
    private JPanel passwordBox;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField passwordField1;
    private JLabel passwordLabel;
    private JLabel statusBox;

    public PasswordBox() {
        setContentPane(passwordBox);
        setPreferredSize(new Dimension(565,140));
        setMinimumSize(new Dimension(565,140));
        setTitle("Enter the password");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
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
        passwordBox.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


       WindowListener activateListener = new WindowAdapter() {

            @Override
            public void windowActivated(WindowEvent e) {
                File dbfile = new File("D:/preferences.xml");
                if (!dbfile.exists()) {
                    statusBox.setText("Database file doesn't exist. \nEnter a password to create new user. \nDon't forget the password!");
                }
            }
        };
        addWindowListener(activateListener);
    }


    private void onOK() {
        class FieldEmptyException extends Exception{}
        try {
            if (passwordField1.getText().length() < 0) {
                throw new FieldEmptyException();}
            dispose();
        }
        catch (FieldEmptyException e) {
            statusBox.setText("Password can't be shorter than 0 symbols.");
        }
        catch (Exception e) {
            e.printStackTrace();}
    }

    private void onCancel() {
        // add your code here if necessary
        System.exit(0);
        dispose();
    }
    /**
     * @return  password from passwordbox
     * Created by HOME on 04.12.2020.
     */
    public String getPassword () {return passwordField1.getText();}
}
