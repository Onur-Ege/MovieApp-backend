package com.onurege.demo.data;

public class FavoriteResponse {
    private String status_message;
    private boolean success;

    public FavoriteResponse(String status_message, boolean success) {
        this.status_message = status_message;
        this.success = success;
    }

    public String getMessage() { return status_message; }
    public boolean isSuccess() { return success; }
}
