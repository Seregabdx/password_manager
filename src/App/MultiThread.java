package App;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;


public class MultiThread {



    public DefaultTableModel mTableModel = new DefaultTableModel();

    //Thread 1
    class thread1 extends Thread
    {


        public void run()
        {
            try {
                setPriority(MAX_PRIORITY);
                File openfile = new File("C:/Users/Home/Documents/test.xml");
                DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = f.newDocumentBuilder();
                Document doc = builder.parse(openfile);
                NodeList nlist = doc.getElementsByTagName("mainTable");
                synchronized (mTableModel) {
                    new OpenXML(mTableModel,nlist.item(0),0).initTable();
                }
                System.out.println("XML opened");
            } catch (Exception exc) {
                exc.printStackTrace();
            }

        }
    }

    //Thread 2
    class thread2 extends Thread
    {
        public void run()
        {
            setPriority(NORM_PRIORITY);
            synchronized(mTableModel)
            {
                mTableModel.addRow(new Object[]{"gg","hh"});
                mTableModel.insertRow(0,new Object[]{"gg","hh"});
                mTableModel.removeRow(1);
            }

            System.out.println("Items added");

        }
    }

    //Thread 3
    class thread3 extends Thread
    {
        public void run()
        {
            setPriority(MIN_PRIORITY);
            try {
                com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                document.setPageSize(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Home/Documents/test.pdf"));
                document.open();
                Font fontMain = new Font(BaseFont.createFont("c:/windows/fonts/times.ttf", "cp1251", BaseFont.EMBEDDED));
                Font fontHeader = new Font(BaseFont.createFont("c:/windows/fonts/times.ttf", "cp1251", BaseFont.EMBEDDED));
                fontHeader.setStyle(Font.BOLD);
                fontHeader.setSize(16);
                Paragraph pMerch = new Paragraph("Passwords", fontHeader);
                pMerch.setAlignment(Element.ALIGN_CENTER);
                document.add(pMerch);
                PdfPTable Mtable = new PdfPTable(3);
                Mtable.setSpacingBefore(10);
                Mtable.setSpacingAfter(10);
                Mtable.setHorizontalAlignment(Element.ALIGN_CENTER);
                Mtable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                Mtable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
                Mtable.addCell(new Phrase("UID", fontMain));
                Mtable.addCell(new Phrase("URL", fontMain));
                Mtable.addCell(new Phrase("Password", fontMain));
                Mtable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                Mtable.getDefaultCell().setVerticalAlignment(Element.ALIGN_LEFT);
                synchronized (mTableModel) {
                    for(int i = 0; i < mTableModel.getRowCount(); i++){
                        Mtable.addCell(new Phrase(mTableModel.getValueAt(i,0).toString(), fontMain));
                        Mtable.addCell(new Phrase(mTableModel.getValueAt(i,1).toString(), fontMain));
                    }
                }
                document.add(Mtable);

            } catch (Exception exc) {
                exc.printStackTrace();
            }

            System.out.println("PDF saved");
        }
    }

    public MultiThread()
    {
        mTableModel.addColumn("1");
        mTableModel.addColumn("2");
        mTableModel.addColumn("3");

        thread1 thr1 = new thread1();
        thread2 thr2 = new thread2();
        thread3 thr3 = new thread3();

        thr1.run();
        thr2.run();
        thr3.run();

        try
        {
            thr1.join();
            thr2.join();
            thr3.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

}

