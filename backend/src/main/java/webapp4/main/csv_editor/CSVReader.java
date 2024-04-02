package webapp4.main.csv_editor;



import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private final String path;
    public static String DELIMITER = ",";
    public CSVReader(String filepath){
        path = filepath;
    }

    public List<List<String>> readLines(){
        List<List<String>> records = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource(path).getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);
                records.add(List.of(values));
            }
            return records;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath() {
        return path;
    }
}
