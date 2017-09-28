package models;


import java.util.StringJoiner;

public class Quest {
    private int id;
    private String name;
    private String description;
    private int value;
    private int experience;
    private String category;



    public Quest(String name, String description, int value, int experience, String category) {

        this.name = name;
        this.description = description;
        this.value = value;
        this.experience = experience;
        this.category = category;
    }

    public Quest(Integer id, String name, String description, int value, int experience, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.experience = experience;
        this.category = category;
        //todo how to DRY
    }


    public String toString() {

        StringJoiner joiner = new StringJoiner("|");
        joiner.add("Name: " + name);
        joiner.add("Description: " + description);
        joiner.add("Value: " + value);

        return joiner.toString();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public int getExperience() {
        return experience;
    }

    public String getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }
}