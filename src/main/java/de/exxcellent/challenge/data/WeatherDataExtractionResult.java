package de.exxcellent.challenge.data;

import de.exxcellent.challenge.Services.DataExtractionService.DataExtractionService;

import java.util.List;
import java.util.Map;

public class WeatherDataExtractionResult extends DataExtractionResult{


    protected WeatherDataExtractionResult(List<Map<String, Object>> data, String source) {
        super(data, source, "WEATHER");
    }
}
