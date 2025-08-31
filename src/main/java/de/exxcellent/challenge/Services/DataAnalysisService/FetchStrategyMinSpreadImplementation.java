package de.exxcellent.challenge.Services.DataAnalysisService;

import de.exxcellent.challenge.data.DataExtractionResult;
import de.exxcellent.challenge.data.FetchComparisonRequest;
import de.exxcellent.challenge.data.FetchStrategyResult;

import java.util.List;
import java.util.Map;

public class FetchStrategyMinSpreadImplementation implements FetchStrategyInterface{
     @Override
    public FetchStrategyResult execute(DataExtractionResult dataExtractionResult, FetchComparisonRequest fetchComparisonRequest) {
         List<Map<String, Object>> data = dataExtractionResult.getData();



         // Handle den Fall, dass die Daten leer sind.
         if (data == null || data.isEmpty()) {
             return null;
         }

         String bestKey = null;
         double minDifference = Double.MAX_VALUE;

         for (Map<String, Object> row : data) {
             // Validierung der Zeile
             if (!row.containsKey(fetchComparisonRequest.getKey()) || !row.containsKey(fetchComparisonRequest.getFirstCompareValue()) || !row.containsKey(fetchComparisonRequest.getSecondCompareValue())) {
                 throw new IllegalArgumentException();
             }

             // Parsen und Berechnung
             try {
                 double value1 = Double.parseDouble((String) row.get(fetchComparisonRequest.getFirstCompareValue()));
                 double value2 = Double.parseDouble((String) row.get(fetchComparisonRequest.getSecondCompareValue()));
                 double currentDifference = Math.abs(value1 - value2);

                 //
                 if (currentDifference < minDifference) {
                     minDifference = currentDifference;
                     bestKey = row.get(fetchComparisonRequest.getKey()).toString();
                 }
             } catch (NumberFormatException e) {
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
