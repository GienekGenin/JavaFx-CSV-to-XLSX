package sample;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV file");
        fileChooser.setInitialDirectory(new File("U:\\"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.bom"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        CSVParser csvParser = new CSVParser();
        csvParser.parseCSV(selectedFile);

        XLSXCreator.newWorkSheet(csvParser.vendorKeys, csvParser.hmap);

        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
