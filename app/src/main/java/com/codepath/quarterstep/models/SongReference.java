package com.codepath.quarterstep.models;

public class SongReference {
    private User user;
    private String name;
    private String songString;
    private String createdAt;
    private boolean favorite;

    public SongReference() {
        // must have a public no-argument constructor for firebase
    }

    public SongReference(User user, String name, String songString, String createdAt, boolean favorite) {
        this.user = user;
        this.name = name;
        this.songString = songString;
        this.createdAt = createdAt;
        this.favorite = favorite;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSongString() {
        return this.songString;
    }

    public void setSongString(String songString) {
        this.songString = songString;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isFavorite() {
        return this.favorite;
    }

    public void actionFavorite() {
        this.favorite = !this.favorite;
    }
}
