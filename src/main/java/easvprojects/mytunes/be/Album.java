package easvprojects.mytunes.be;

import java.util.List;

public class Album {
    private int id;
    private String name;
    private List<Genre> genres;
    private List<Song> songs;

    public Album(int id, String name, List<Genre> genre, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.genres = genre;
        this.songs = songs;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Song> getSongs() {
        return songs;
    }

}