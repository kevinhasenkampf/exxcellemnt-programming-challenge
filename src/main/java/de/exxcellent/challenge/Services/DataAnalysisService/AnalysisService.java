package de.exxcellent.challenge.Services.DataAnalysisService;

import de.exxcellent.challenge.data.DataExtractionResult;
import de.exxcellent.challenge.data.FetchComparisonRequest;
import de.exxcellent.challenge.data.FetchStrategyResult;

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
