package easvprojects.mytunes.be;

import java.util.List;

public class Album {
    private int id;
    private String name;
    private List<Genre> genreList;
    private List<Song> songList;



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
    public List<Genre> getGenreList() { return genreList;}
    public List<Song> getSongList(){ return songList;}


}


