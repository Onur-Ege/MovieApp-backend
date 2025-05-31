package com.onurege.demo.data.detail.model;

public class Review {
    private String author;
    private String content;
    private String id;
    private String createdAt;
    private Double rating;

    public Review() {
    }

    public Review(String author, String content, String id, String createdAt, Double rating) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.createdAt = createdAt;
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
