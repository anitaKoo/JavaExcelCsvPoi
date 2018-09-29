import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String args[]) throws IOException, InvalidFormatException {
        String filename = "sample-xls-file.xlsx";
        String outputFile = "sample-output.xlsx";
        String csvFile = "sample-output.csv";
        List<String> header = Arrays.asList("test", "test1");
        List<Map<String,String>> data =ExcelReader.readExcel(filename, header);
        ExcelWriter.writeExcel(outputFile,header,data);
        OpenCSVWriter.writeCSV(csvFile,header,data);
    }

}
