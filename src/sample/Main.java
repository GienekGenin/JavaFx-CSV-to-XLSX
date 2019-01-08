package sample;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Scanner;

import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        try {
            Scanner scanner = new Scanner(new File(selectedFile.getAbsolutePath()));
            while (scanner.hasNext()) {
                List<String> line = CSVParser.parseLine(scanner.nextLine());
                // TODO: get info from the line
                System.out.println("Country [id= " + line.get(0) + ", code= " + line.get(1) + " , name=" + line.get(2) + "]");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String filename = "D:\\Bitstream\\test.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("No.");
            rowhead.createCell(1).setCellValue("Name");
            rowhead.createCell(2).setCellValue("Address");
            rowhead.createCell(3).setCellValue("Email");

            HSSFRow row = sheet.createRow((short)1);
            row.createCell(0).setCellValue("1");
            row.createCell(1).setCellValue("Sankumarsingh");
            row.createCell(2).setCellValue("India");
            row.createCell(3).setCellValue("sankumarsingh@gmail.com");

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Your excel file has been generated!");

        } catch ( Exception ex ) {
            System.out.println(ex);
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
