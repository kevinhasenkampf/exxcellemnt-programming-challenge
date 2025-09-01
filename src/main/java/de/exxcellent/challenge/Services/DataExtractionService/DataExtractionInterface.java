package de.exxcellent.challenge.Services.DataExtractionService;

import de.exxcellent.challenge.data.DataExtractionResult;

import java.io.IOException;
import java.io.Reader;

/**
 * Defines a contract for data extraction from an input source.
 * Implementing classes are responsible for extracting data based on specific formats
 * and returning the extracted result encapsulated within a {@link DataExtractionResult}.
 */
public interface DataExtractionInterface {

    public DataExtractionResult extractData(Reader dataReader) throws IOException;

}
