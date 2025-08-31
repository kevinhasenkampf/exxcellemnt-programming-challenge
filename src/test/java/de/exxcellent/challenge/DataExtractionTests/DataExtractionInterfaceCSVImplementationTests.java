package de.exxcellent.challenge.DataExtractionTests;

import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionInterfaceCSVImplementation;
import de.exxcellent.challenge.data.DataExtractionResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DataExtractionInterfaceCSVImplementationTests {

    @Test
    void extractData_ValidCsvData_ReturnsCorrectData() throws IOException {
        // Given
        // Eine Eingabe-CSV-Datei als String, die einen Header und Daten enthält
        String csvInput = "Day,MxT,MnT\n1,88,59\n2,79,63";
        Reader reader = new StringReader(csvInput);

        // Die erwarteten Daten als List<Map<String, Object>>
        List<Map<String, Object>> expectedData = List.of(
                Map.of("Day", "1", "MxT", "88", "MnT", "59"),
                Map.of("Day", "2", "MxT", "79", "MnT", "63")
        );

        DataExtractionInterfaceCSVImplementation dataExtractor = new DataExtractionInterfaceCSVImplementation();

        // When
        DataExtractionResult result = dataExtractor.extractData(reader);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getData()).hasSize(2);
        assertThat(result.getData()).isEqualTo(expectedData);

        assertThat(result.getData().get(0)).containsEntry("Day", "1");
        assertThat(result.getData().get(1)).containsEntry("MnT", "63");
    }

    @Test
    void extractData_EmptyCsvFile_ReturnsEmptyList() throws IOException {
        // Given
        String csvInput = ""; // Leere Eingabe
        Reader reader = new StringReader(csvInput);

        DataExtractionInterfaceCSVImplementation extractor = new DataExtractionInterfaceCSVImplementation();

        // When
        DataExtractionResult result = extractor.extractData(reader);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getData()).isEmpty();
    }

    @Test
    void extractData_HeaderOnlyCsvFile_ReturnsEmptyList() throws IOException {
        // Given
        String csvInput = "Day,MxT,MnT"; // Nur der Header
        Reader reader = new StringReader(csvInput);

        DataExtractionInterfaceCSVImplementation extractor = new DataExtractionInterfaceCSVImplementation();

        // When
        DataExtractionResult result = extractor.extractData(reader);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getData()).isEmpty();
    }

    @Test
    void extractData_MalformedCsvData_ThrowsIOException() {
        // Given
        // Eine Zeile mit zu wenigen Spalten
        String csvInput = "Day,MxT,MnT\n1,88";
        Reader reader = new StringReader(csvInput);

        DataExtractionInterfaceCSVImplementation extractor = new DataExtractionInterfaceCSVImplementation();

        // When & Then
        assertThatThrownBy(() -> extractor.extractData(reader))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
