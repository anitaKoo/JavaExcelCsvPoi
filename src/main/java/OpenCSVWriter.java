import com.opencsv.CSVWriter;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class OpenCSVWriter {

    protected static void writeCSV(String outFile, List<String> outCol, List<Map<String,String>> data ) throws IOException{
        Writer writer = Files.newBufferedWriter(Paths.get(outFile));

        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);


        csvWriter.writeNext(outCol.toArray(new String[0]));

        for (Map<String,String> row : data){
            csvWriter.writeNext(row.values().toArray(new String[0]));
        }


        csvWriter.close();
        writer.close();

    }

}
