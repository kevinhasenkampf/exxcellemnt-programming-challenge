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

/**
 * Implementation of {@link DataExtractionInterface} for extracting data from CSV files.
 * This class provides methods to read CSV data using a given {@link Reader} and convert it
 * into a structured {@link DataExtractionResult}.
 * The implementation uses Apache Commons CSV for parsing CSV data.
 */
public class DataExtractionInterfaceCSVImplementation implements DataExtractionInterface{

    /**
     * Extracts data from the given {@link Reader} instance and returns it as a {@link DataExtractionResult}.
     * This method reads CSV data, processes it, and converts it into a structured result object.
     *
     * @param dataReader the {@link Reader} instance to read and process data from
     * @return a {@link DataExtractionResult} containing the extracted data
     * @throws IOException if an I/O error occurs while reading or processing the data
     */
    @Override
    public DataExtractionResult extractData(Reader dataReader) throws IOException {

        List<Map<String, Object>> data = readCSV(dataReader);

        DataExtractionResult  dataExtractionResult = new DataExtractionResult(data);
        return dataExtractionResult;
    }


    private static List<Map<String, Object>> readCSV(Reader dataReader) throws IOException {
        CSVParser csvParser = new CSVParser(dataReader, CSVFormat.DEFAULT
                .builder()
                .setHeader() // Wichtig: Behandelt die erste Zeile als Header
                .setSkipHeaderRecord(true) // Überspringt die Header-Zeile bei der Datenverarbeitung
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build());

        // Header-Spaltennamen abrufen
        Map<String, Integer> headerMap = csvParser.getHeaderMap();

        List<Map<String, Object>> data = new ArrayList<>();

        // Datensätze/Reihen durchlaufen und in eine Liste von Maps umwandeln
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

        return data;
    }


}
