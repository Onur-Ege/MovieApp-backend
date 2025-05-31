package com.onurege.demo.data.detail.dto;

public class Result {
    public String author;
    public AuthorDetails author_details;
    public String content;
    public String created_at;
    public String id;
    public String updated_at;
    public String url;

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AuthorDetails getAuthor_details() {
        return author_details;
    }

    public void setAuthor_details(AuthorDetails author_details) {
        this.author_details = author_details;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
