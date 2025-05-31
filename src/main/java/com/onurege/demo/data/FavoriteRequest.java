package com.onurege.demo.data;

public class FavoriteRequest {
    private String userId;
    private Integer tmdbId;
    private String imdbId;
    private String title;
    private Integer rating;


    public FavoriteRequest() {
    }

    public FavoriteRequest(String userId, String imdbId, Integer tmdbId, String title, Integer rating) {
        this.userId = userId;
        this.imdbId = imdbId;
        this.tmdbId = tmdbId;
        this.title = title;
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() { return userId; }
    public Integer getTmdbId() { return tmdbId; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setTmdbId(Integer tmdbId) { this.tmdbId = tmdbId; }
}