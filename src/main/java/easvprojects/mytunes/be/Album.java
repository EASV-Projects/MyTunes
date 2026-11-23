package easvprojects.mytunes.be;

public class Album {
    private int id;
    private String name;

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

    public void setName(){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}


