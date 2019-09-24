package com.cyberwith.tasknote;
import com.google.firebase.database.Exclude;

public class DataUpload {
    private String title, details;
    private String key;

    DataUpload(){

    }

    public DataUpload(String title, String details) {
        this.title = title;
        this.details = details;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
