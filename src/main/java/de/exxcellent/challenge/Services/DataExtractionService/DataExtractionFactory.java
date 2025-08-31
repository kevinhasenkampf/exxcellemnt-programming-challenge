package de.exxcellent.challenge.Services.DataExtractionService;

public class DataExtractionFactory {

    public DataExtractionFactory(){}

    public DataExtractionInterface getDataExtractionInterface(String filePath){
        // Prüfen auf leeren oder ungültigen Pfad
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path must not be null or empty.");
        }

        // Dateiendung ermitteln
        String fileExtension = getFileExtension(filePath);

        // Logik, um den richtigen Extractor zurückzugeben
        switch (fileExtension.toLowerCase()) {
            case "csv":
                return new DataExtractionInterfaceCSVImplementation();
            // ... weitere Dateitypen ...
            default:
                throw new UnsupportedOperationException("Unsupported file type: " + fileExtension);
        }
    }

    // Hilfsmethode zur Extraktion der Dateiendung
    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1);
        }
        return ""; // Keine Endung gefunden
    }

    }
