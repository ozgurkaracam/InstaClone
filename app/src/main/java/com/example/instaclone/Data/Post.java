package com.example.instaclone.Data;

import com.google.firebase.database.ServerValue;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {
    private String imageUrl;
    private String email;
    private String comment;

    public Post(String imageUrl, String email, String comment) {
        this.imageUrl = imageUrl;
        this.email = email;
        this.comment = comment;
    }

    public Post(){

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
