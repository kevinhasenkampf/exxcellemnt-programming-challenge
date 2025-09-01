package de.exxcellent.challenge;

import de.exxcellent.challenge.Services.DataAnalysisService.AnalysisService;
import de.exxcellent.challenge.Services.DataAnalysisService.FetchStrategyInterface;
import de.exxcellent.challenge.Services.DataAnalysisService.FetchStrategyMinSpreadImplementation;
import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionService;
import de.exxcellent.challenge.data.DataExtractionResult;
import de.exxcellent.challenge.data.FetchComparisonRequest;
import de.exxcellent.challenge.data.FetchStrategyResult;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // Your preparation code …

        // Lese die Daten aus der CSV Datei ein
        DataExtractionService dataExtractionService = new DataExtractionService();
        DataExtractionResult dataExtractionResult =  dataExtractionService.readInDataFromFile("src/main/resources/de/exxcellent/challenge/weather.csv");

        // Erstelle eine FetchStrategie
        FetchStrategyInterface fetchStrategyInterface = new FetchStrategyMinSpreadImplementation();
        AnalysisService analysisService = new AnalysisService(fetchStrategyInterface);
        FetchComparisonRequest fetchComparisonRequest = new FetchComparisonRequest("Day", "MxT", "MnT");

        // Führe den Request auf die Daten aus
        FetchStrategyResult fetchStrategyResult = analysisService.analyze(dataExtractionResult, fetchComparisonRequest);

        String dayWithSmallestTempSpread = "Someday";     // Your day analysis function call …
        System.out.printf("Day with smallest temperature spread : %s%n", fetchStrategyResult.getBestKey());


        // Lese die Daten aus der CSV Datei ein
        DataExtractionService dataExtractionServiceTeam = new DataExtractionService();
        DataExtractionResult dataExtractionResultTeam =  dataExtractionServiceTeam.readInDataFromFile("src/main/resources/de/exxcellent/challenge/football.csv");

        // Erstelle eine FetchStrategie
        FetchStrategyInterface fetchStrategyInterfaceTeam = new FetchStrategyMinSpreadImplementation();
        AnalysisService analysisServiceTeam = new AnalysisService(fetchStrategyInterfaceTeam);
        FetchComparisonRequest fetchComparisonRequestTeam = new FetchComparisonRequest("Team", "Goals", "Goals Allowed");

        // Führe den Request auf die Daten aus
        FetchStrategyResult fetchStrategyResultTeam = analysisServiceTeam.analyze(dataExtractionResultTeam, fetchComparisonRequestTeam);


        String teamWithSmallestGoalSpread = "A good team"; // Your goal analysis function call …
        System.out.printf("Team with smallest goal spread       : %s%n", fetchStrategyResultTeam.getBestKey());
    }
}
