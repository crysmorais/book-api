package com.bookapi.service.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryResponse {

    private List<OpenLibraryDoc> docs;

    public List<OpenLibraryDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<OpenLibraryDoc> docs) {
        this.docs = docs;
    }
}