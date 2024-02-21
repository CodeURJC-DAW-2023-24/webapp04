package main.backend;

import org.junit.jupiter.api.Test;
import webapp4.main.csv_editor.CSVReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class CSVReaderTest {

    String clientDataPath = "../backend/src/main/resources/static/data/client_data.csv";

    @Test
    void nonNullListTest(){
        CSVReader csvReader = new CSVReader(clientDataPath);
        List<List<String>> records = csvReader.readLines();
        assertNotNull(records);
    }

    @Test
    void getFirstElementTest(){
        CSVReader csvReader = new CSVReader(clientDataPath);
        List<List<String>> records = csvReader.readLines();
        assertEquals("Borrás Narváez", records.get(2).get(3));
    }
}
