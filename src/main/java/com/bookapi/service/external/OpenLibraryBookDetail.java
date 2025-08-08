package com.bookapi.service.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryBookDetail {
    private String title;
    private String by_statement;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBy_statement() {
        return by_statement;
    }

    public void setBy_statement(String by_statement) {
        this.by_statement = by_statement;
    }
}