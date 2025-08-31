package de.exxcellent.challenge.Services.DataAnalysisService;

import de.exxcellent.challenge.data.DataExtractionResult;
import de.exxcellent.challenge.data.FetchComparisonRequest;
import de.exxcellent.challenge.data.FetchStrategyResult;

import java.util.Map;

public interface FetchStrategyInterface {

    public FetchStrategyResult execute(DataExtractionResult dataExtractionResult, FetchComparisonRequest fetchComparisonRequest);
}
