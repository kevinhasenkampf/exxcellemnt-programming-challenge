package de.exxcellent.challenge.Services.DataExtractionService;

/**
 * Factory class responsible for creating instances of {@link DataExtractionInterface}
 * specific to different file types. This class encapsulates the logic for determining
 * the appropriate implementation of the interface based on file extension.
 * It ensures flexibility and scalability by abstracting the creation of file-specific
 * data extraction strategies.
 */
public class DataExtractionFactory {

    public DataExtractionFactory(){}

    /**
     * Returns the appropriate implementation of {@link DataExtractionInterface} based on the file type
     * derived from the provided file path. The method determines the file extension and provides
     * a specific implementation to handle data extraction from the file.
     *
     * @param filePath the path to the file for which a data extraction interface implementation is required;
     *                 must not be null or empty
     * @return an implementation of {@link DataExtractionInterface} suitable for the specified file type
     * @throws IllegalArgumentException if the provided file path is null or empty
     * @throws UnsupportedOperationException if the file type is unsupported
     */
    public DataExtractionInterface getDataExtractionInterface(String filePath){
        // Prüfen auf leeren oder ungültigen Pfad
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path must not be null or empty.");
        }

        // Dateiendung ermitteln
        String fileExtension = getFileExtension(filePath);

        // Logik, um den richtigen Extractor zurückzugeben
        // TODO: Das herausfinden der fileExtension solider machen -> keine "magicStrings"
        switch (fileExtension.toLowerCase()) {
            case "csv":
                return new DataExtractionInterfaceCSVImplementation();
            // ... weitere Dateitypen ...
            default:
                throw new UnsupportedOperationException("Unsupported file type: " + fileExtension);
        }
    }

    /**
     * Extracts the file extension from the given file path.
     * The method identifies the position of the last '.' character in the file path
     * and extracts the substring after it. If no valid file extension is found,
     * an empty string is returned.
     *
     * @param filePath the full path or name of the file for which the extension is to be extracted;
     *                 must not be null or empty
     * @return the file extension as a string (e.g., "txt" or "csv"), or an empty string if no extension is found
     */
    // Hilfsmethode zur Extraktion der Dateiendung
    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1);
        }
        return ""; // Keine Endung gefunden
    }

    }
