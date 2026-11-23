package easvprojects.mytunes.be;

import java.util.List;

public class Album {
    private int id;
    private String name;
    private List<Genre> genres;
    private List<Song> songs;



    public Album(String name){
        this.name = name;
    }

    public Album(int id, String name){
        this.name = name;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public List<Genre> getGenres() { return genres;}
    public List<Song> getSongs(){ return songs;}


}


