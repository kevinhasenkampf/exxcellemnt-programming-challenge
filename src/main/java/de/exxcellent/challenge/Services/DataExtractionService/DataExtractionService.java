package de.exxcellent.challenge.Services.DataExtractionService;

import de.exxcellent.challenge.data.DataExtractionResult;

import java.util.Date;

public class DataExtractionService {

    private DataExtractionFactory dataExtractionFactory;

    public DataExtractionService() {
        this.dataExtractionFactory = new DataExtractionFactory();
    }

    public DataExtractionResult readInDataFromFile(String filePath){

        DataExtractionInterface dataExtractionInterfaceImplementation =  this.dataExtractionFactory.getDataExtractionInterface(filePath);

        DataExtractionResult dataExtractionResult = dataExtractionInterfaceImplementation.extractData(filePath);

        return dataExtractionResult;
    }
}
