package de.exxcellent.challenge.Services.DataAnalysisService;

import de.exxcellent.challenge.data.DataExtractionResult;
import de.exxcellent.challenge.data.FetchComparisonRequest;
import de.exxcellent.challenge.data.FetchStrategyResult;

/**
 * The AnalysisService class facilitates data analysis by using a specific strategy
 * to process extracted data and perform a comparison based on a defined request.
 * The strategy is provided through dependency injection, allowing flexibility and adaptability
 * for different analysis requirements.
 */
public class AnalysisService {

    private final FetchStrategyInterface fetchStrategy;

    // (Dependency Injection)
    public AnalysisService(FetchStrategyInterface fetchStrategy) {
        this.fetchStrategy = fetchStrategy;
    }

    public FetchStrategyResult analyze(DataExtractionResult dataExtractionResult, FetchComparisonRequest request) {
        // Die Logik der Strategie wird ausgeführt.
        return fetchStrategy.execute(dataExtractionResult, request);
    }


}
