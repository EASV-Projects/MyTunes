package easvprojects.mytunes.be;

public class Artist {
    private int id;
    private String name;

    public Artist(String name) {

        this.name = name;
    }

    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
