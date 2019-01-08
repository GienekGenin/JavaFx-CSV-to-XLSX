package sample;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class Main extends Application {

    // TODO: transfer functions to another class
    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV file");
        fileChooser.setInitialDirectory(new File("D:\\Bitstream\\CSV_to_XLSX\\testFiles"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.bom"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        HashMap<String, xlsLineModel> hmap = new HashMap<String, xlsLineModel>();
        Set<String> vendorKeys = new HashSet<>();

        try {
            Scanner scanner = new Scanner(new File(selectedFile.getAbsolutePath()));

            while (scanner.hasNext()) {
                List<String> line = CSVParser.parseLine(scanner.nextLine());
                // TODO: get info from the line
                // Columns: Count RefDes Value PatternName Manufacturer Producent Uwagi VENDO ?THT/SMD?
                // Data sample: "4";"R93";"0R";"R0603";"";"";"";"MPRS-0R-SMD-0603-1%","SMD"
                String vendoID = line.get(7);
//                System.out.println("Count: " + line.get(0) + ", RefDes: " + line.get(1) + " , Value: " + line.get(2) +
//                         " , PatternName: " + line.get(3) + " , Manufacturer: " + line.get(4) + " , Producent: " + line.get(5) +
//                        " , Uwagi: " + line.get(6) + " , VENDO: " + line.get(7)
//                );

                if (!vendoID.equals("") && !vendoID.equals(" ")) {
                    vendorKeys.add(vendoID);
                    int count = 0;
                    if(Main.isInteger(line.get(0))){
                        count = Integer.parseInt(line.get(0));
                    }
                    String kodTow = line.get(1);
                    String nazwa = line.get(3);
                    if(count >= 1){
                        if (hmap.get(vendoID) == null) {
                            xlsLineModel model = new xlsLineModel();
                            model.setILOSC(model.getILOSC() + count);
                            model.KODTOWARU.add(kodTow);
                            model.NAZWATOWARU = nazwa;
                            hmap.put(vendoID, model);
                        } else {
                            xlsLineModel model = hmap.get(vendoID);
                            model.setILOSC(model.getILOSC() + count);
                            model.KODTOWARU.add(kodTow);
                            hmap.replace(vendoID, model);
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        for (String key : vendorKeys) {
            xlsLineModel towarInfo = hmap.get(key);
            String kody = "";
            for (String KOD : towarInfo.KODTOWARU) {
                kody += KOD + ",";
            }
            System.out.println("Vendor key: " + key + ", nazwa: " + towarInfo.NAZWATOWARU + ", Ilosc: " + towarInfo.ILOSC +
                    ", KODTOWARU: " + kody);
        }


        try {
            String filename = "D:\\Bitstream\\test.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("Name");
            rowhead.createCell(2).setCellValue("Address");
            rowhead.createCell(3).setCellValue("Email");

            HSSFRow row = sheet.createRow((short) 1);
            row.createCell(0).setCellValue("1");
            row.createCell(1).setCellValue("Sankumarsingh");
            row.createCell(2).setCellValue("India");
            row.createCell(3).setCellValue("sankumarsingh@gmail.com");

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Your excel file has been generated!");

        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
