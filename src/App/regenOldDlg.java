package App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * show table of regenerated passwords
 * Created by HOME on 04.12.2020.
 */
public class regenOldDlg extends JDialog {
    private JPanel contentPane;
    private JTable oldPswTable;

    private DefaultTableModel pswTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public regenOldDlg(ArrayList<Integer> indexArr, DefaultTableModel mainTableModel, ArrayList<String> oldPass) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Regenerated passwords");
        setMinimumSize(new Dimension(280,260));
        pswTableModel.addColumn("URL");
        pswTableModel.addColumn("Old password");
        pswTableModel.addColumn("New password");
        oldPswTable.setModel(pswTableModel);
        for (int i = 0; i<indexArr.size(); i++) {
            pswTableModel.addRow(new Object[]{mainTableModel.getValueAt(indexArr.get(i),1).toString(), oldPass.get(i),
                    mainTableModel.getValueAt(indexArr.get(i),2).toString()});
        }
        setLocationRelativeTo(null);
        pack();
        setVisible(true);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
}
