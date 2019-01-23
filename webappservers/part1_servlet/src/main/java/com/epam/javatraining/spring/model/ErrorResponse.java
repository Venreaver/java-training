package com.epam.javatraining.spring.model;

public class ErrorResponse {
    private String info;

    public ErrorResponse(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
