package de.exxcellent.challenge.DataAnalysisTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FetchStrategySmallestSpreadTests {


    @Test
    void shouldFindDayWithSmallestTemperatureSpread() {

        List<Map<String, String>> testData = List.of(
                Map.of("Day", "1", "MxT", "88", "MnT", "50"), // Spread: 38
                Map.of("Day", "2", "MxT", "75", "MnT", "60"), // Spread: 15
                Map.of("Day", "3", "MxT", "80", "MnT", "70")  // Spread: 10 (Der Gewinner!)
        );

        SmallestSpreadStrategy strategy = new SmallestSpreadStrategy();
        Map<String, String> result = strategy.execute(testData);

        //Assertion:
        assertEquals("3", result.get("Day"));
    }

    @Test
    void shouldReturnEmptyMapIfDataIsEmpty() {
        // Arrange
        List<Map<String, String>> testData = Collections.emptyList();

        // Act
        Map<String, String> result = strategy.execute(testData);

        // Assert
        assertEquals(Collections.emptyMap(), result);
    }

    @Test
    @DisplayName("Soll eine Exception werfen, wenn erforderliche Spalten fehlen")
    void shouldThrowExceptionIfRequiredColumnsAreMissing() {
        // Arrange
        List<Map<String, String>> testData = List.of(
                Map.of("Day", "1", "MxT", "88"), // MnT fehlt
                Map.of("Day", "2", "MxT", "75", "MnT", "60")
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> strategy.execute(testData));
    }
}
