package models;

public class Group {

    private Integer id;
    private String name;

    public Group(String name) {
        this.name = name;
    }

    public Group(Integer id, String name) {

        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getID() {
        return id;
    }

    public String toString(){

        String view = String.format("ID: %s, Name: %s", this.getID(), this.getName());
        return view;
    }
}
