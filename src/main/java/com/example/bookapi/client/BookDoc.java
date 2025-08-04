package com.example.bookapi.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookDoc {

    private String title;

    @JsonProperty("author_name")
    private List<String> authorName;

    private List<String> subject;

    private String key;

    public String getTitle() {
        return title;
    }

    public List<String> getAuthorName() {
        return authorName;
    }

    public List<String> getSubject() {
        return subject;
    }

    public String getKey() {
        return key;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(List<String> authorName) {
        this.authorName = authorName;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public void setKey(String key) {
        this.key = key;
    }
}