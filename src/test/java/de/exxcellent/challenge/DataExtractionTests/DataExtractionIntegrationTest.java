package de.exxcellent.challenge.DataExtractionTests;

import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionFactory;
import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionService;
import de.exxcellent.challenge.data.DataExtractionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class DataExtractionIntegrationTest {

    private DataExtractionService dataExtractionService;

    @BeforeEach
    void setUp() {

        this.dataExtractionService = new DataExtractionService();
    }
    @Test
    void readInDataFromFile_ValidFileFromResources_ReturnsCorrectResult() throws IOException {

        String filePath = getResourcePath("test-weather.csv");

        // When
        DataExtractionResult result = dataExtractionService.readInDataFromFile(filePath);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getData()).hasSize(30);
        assertThat(result.getRecordCount()).isEqualTo(30);

        // Überprüfen Sie den Inhalt der ersten Zeile
        Map<String, Object> firstRecord = result.getData().get(0);
        assertThat(firstRecord).containsEntry("Day", "1");
        assertThat(firstRecord).containsEntry("MxT", "88");
        assertThat(firstRecord).containsEntry("MnT", "59");
    }

    // Hilfsmethode, um den Pfad zu den Test-Ressourcen zu erhalten
    private String getResourcePath(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        return file.getAbsolutePath();
    }

}
