package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

class CSVParser {

    HashMap<String, ProductModel> hmap = new HashMap<>();
    HashSet<String> vendorKeys = new HashSet<>();

    void parseCSV(File selectedFile) {

        try {
            Scanner scanner = new Scanner(new File(selectedFile.getAbsolutePath()));

            while (scanner.hasNext()) {
                List<String> line = CSVLineParser.parseLine(scanner.nextLine());

                String vendorID = line.get(7);
                if (!vendorID.equals("") && !vendorID.equals(" ")) {
                    vendorKeys.add(vendorID);
                    int itemsCount = 0;
                    if (Functional.isInteger(line.get(0))) {
                        itemsCount = Integer.parseInt(line.get(0));
                    }
                    String prodCode = line.get(1);
                    String name = line.get(3);
                    String SMD_THT = line.get(8);
                    if (itemsCount >= 1) {
                        if (hmap.get(vendorID) == null) {
                            ProductModel products = new ProductModel();
                            products.setILOSC(products.getILOSC() + itemsCount);
                            products.KODTOWARU.add(prodCode);
                            products.NAZWATOWARU = name;
                            products.OBUDOWA = SMD_THT;
                            hmap.put(vendorID, products);
                        } else {
                            ProductModel products = hmap.get(vendorID);
                            products.setILOSC(products.getILOSC() + itemsCount);
                            products.KODTOWARU.add(prodCode);
                            hmap.replace(vendorID, products);
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
