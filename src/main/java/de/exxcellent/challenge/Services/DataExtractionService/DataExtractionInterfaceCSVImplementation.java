package de.exxcellent.challenge.Services.DataExtractionService;

import de.exxcellent.challenge.data.DataExtractionResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataExtractionInterfaceCSVImplementation implements DataExtractionInterface{

    @Override
    public DataExtractionResult extractData(Reader dataReader) throws IOException {

        List<Map<String, Object>> data = new ArrayList<>();

        csvReader(dataReader, data);

        DataExtractionResult  dataExtractionResult = new DataExtractionResult(data);
        return dataExtractionResult;
    }

    private static void csvReader(Reader dataReader, List<Map<String, Object>> data) throws IOException {
        CSVParser csvParser = new CSVParser(dataReader, CSVFormat.DEFAULT
                .builder()
                .setHeader() // Wichtig: Behandelt die erste Zeile als Header
                .setSkipHeaderRecord(true) // Überspringt die Header-Zeile bei der Datenverarbeitung
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build());

        // Header-Spaltennamen abrufen
        Map<String, Integer> headerMap = csvParser.getHeaderMap();

        // Datensätze durchlaufen und in eine Liste von Maps umwandeln
        for (CSVRecord csvRecord : csvParser) {
            // Eine neue Map für jeden Datensatz erstellen
            Map<String, Object> recordMap = new java.util.HashMap<>();

            // Jede Spalte des aktuellen Datensatzes durchlaufen
            for (Map.Entry<String, Integer> entry : headerMap.entrySet()) {
                String headerName = entry.getKey();
                // Wert aus dem CSVRecord mit dem Header-Namen als Schlüssel abrufen
                String value = csvRecord.get(headerName);

                recordMap.put(headerName, value);
            }

            data.add(recordMap);
        }
    }


}
