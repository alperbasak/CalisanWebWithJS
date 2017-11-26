package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Calisan;

import javax.servlet.http.Part;
import java.awt.Image;
import java.io.*;
import java.util.List;

public final class Util {

    private static Util INSTANCE;

    private Util(){}

    public static Util getInstance(){
        if (INSTANCE==null){
            return new Util();}
        return INSTANCE;
    }

    public static boolean isValidTckn(String tckn) {
        if (null!=tckn && tckn.matches("^([1-9]{1}[0-9]{10})$")) {
            return isValidTckn(Long.valueOf(tckn));
        }

        return false;
    }

    private static boolean isValidTckn(Long tckn) {
        try {
            String tmp = tckn.toString();

            if (tmp.length() == 11) {
                int totalOdd = 0;

                int totalEven = 0;

                for (int i = 0; i < 9; i++) {
                    int val = Integer.valueOf(tmp.substring(i, i + 1));

                    if (i % 2 == 0) {
                        totalOdd += val;
                    } else {
                        totalEven += val;
                    }
                }

                int total = totalOdd + totalEven + Integer.valueOf(tmp.substring(9, 10));

                int lastDigit = total % 10;

                if (tmp.substring(10).equals(String.valueOf(lastDigit))) {
                    int check = (totalOdd * 7 - totalEven) % 10;

                    if (tmp.substring(9, 10).equals(String.valueOf(check))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        e.printStackTrace();
        }

        return false;
    }

    public void exportList(List<Calisan> calisanList) throws IOException, DocumentException {
        Document document=new Document();
        OutputStream fos=new FileOutputStream(new File("export.pdf"));
        PdfWriter.getInstance(document,fos);

        PdfPTable baslik=new PdfPTable(1);
        PdfPCell pdfPCell=new PdfPCell(Phrase.getInstance("Calisanlar Tablosu"));
        pdfPCell.setBackgroundColor(BaseColor.RED);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        baslik.addCell(pdfPCell);

        PdfPTable calisanTablosu=new PdfPTable(4); //Bunu biliyoruz, normalde dinamik yapılmalı.
        calisanTablosu.addCell("TCID: ");
        calisanTablosu.addCell("AD: ");
        calisanTablosu.addCell("SOYAD: ");
        calisanTablosu.addCell("MAAS: ");

        for (Calisan calisan:calisanList) {
            calisanTablosu.addCell(calisan.getTcID());
            calisanTablosu.addCell(calisan.getName());
            calisanTablosu.addCell(calisan.getLastName());
            calisanTablosu.addCell(String.valueOf(calisan.getSalary()));
        }
        document.open();
        document.add(baslik);
        document.add(calisanTablosu);
        document.close();
        fos.close();
    }

    public byte[] convertImage(Part image) throws IOException {
        byte[] newImage=new byte[(int)image.getSize()];
        image.getInputStream().read(newImage);
        return newImage;
    }

}
