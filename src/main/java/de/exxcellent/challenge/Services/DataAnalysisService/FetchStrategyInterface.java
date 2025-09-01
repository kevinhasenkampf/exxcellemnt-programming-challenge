package de.exxcellent.challenge.Services.DataAnalysisService;

import de.exxcellent.challenge.data.DataExtractionResult;
import de.exxcellent.challenge.data.FetchComparisonRequest;
import de.exxcellent.challenge.data.FetchStrategyResult;

import java.util.Map;

/**
 * FetchStrategyInterface defines a contract for implementing various data fetching strategies
 * used in data analysis. These strategies process extracted data and generate results based
 * on a comparison request. The interface facilitates flexibility and allows for different
 * implementations depending on specific analysis requirements.
 */
public interface FetchStrategyInterface {

    public FetchStrategyResult execute(DataExtractionResult dataExtractionResult, FetchComparisonRequest fetchComparisonRequest);
}
