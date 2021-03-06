package models;

import java.util.StringJoiner;

public class Inventory {

    private Integer studentID;
    private Integer artifactID;
    private String date;
    private String name;
    private String description;
    private Integer price;
    private boolean isMagic;


    public Inventory(Integer studentID, Integer artifactID, String date, Integer price) {

        this.studentID = studentID;
        this.artifactID = artifactID;
        this.date = date;
        this.price = price;
    }

    public Inventory(String name, String description, String date, Integer price, boolean isMagic) {

        this.name = name;
        this.description = description;
        this.date= date;
        this.price = price;
        this.isMagic = isMagic;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public Integer getArtifactID() {
        return artifactID;
    }

    public String getDate() {
        return date;
    }

    public Integer getPrice() { return price; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getCategory() {
        if(isMagic) {
            return "Magic Item";
        } else {
            return "Basic Item";
        }
    }
//    public String toString() {
//
//        StringJoiner joiner = new StringJoiner(" | ");
//        joiner.add("Name : " + name);
//        joiner.add("Description : " + description);
//        joiner.add("Date : " + date);
//        joiner.add("Price : " + price);
//
//        if(isMagic) {
//            joiner.add("Magic Item");
//        }
//
//        else {
//            joiner.add("Basic Item");
//        }
//        String joinedString = joiner.toString();
//
//        return joinedString;
//    }
}
