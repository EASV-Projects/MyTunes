package easvprojects.mytunes.be;

import java.util.List;

public class Song {
    private int id;
    private String name;
    private Artist artist;
    private Album album;
    private List<Genre> genres;

    public Song(String name){
        this.name = name;
    }

    public Song(int id, String name, Artist artist, Album album, List<Genre> genres){
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genres = genres;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Artist getArtist() {
        return artist;
    }
    public Album getAlbum(){
        return album;
    }
    public List<Genre> getGenres() {
        return genres;
    }

}
