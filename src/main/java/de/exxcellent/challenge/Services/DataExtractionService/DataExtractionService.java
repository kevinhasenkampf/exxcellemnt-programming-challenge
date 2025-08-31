package de.exxcellent.challenge.Services.DataExtractionService;

import de.exxcellent.challenge.data.DataExtractionException;
import de.exxcellent.challenge.data.DataExtractionResult;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DataExtractionService {

    private final DataExtractionFactory dataExtractionFactory;

    public DataExtractionService() {
        this.dataExtractionFactory = new DataExtractionFactory();
    }

    public DataExtractionResult readInDataFromFile(String filePath) throws DataExtractionException {
        try (Reader reader = new FileReader(filePath)) {
            // Open a fileReader and try to extract the Data
            return readData(reader, filePath);
        } catch (IOException e) {
            throw new DataExtractionException("Failed to extract data from: " + filePath);
        }
    }


    public DataExtractionResult readData(Reader reader, String filePath) throws IOException {
        // Get the Correct File Extractor with the Data Extractor Factory
        DataExtractionInterface dataExtractionInterfaceImplementation = this.dataExtractionFactory.getDataExtractionInterface(filePath);

        // Try to read the data
        return dataExtractionInterfaceImplementation.extractData(reader);
    }
}
