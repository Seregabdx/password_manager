package App;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.table.DefaultTableModel;

/**
 * export db to xml file
 * Created by HOME on 04.12.2020.
 */
public class SaveXML {
    DefaultTableModel tableToSave;
    Element tableEl;
    Document doc;

    public SaveXML(DefaultTableModel tS, Element tE, Document d) {
        tableToSave = tS;
        tableEl = tE;
        doc = d;
    }
    /**
     * @return doc with paeswd table
     * @throws Exception parser error
     * compile xml file using one model
     */
    public Document saveTableToFXML() throws Exception {
        for (int i = 0; i < tableToSave.getRowCount(); i++) {
            Element rowEl = doc.createElement("row");
            tableEl.appendChild(rowEl);

            for (int j = 0; j < tableToSave.getColumnCount(); j++) {
                String header = tableToSave.getColumnName(j);
                String value = tableToSave.getValueAt(i, j).toString();
                Element cellEl = doc.createElement("cell");
                Attr colAttr = doc.createAttribute("colName");
                cellEl.setAttributeNode(colAttr);
                rowEl.appendChild(cellEl);
                colAttr.appendChild(doc.createTextNode(header));
                cellEl.appendChild(doc.createTextNode(value));
            }
        }
        return doc;
    }
}
