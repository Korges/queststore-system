package models;


import java.util.StringJoiner;

public class Fundraise {

    private Integer fundraiseID;
    private Integer artifactID;
    private Integer studentID;
    private Integer price;
    private String title;
    private String name;


    public Fundraise(Integer artifactID, String title) {

        this.artifactID = artifactID;
        this.title = title;
    }


    public Fundraise(Integer id,  String title, String name, Integer price ) {

        this.fundraiseID = id;
        this.title = title;
        this.name = name;
        this.price = price;
    }

    public Integer getFundraiseID() {
        return fundraiseID;
    }

    public Integer getArtifactID() {
        return artifactID;
    }

    public String getTitle() {
        return title;
    }

    public String getName() { return name; }

    public String toString() {

        StringJoiner joiner = new StringJoiner(" | ");
        joiner.add("ID: " + fundraiseID);
        joiner.add("Title: " + title);
        joiner.add("Name: " + name);
        joiner.add("Price: " + price);
        String joinedString = joiner.toString();

        return joinedString;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public Integer getPrice() {
        return price;
    }
}
