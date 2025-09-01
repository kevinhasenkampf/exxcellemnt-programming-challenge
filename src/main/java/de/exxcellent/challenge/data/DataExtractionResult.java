package de.exxcellent.challenge.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// Class that defines how Extracted Data should Look like
public class DataExtractionResult {

    private final List<Map<String, Object>> data;
    private String source;
    private final LocalDateTime extractedAt;
    private String dataType;


    public DataExtractionResult(List<Map<String, Object>> data, String source, String dataType) {
        this.data = data;
        this.source = source;
        this.extractedAt = LocalDateTime.now();
        this.dataType = dataType;
    }
    public DataExtractionResult(List<Map<String, Object>> data) {
        this.data = data;
        this.extractedAt = LocalDateTime.now();
    }



    public List<Map<String, Object>> getData() { return data; }
    public int getRecordCount() { return data.size(); }
    public boolean isEmpty() { return data.isEmpty(); }

}
