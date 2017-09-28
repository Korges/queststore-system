package models;


import java.util.ArrayList;
import java.util.StringJoiner;

public class Quest {

    private String name;
    private String description;
    private int value;
    private int experience;
    private QuestCategory category;

    public Quest(String name, String description, int value, QuestCategory category) {

        this.name = name;
        this.description = description;
        this.value = value;
        this.experience = value;
        this.category = category;
    }

    public String toString() {

        StringJoiner joiner = new StringJoiner("|");
        joiner.add("Name: " + name);
        joiner.add("Description: " + description);
        joiner.add("Value: " + value);
        joiner.add(category.toString());
        String joinedString = joiner.toString();

        return joinedString;
    }

    public String getName() {
        return name;
    }
}