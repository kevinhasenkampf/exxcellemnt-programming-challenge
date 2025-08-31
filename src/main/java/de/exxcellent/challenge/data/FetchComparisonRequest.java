package de.exxcellent.challenge.data;

public class FetchComparisonRequest {

    private String key;



    private String firstCompareValue;

    private String secondCompareValue;

    public FetchComparisonRequest(String key, String firstCompareValue, String secondCompareValue){
        this.firstCompareValue = firstCompareValue;
        this.key = key;
        this.secondCompareValue = secondCompareValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFirstCompareValue() {
        return firstCompareValue;
    }

    public void setFirstCompareValue(String firstCompareValue) {
        this.firstCompareValue = firstCompareValue;
    }

    public String getSecondCompareValue() {
        return secondCompareValue;
    }

    public void setSecondCompareValue(String secondCompareValue) {
        this.secondCompareValue = secondCompareValue;
    }
}
