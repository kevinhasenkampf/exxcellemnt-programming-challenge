package de.exxcellent.challenge.DataExtractionTests;

import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionFactory;
import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionInterface;
import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionService;
import de.exxcellent.challenge.data.DataExtractionResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataExtractionServiceTests {

    @Mock
    private DataExtractionFactory dataExtractionFactory;

    @Mock
    private DataExtractionInterface dataExtractionInterface;

    @InjectMocks
    private DataExtractionService dataExtractionService;

    @Test
    void readData_ValidReaderAndFilePath_ReturnsDataExtractionResult() throws IOException {
        // Given
        String filePath = "test-weather.csv";
        String dummyCsvData = "Day,MxT,MnT\n1,88,59\n2,79,63";
        Reader stringReader = new StringReader(dummyCsvData);

        List<Map<String, Object>> mockData = Arrays.asList(
                Map.of("Day", "1", "MxT", "88", "MnT", "59"),
                Map.of("Day", "2", "MxT", "79", "MnT", "63")
        );
        DataExtractionResult mockDataExtractionResult = new DataExtractionResult(mockData, filePath, "WEATHER");

        DataExtractionInterface mockExtractor = mock(DataExtractionInterface.class);

        when(mockExtractor.extractData(any(Reader.class))).thenReturn(mockDataExtractionResult);

        when(dataExtractionFactory.getDataExtractionInterface(filePath)).thenReturn(mockExtractor);

        // When
        DataExtractionResult result = dataExtractionService.readData(stringReader, filePath);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getData()).hasSize(2);
        assertThat(result.getRecordCount()).isEqualTo(2);

        assertThat(result.getData().get(0)).containsEntry("Day", "1");
    }

    @Test
    void readData_UnsupportedFileType_ThrowsUnsupportedOperationException() throws IOException {
        // Given
        String filePath = "unsupported.xyz"; // Eine nicht unterstützte Dateiendung
        Reader dummyReader = new StringReader("irgendwelcher Inhalt");

        when(dataExtractionFactory.getDataExtractionInterface(filePath))
                .thenThrow(new UnsupportedOperationException("Unsupported file type: xyz"));

        // When & Then
        assertThatThrownBy(() -> dataExtractionService.readData(dummyReader, filePath))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Unsupported file type: xyz");

        // Extractor nicht aufgerufen
        verifyNoInteractions(mock(DataExtractionInterface.class));
    }
    @Test
    void readData_DataExtractionFails_ThrowsIOException() throws IOException {
        // Given
        String filePath = "corrupted.csv";
        Reader dummyReader = new StringReader("ungültiger Dateninhalt");

        DataExtractionInterface mockExtractor = mock(DataExtractionInterface.class);
        when(dataExtractionFactory.getDataExtractionInterface(filePath)).thenReturn(mockExtractor);
        when(mockExtractor.extractData(any(Reader.class)))
                .thenThrow(new IOException("Simulated parsing error"));

        // When & Then
        assertThatThrownBy(() -> dataExtractionService.readData(dummyReader, filePath))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Simulated parsing error");
    }

    @Test
    void readData_EmptyFile_ReturnsEmptyResult() throws IOException {
        // Given
        String filePath = "empty.csv";
        Reader emptyReader = new StringReader(""); // Ein leerer Reader

        DataExtractionResult emptyResult = new DataExtractionResult(Collections.emptyList(), filePath, "UNKNOWN");

        DataExtractionInterface mockExtractor = mock(DataExtractionInterface.class);
        when(dataExtractionFactory.getDataExtractionInterface(filePath)).thenReturn(mockExtractor);
        when(mockExtractor.extractData(any(Reader.class))).thenReturn(emptyResult);

        // When
        DataExtractionResult result = dataExtractionService.readData(emptyReader, filePath);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getData()).isEmpty();
        assertThat(result.getRecordCount()).isEqualTo(0);
    }
}
