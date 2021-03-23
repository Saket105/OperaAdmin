package com.example.operaadmin.Model;

import com.google.firebase.database.Exclude;

public class SongModels {
    String SongTitle, SongArtist, SongAlbum, SongDuration, SongLink, mKey, SongPoster, SongCategory;

    public SongModels(String songTitle, String songArtist, String songAlbum, String songDuration, String songLink, String songCategory) {

        if (songTitle.trim().equals("")){
            songTitle = "No Title";
        }

        SongTitle = songTitle;
        SongArtist = songArtist;
        SongAlbum = songAlbum;
        SongDuration = songDuration;
        SongLink = songLink;
        SongCategory = songCategory;
    }

    public SongModels() {
    }

    public String getSongTitle() {
        return SongTitle;
    }

    public void setSongTitle(String songTitle) {
        SongTitle = songTitle;
    }

    public String getSongArtist() {
        return SongArtist;
    }

    public void setSongArtist(String songArtist) {
        SongArtist = songArtist;
    }

    public String getSongAlbum() {
        return SongAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        SongAlbum = songAlbum;
    }

    public String getSongDuration() {
        return SongDuration;
    }

    public void setSongDuration(String songDuration) {
        SongDuration = songDuration;
    }

    public String getSongLink() {
        return SongLink;
    }

    public void setSongLink(String songLink) {
        SongLink = songLink;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    @Exclude
    public String getSongPoster() {
        return SongPoster;
    }

    @Exclude
    public void setSongPoster(String songPoster) {
        SongPoster = songPoster;
    }

    public String getSongCategory() {
        return SongCategory;
    }

    public void setSongCategory(String songCategory) {
        SongCategory = songCategory;
    }
}
