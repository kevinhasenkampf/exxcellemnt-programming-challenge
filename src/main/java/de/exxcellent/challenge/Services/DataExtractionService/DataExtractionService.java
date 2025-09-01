package de.exxcellent.challenge.Services.DataExtractionService;

import de.exxcellent.challenge.data.DataExtractionException;
import de.exxcellent.challenge.data.DataExtractionResult;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DataExtractionService {

    /**
     * A factory used to determine and provide the appropriate implementation of
     * {@link DataExtractionInterface} based on the file type.
     * This factory enables the {@link DataExtractionService} to handle various input data formats
     * seamlessly by encapsulating the logic of extractor selection.
     * It ensures a flexible and scalable architecture for data extraction tasks.
     */
    private final DataExtractionFactory dataExtractionFactory;

    /**
     * This constructor initializes the {@code DataExtractionFactory} that facilitates the retrieval
     * of file-specific data extraction implementations.
     */
    public DataExtractionService() {
        this.dataExtractionFactory = new DataExtractionFactory();
    }

    /**
     * Reads data from a specified file path and extracts it into a {@link DataExtractionResult}.
     * The method identifies the appropriate data extraction strategy based on the file type
     * and processes the file content accordingly.
     *
     * @param filePath the path to the file from which data will be read and extracted
     * @return the extracted data encapsulated within a {@link DataExtractionResult} object
     * @throws DataExtractionException if there is an error accessing or extracting data from the specified file
     */
    public DataExtractionResult readInDataFromFile(String filePath) throws DataExtractionException {
        try (Reader reader = new FileReader(filePath)) {
            // Open a fileReader and try to extract the Data
            return readData(reader, filePath);
        } catch (IOException e) {
            throw new DataExtractionException("Failed to extract data from: " + filePath);
        }
    }

    /**
     * Reads and extracts data from the provided {@link Reader} based on the file type derived from the file path.
     * This method uses a factory to determine the appropriate implementation for data extraction
     * and delegates the extraction process to the corresponding implementation.
     *
     * @param reader   the {@link Reader} instance to read data from
     * @param filePath the file path of the data file being processed, used to identify the file type
     * @return a {@link DataExtractionResult} containing the extracted data
     * @throws IOException if an I/O error occurs while reading data
     */
    // Extracted this method to isolate the readDate Part and have better testability
    public DataExtractionResult readData(Reader reader, String filePath) throws IOException {
        // Get the Correct File Extractor with the Data Extractor Factory
        DataExtractionInterface dataExtractionInterfaceImplementation = this.dataExtractionFactory.getDataExtractionInterface(filePath);

        // Try to read the data
        return dataExtractionInterfaceImplementation.extractData(reader);
    }
}
