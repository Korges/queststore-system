package models;

public class QuestCategory {

    private String name;

    public QuestCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {

        String data;
        data = "Category name: " + name;

        return data;
    }
}