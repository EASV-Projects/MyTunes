package easvprojects.mytunes.be;

public class Song {
    private int id;
    private String name;
    private int artist_id;
    private int album_id;
    private int genre_id;

    public Song(String name){
        this.name = name;
    }

    public Song(int id, String name, int artist_id, int album_id){
        this.id = id;
        this.name = name;
        this.artist_id = artist_id;
        this.album_id = album_id;
    }

    public int getId(){
        return id;
    }

    public int getArtist_id(){
        return artist_id;
    }

    public int getAlbum_id(){
        return album_id;
    }

    public String getName(){
        return name;
    }

}
