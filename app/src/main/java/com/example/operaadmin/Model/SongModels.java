package com.example.operaadmin.Model;

public class SongModels {
    String id, song_title, song_artist, album_art, song_duration, song_category, mKey;

    public SongModels(String id, String song_title, String song_artist, String album_art, String song_duration, String song_category) {

        if (song_title.trim().equals("")){
            song_title = "No Title";
        }
        this.id = id;
        this.song_title = song_title;
        this.song_artist = song_artist;
        this.album_art = album_art;
        this.song_duration = song_duration;
        this.song_category = song_category;
//        this.mKey = mKey;
    }

    public SongModels() {
    }

    public SongModels(String category_sel, String toString, String toString1, String toString2, String image_url, String toString3, String toString4) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getSong_artist() {
        return song_artist;
    }

    public void setSong_artist(String song_artist) {
        this.song_artist = song_artist;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getSong_duration() {
        return song_duration;
    }

    public void setSong_duration(String song_duration) {
        this.song_duration = song_duration;
    }

    public String getSong_category() {
        return song_category;
    }

    public void setSong_category(String song_category) {
        this.song_category = song_category;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
