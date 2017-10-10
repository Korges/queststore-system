package models;


import java.util.StringJoiner;

public class Fundraise {

    private Integer fundraiseID;
    private Integer artifactID;
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

    public Integer getFundraiseID() {
        return fundraiseID;
    }

    public Integer getArtifactID() {
        return artifactID;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(" | ");
        joiner.add("ID: " + fundraiseID);
        joiner.add("Title: " + title);
        joiner.add("Name: " + name);
        String joinedString = joiner.toString();

        return joinedString;
    }
}
