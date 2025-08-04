package com.example.bookapi.client;

import java.util.List;

public class OpenLibraryResponse {

    private List<BookDoc> docs;

    public List<BookDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<BookDoc> docs) {
        this.docs = docs;
    }
}