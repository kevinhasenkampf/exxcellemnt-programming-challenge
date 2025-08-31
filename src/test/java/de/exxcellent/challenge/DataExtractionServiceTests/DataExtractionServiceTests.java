package de.exxcellent.challenge.DataExtractionServiceTests;

import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionFactory;
import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionInterface;
import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionService;
import de.exxcellent.challenge.data.DataExtractionException;
import de.exxcellent.challenge.data.DataExtractionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataExtractionServiceTests {

    @Mock
    private DataExtractionFactory factory;

    @Mock
    private DataExtractionInterface extractor;

    private DataExtractionService service;

    @BeforeEach
    void setUp() {
        service = new DataExtractionService();
    }

    @Test
    void readInDataFromFile_ValidCsvFile_ReturnsDataExtractionResult() {
        // Given
        String filePath = "weather.csv";
        String dataType = "WEATHER";
        List<Map<String, Object>> mockData = Arrays.asList(
                Map.of("Day", "1", "MxT", "88", "MnT", "59"),
                Map.of("Day", "2", "MxT", "79", "MnT", "63")
        );

        DataExtractionResult mockDataExtractionResult = new DataExtractionResult(mockData, filePath, "WEATHER");

        when(factory.getDataExtractionInterface(filePath)).thenReturn(extractor);
        when(extractor.extractData(filePath)).thenReturn(mockDataExtractionResult);

        // When
        DataExtractionResult result = service.readInDataFromFile(filePath);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getData()).hasSize(2);
        assertThat(result.getRecordCount()).isEqualTo(2);
    }

    @Test
    void readInDataFromFile_EmptyFile_ReturnsEmptyResult() {
        // Given
        String filePath = "empty.csv";
        String dataType = "WEATHER";
        List<Map<String, Object>> emptyData = Collections.emptyList();

        DataExtractionResult mockDataExtractionResult = new DataExtractionResult(emptyData, filePath, dataType);


        when(factory.getDataExtractionInterface(filePath)).thenReturn(extractor);

        when(extractor.extractData(filePath)).thenReturn(mockDataExtractionResult);
        // When
        DataExtractionResult result = service.readInDataFromFile(filePath);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getData()).isEmpty();
        assertThat(result.getRecordCount()).isEqualTo(0);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void readInDataFromFile_FileNotFound_ThrowsDataExtractionException() {
        // Given
        String filePath = "nonexistent.csv";
        String dataType = "WEATHER";

        when(factory.getDataExtractionInterface(filePath)).thenReturn(extractor);
        when(extractor.extractData(filePath)).thenThrow(new FileNotFoundException("File not found"));

        // When & Then
        assertThatThrownBy(() -> service.readInDataFromFile(filePath))
                .isInstanceOf(DataExtractionException.class)
                .hasMessageContaining("Failed to extract data from: " + filePath)
                .hasCauseInstanceOf(FileNotFoundException.class);
    }

    @Test
    void readInDataFromFile_ValidFile_ExceptionWhileReadingFile_ThrowsDataExtractionException(){
        String filePath = "empty.csv";
        String dataType = "WEATHER";
        List<Map<String, Object>> emptyData = Collections.emptyList();

        DataExtractionResult mockDataExtractionResult = new DataExtractionResult(emptyData, filePath, dataType);

        when(factory.getDataExtractionInterface(filePath)).thenReturn(extractor);
        when(extractor.extractData(filePath)).thenThrow(new RuntimeException("File Deleted"));

        // When & Then
        assertThatThrownBy(() -> service.readInDataFromFile(filePath))
                .isInstanceOf(DataExtractionException.class)
                .hasMessageContaining("Error while Reading from File: " + filePath)
                .hasCauseInstanceOf(RuntimeException.class);

    }

    //TODO: hier müssen noch tests
}
