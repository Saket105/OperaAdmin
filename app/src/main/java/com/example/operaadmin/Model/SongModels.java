package com.example.operaadmin.Model;

public class SongModels {
    String id, title, artist, album_art, duration, category_name;
    String image;

    public SongModels(String id, String title, String artist, String album_art, String duration, String category_name, String image) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album_art = album_art;
        this.duration = duration;
        this.category_name = category_name;
        this.image = image;
    }

    public SongModels() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
