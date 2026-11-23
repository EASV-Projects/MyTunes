package easvprojects.mytunes.be;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private String name;
    private String description;
    private List<Song> songs = new ArrayList<>();

    public Playlist(int id, String name, String description, List<Song> songs){
        this.id = id;
        this.name = name;
        this.description =  description;
        this.songs = songs;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public List<Song> getSongs(){
        return this.songs;
    }

}
