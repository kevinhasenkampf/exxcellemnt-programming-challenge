package de.exxcellent.challenge.data;

import de.exxcellent.challenge.Services.DataAnalysisService.FetchStrategyInterface;

import java.util.List;
import java.util.Map;

public class FetchStrategyResult {

    public FetchStrategyResult() {

    }

    public FetchStrategyResult(String data, String message){
        this.bestKey = data;
        this.message = message;
    }

    public String getBestKey() {
        return bestKey;
    }

    public void setBestKey(String bestKey) {
        this.bestKey = bestKey;
    }

    private String bestKey;

    private String message;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
