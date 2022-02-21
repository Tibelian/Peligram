package com.tibelian.peligram.model;

import java.util.UUID;

public class Post {

    private UUID id;
    private String title;
    private String image;
    private int likes;

    public Post(UUID id) {
        this.id = id;
    }
    public Post() {
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
