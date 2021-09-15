package App;

import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.table.DefaultTableModel;

/**
 * export db into cute pdf
 * Created by HOME on 04.12.2020.
 */
public class PdfOut {
    public void output(File filename, DefaultTableModel passwords, DefaultTableModel dates, DefaultTableModel hisory)
    {
        Document document = new Document();
        document.setPageSize(PageSize.A4);


        try{
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            //BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251",BaseFont.EMBEDDED);
            Font fontMain = new Font(BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251", BaseFont.EMBEDDED));
            Font fontHeader = new Font(BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251", BaseFont.EMBEDDED));
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
            for(int i = 0; i < passwords.getRowCount(); i++){
                Mtable.addCell(new Phrase(passwords.getValueAt(i,0).toString(), fontMain));
                Mtable.addCell(new Phrase(passwords.getValueAt(i,1).toString(), fontMain));
                Mtable.addCell(new Phrase(passwords.getValueAt(i,2).toString(), fontMain));
            }
            document.add(Mtable);

            Paragraph pStaff = new Paragraph("Passwords lifetimes", fontHeader);
            pStaff.setAlignment(Element.ALIGN_CENTER);
            document.add(pStaff);

            PdfPTable Stable = new PdfPTable(4);
            Stable.setSpacingBefore(10);
            Stable.setSpacingAfter(10);
            Stable.setHorizontalAlignment(Element.ALIGN_CENTER);
            Stable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            Stable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
            Stable.addCell(new Phrase("UID", fontMain));
            Stable.addCell(new Phrase("Create date", fontMain));
            Stable.addCell(new Phrase("Next regeneration date", fontMain));
            Stable.addCell(new Phrase("Regeneration frequency", fontMain));
            Stable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            Stable.getDefaultCell().setVerticalAlignment(Element.ALIGN_LEFT);
            for(int i = 0; i < dates.getRowCount(); i++){
                Stable.addCell(new Phrase(dates.getValueAt(i,0).toString(), fontMain));
                Stable.addCell(new Phrase(dates.getValueAt(i,1).toString(), fontMain));
                Stable.addCell(new Phrase(dates.getValueAt(i,2).toString(), fontMain));
                Stable.addCell(new Phrase(dates.getValueAt(i,3).toString(), fontMain));
            }

            document.add(Stable);

            Paragraph pSales = new Paragraph("Passwords history", fontHeader);
            pSales.setAlignment(Element.ALIGN_CENTER);
            document.add(pSales);

            PdfPTable Saltable = new PdfPTable(2);
            Saltable.setSpacingBefore(10);
            Saltable.setSpacingAfter(10);
            Saltable.setHorizontalAlignment(Element.ALIGN_CENTER);
            Saltable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            Saltable.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
            Saltable.addCell(new Phrase("UID", fontMain));
            Saltable.addCell(new Phrase("Old password", fontMain));
            Saltable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            Saltable.getDefaultCell().setVerticalAlignment(Element.ALIGN_LEFT);
            for(int i = 0; i < hisory.getRowCount(); i++){
                Saltable.addCell(new Phrase(hisory.getValueAt(i,0).toString(), fontMain));
                Saltable.addCell(new Phrase(hisory.getValueAt(i,1).toString(), fontMain));
            }

            document.add(Saltable);

        }
        catch(Exception e){
            System.out.println(e);
        }
        document.close();
    }
}
