package org.example.main.model;

public class ReportResponse {

    private int id;
    private String name;
    private String response;

    public ReportResponse() {
    }

    public ReportResponse(int id, String name, String response) {
        this.id = id;
        this.name = name;
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getResponse() {
        return response;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
