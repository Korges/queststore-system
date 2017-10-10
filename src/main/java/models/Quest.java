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
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", experience=" + experience +
                ", category='" + category + '\'' +
                '}';
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void switchCategory() {
        if (category.toLowerCase().equals("thequest")) {
            category = "magicQuest";
        }else {
            category = "theQuest";
        }
    }
}