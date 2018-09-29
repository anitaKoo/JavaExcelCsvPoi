import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;





public class ExcelReader {



    protected static List<Map<String, String>> readExcel(String filename, List<String> header) throws IOException, InvalidFormatException{

        List<Map<String, String>> data = new ArrayList<> ();
        FileInputStream  file  = new FileInputStream((filename));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next();

        boolean notNullRow = true;

        while(rowIterator.hasNext() & notNullRow){
            Row row = rowIterator.next();
            List<String> values = getRowValues(row, dataFormatter);
            if(!checkEmptyRow(values)) {
                Map<String, String> rowMap = combineListsToMap(header, values);
                data.add(rowMap);
            }else{
                notNullRow = false;
            }
        }


        file.close();
        workbook.close();


        return data;
    }


    private static List<String> getRowValues (Row row, DataFormatter dataFormatter){
        List<String> values = new ArrayList<>();
        Iterator<Cell> cellIterator = row.cellIterator();
        while(cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);
            values.add(cellValue);

        }
        return values;

    }

    private static Map<String,String> combineListsToMap (List<String> keys, List<String> values) {


        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), values.get(i));
        }
        return result;
    }

    private static boolean checkEmptyRow(List<String> values){
        return(values.stream().allMatch(x -> x.equals("")));

    }

    private static String  readCell(int row, String col, String filename) throws InvalidFormatException, IOException{

        FileInputStream  file  = new FileInputStream((filename));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Cell cell = sheet.getRow(row).getCell(CellReference.convertColStringToIndex(col));
        file.close();
        workbook.close();


        return dataFormatter.formatCellValue(cell);

    }
}
