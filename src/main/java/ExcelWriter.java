import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelWriter {


    public static void writeExcel(String outFile, List<String> outCol, List<Map<String,String>> data) throws IOException {

        Workbook workbook = new XSSFWorkbook(); //HSSFWorkbook for xls file


        Sheet sheet = workbook.createSheet("Sheet1");

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < outCol.size(); i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(outCol.get(i));

        }

        int rowNo = 1;
        for (int i = 0; i < data.size(); i++){

            Row row = sheet.createRow(rowNo++);

            int cellNo = 0;
            for (Object key : data.get(i).keySet()) {
                row.createCell(cellNo++).setCellValue(data.get(i).get(key));
            }

        }

        //Resize columns to fit content size
        for(int i = 0; i < outCol.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        //Write the output to file
        FileOutputStream fileOut = new FileOutputStream(outFile);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();




    }

}
