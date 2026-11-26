package easvprojects.mytunes.be;

import java.nio.file.Path;
import java.util.List;
import java.time.Duration;

public class Song {
    private int id;
    private String name;
    private Artist artist;
    private Album album;
    private List<Genre> genres;
    private Duration length;
    private Path filePath;

    public Song(int id, String name, Artist artist, Album album, List<Genre> genres, Duration length, Path filePath){
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genres = genres;
        this.length = length;
        this.filePath = filePath;
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
    public Duration getLength(){
        return length;
    }
    public Path getFilePath(){
        return filePath;
    }

}
