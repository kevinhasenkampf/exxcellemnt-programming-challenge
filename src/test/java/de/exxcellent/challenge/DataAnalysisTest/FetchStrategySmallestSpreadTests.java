package de.exxcellent.challenge.DataAnalysisTest;

import de.exxcellent.challenge.Services.DataAnalysisService.FetchStrategyInterface;
import de.exxcellent.challenge.Services.DataAnalysisService.FetchStrategyMinSpreadImplementation;
import de.exxcellent.challenge.data.DataExtractionResult;
import de.exxcellent.challenge.data.FetchComparisonRequest;
import de.exxcellent.challenge.data.FetchStrategyResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FetchStrategySmallestSpreadTests {


    @Test
    void shouldFindDayWithSmallestTemperatureSpread() {

        List<Map<String, Object>> testData = List.of(
                Map.of("Day", "1", "MxT", "88", "MnT", "50"), // Spread: 38
                Map.of("Day", "2", "MxT", "75", "MnT", "60"), // Spread: 15
                Map.of("Day", "3", "MxT", "80", "MnT", "70")  // Spread: 10
        );

        DataExtractionResult dataExtractionResult = new DataExtractionResult(testData, "test", "test");

        FetchComparisonRequest fetchComparisonRequest = new FetchComparisonRequest("Day", "MxT", "MnT");

        FetchStrategyMinSpreadImplementation strategy = new FetchStrategyMinSpreadImplementation();
        FetchStrategyResult result = strategy.execute(dataExtractionResult, fetchComparisonRequest);

        //Assertion:
        assertEquals("3", result.getBestKey());
    }

    @Test
    void shouldReturnEmptyMapIfDataIsEmpty() {
        // Arrange
        List<Map<String, Object>> testData = Collections.emptyList();

        DataExtractionResult dataExtractionResult = new DataExtractionResult(testData, "test", "test");

        FetchComparisonRequest fetchComparisonRequest = new FetchComparisonRequest("Day", "MxT", "MnT");
        FetchStrategyMinSpreadImplementation strategy = new FetchStrategyMinSpreadImplementation();

        // Act
        FetchStrategyResult result = strategy.execute(dataExtractionResult, fetchComparisonRequest);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Soll eine Exception werfen, wenn erforderliche Spalten fehlen")
    void shouldThrowExceptionIfRequiredColumnsAreMissing() {
        // Arrange
        List<Map<String, Object>> testData = List.of(
                Map.of("Day", "1", "MxT", "88"), // MnT fehlt
                Map.of("Day", "2", "MxT", "75", "MnT", "60")
        );

        DataExtractionResult dataExtractionResult = new DataExtractionResult(testData, "test", "test");

        FetchComparisonRequest fetchComparisonRequest = new FetchComparisonRequest("Day", "MxT", "MnT");
        FetchStrategyMinSpreadImplementation strategy = new FetchStrategyMinSpreadImplementation();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> strategy.execute(dataExtractionResult, fetchComparisonRequest));
    }
}
