package de.exxcellent.challenge.Services.DataAnalysisService;

import de.exxcellent.challenge.data.DataExtractionResult;
import de.exxcellent.challenge.data.FetchComparisonRequest;
import de.exxcellent.challenge.data.FetchStrategyResult;

import java.util.List;
import java.util.Map;

/**
 * The FetchStrategyMinSpreadImplementation class implements the FetchStrategyInterface
 * by providing a data comparison strategy that identifies the key associated with the
 * smallest difference (spread) between two specified values in a dataset.
 *
 * This strategy is applied to a data source represented by a DataExtractionResult and
 * operates based on a FetchComparisonRequest, which specifies the key for identification
 * and two comparison fields. The algorithm calculates the absolute difference between
 * the two comparison fields for each data entry, and determines the entry with the minimal
 * spread. The result includes the associated key and a message describing the spread.
 *
 * Key details:
 * - The class ensures that the provided comparison fields and key are valid.
 * - If the dataset is empty or contains parsing errors, appropriate handling is implemented.
 * - Results are returned as an instance of FetchStrategyResult.
 */
public class FetchStrategyMinSpreadImplementation implements FetchStrategyInterface{
     @Override
    public FetchStrategyResult execute(DataExtractionResult dataExtractionResult, FetchComparisonRequest fetchComparisonRequest) {
         List<Map<String, Object>> data = dataExtractionResult.getData();



         // Handle den Fall, dass die Daten leer sind.
         if (data == null || data.isEmpty()) {
             // Hier könnte man auch eine Exception werfen -> je nachdem wie man es handeln will
             return null;
         }

         String bestKey = null;
         double minDifference = Double.MAX_VALUE;

         for (Map<String, Object> row : data) {
             // Validierung der Zeile -> Exit Conditions in the front
             if (!row.containsKey(fetchComparisonRequest.getKey()))
                throw new IllegalArgumentException("Key is missing in row: " + row + "");
             if (!row.containsKey(fetchComparisonRequest.getFirstCompareValue()))
                throw new IllegalArgumentException("First compare value is missing in row: " + row + "");
             if (!row.containsKey(fetchComparisonRequest.getSecondCompareValue()))
                throw new IllegalArgumentException("Second compare value is missing in row: " + row + "");


             // Parsen und Berechnung
             try {
                 // Java has no real way to parse Object to String -> https://stackoverflow.com/questions/7503877/java-correct-way-convert-cast-object-to-double
                 double value1 = Double.parseDouble((String) row.get(fetchComparisonRequest.getFirstCompareValue()));
                 double value2 = Double.parseDouble((String) row.get(fetchComparisonRequest.getSecondCompareValue()));
                 double currentDifference = Math.abs(value1 - value2);

                 //
                 if (currentDifference < minDifference) {
                     minDifference = currentDifference;
                     bestKey = row.get(fetchComparisonRequest.getKey()).toString();
                 }
             } catch (NumberFormatException e) {
                 // Catch the conversion  from String  to  double
                 continue;
             }
         }


         if (bestKey != null) {
             return new FetchStrategyResult(bestKey, "Min Spread = " + minDifference);
         } else {
             return null; // Oder eine passende, leere Instanz
         }
     }
    }
