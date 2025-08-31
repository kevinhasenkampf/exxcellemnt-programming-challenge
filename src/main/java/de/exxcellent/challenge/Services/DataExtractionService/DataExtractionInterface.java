package de.exxcellent.challenge.Services.DataExtractionService;

import de.exxcellent.challenge.data.DataExtractionResult;

import java.io.IOException;
import java.io.Reader;

public interface DataExtractionInterface {

    public DataExtractionResult extractData(Reader dataReader) throws IOException;

}
