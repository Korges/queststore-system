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

    public Fundraise(Integer fundraiseID, Integer artifactID, String title, String name) {
        this.fundraiseID = fundraiseID;
        this.artifactID = artifactID;
        this.title = title;
        this.name = name;
    }

    public Fundraise(Integer studentID, Integer fundraiseID, Integer artifactID, String title, String name, Integer price ) {
        this.studentID = studentID;
        this.fundraiseID = fundraiseID;
        this.artifactID = artifactID;
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
        String joinedString = joiner.toString();

        return joinedString;
    }

    public String toStringCheck() {
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
