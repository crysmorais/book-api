package com.example.bookapi.service.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryResponse {

    private List<Doc> docs;

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Doc {

        private String title;

        @JsonProperty("author_name")
        private List<String> authorName;

        @JsonProperty("subject")
        private List<String> subjects;

        @JsonProperty("key")
        private String key;

        // Getters e Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getAuthorName() {
            return authorName;
        }

        public void setAuthorName(List<String> authorNames) {
            this.authorName = authorNames;
        }

        public List<String> getSubjects() {
            return subjects;
        }

        public void setSubjects(List<String> subjects) {
            this.subjects = subjects;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}