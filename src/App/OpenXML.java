package App;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * opening xml
 * Created by HOME on 04.12.2020.
 */
public class OpenXML {
    DefaultTableModel tabModel;
    Node nList;
    int tabNo;

    public OpenXML(DefaultTableModel tM, Node nL, int tN) {
        tabModel = tM;
        nList = nL;
        tabNo = tN;
    }
    /**
     * @return table uses tagged node
     * @throws Exception parse error
     */
    public DefaultTableModel initTable() throws Exception {
        NodeList childs = nList.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            String[] rowList = new String[4];
            NodeList childsOfChilds = childs.item(i).getChildNodes();
            for (int j = 0; j < childsOfChilds.getLength(); j++) {
                rowList[j] = (childsOfChilds.item(j).getChildNodes().item(0).getNodeValue());
            }
            switch (tabNo) {
                case 0:
                    initMainTableRow(tabModel, rowList);
                    break;
                case 1:
                    initDateTableRow(tabModel, rowList);
                    break;
                case 2:
                    initHistTableRow(tabModel, rowList);
                    break;
            }
        }

        return tabModel;
    }

    private void initMainTableRow(DefaultTableModel tabModel, String[] rowList) throws Exception {
        tabModel.addRow(new Object[]{rowList[0], rowList[1], rowList[2]});
    }

    private void initDateTableRow(DefaultTableModel tabModel, String[] rowList) throws Exception {
        tabModel.addRow(new Object[]{rowList[0], rowList[1],
                rowList[2], rowList[3]});
    }

    private void initHistTableRow(DefaultTableModel tabModel, String[] rowList) throws Exception {
        tabModel.addRow(new Object[]{rowList[0], rowList[1]});
    }



}
