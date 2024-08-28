package a5.cmpt213.server.models;



public class Tokimon {
    private String type;
    private int rarity;
    private String name;
    private int id;
    private int hp;
    private String url;

    public Tokimon(String type, int rarity, String name, int hp, String URL) {

        this.type = type;
        this.rarity = rarity;
        this.name = name;
        this.hp = hp;
        this.url = URL;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String URL) {
        this.url = URL;
    }

}
