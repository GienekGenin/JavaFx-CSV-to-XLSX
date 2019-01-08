package sample;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;

class XLSXCreator {

    static void newWorkSheet(HashSet<String> vendorKeys, HashMap<String, ProductModel> hmap) {
        try {
            String filename = "catalog.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Catalog");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("KOD");
            rowhead.createCell(1).setCellValue("KODTOWARU");
            rowhead.createCell(2).setCellValue("NAZWATOWARU");
            rowhead.createCell(3).setCellValue("MAGAZYN");
            rowhead.createCell(4).setCellValue("ILOSC");
            rowhead.createCell(5).setCellValue("NA_IL_OPERACJI");
            rowhead.createCell(6).setCellValue("BRAKI");
            rowhead.createCell(7).setCellValue("REF");
            rowhead.createCell(7).setCellValue("SMD/THT");

            int cnt = 0;
            for (String key : vendorKeys) {
                cnt += 1;
                ProductModel towarInfo = hmap.get(key);
                String kody = "";
                for (String KOD : towarInfo.KODTOWARU) {
                    kody += KOD + ",";
                }

                HSSFRow row = sheet.createRow((short) cnt);

                // TODO: delete sout after tests
                System.out.println("Vendor key: " + key + ", nazwa: " +
                        towarInfo.NAZWATOWARU + ", Ilosc: " + towarInfo.ILOSC +
                        ", KODTOWARU: " + kody);

                row.createCell(0).setCellValue(key);
                row.createCell(1).setCellValue(kody);
                row.createCell(2).setCellValue(towarInfo.NAZWATOWARU);
                row.createCell(3).setCellValue("");
                row.createCell(4).setCellValue(towarInfo.getILOSC());
                row.createCell(5).setCellValue(1);
                row.createCell(6).setCellValue("Z brakami");
                row.createCell(7).setCellValue(towarInfo.OBUDOWA);
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Your excel file has been generated!");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}

