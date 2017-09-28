package models;

import java.util.StringJoiner;

public abstract class Artifact {

    private Integer id;
    private String name;
    private Integer price;
    private boolean isUsed;
    private String description;

    Artifact(Integer id, String name, String description, Integer price) {

        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        isUsed = false;
    }

    public Integer getID() {
        return id;
    }

    public boolean useArtefact() {

        if (!isUsed) {

            isUsed = true;
            return true;

        }
        return false;
    }

    public String toString() {

        StringJoiner joiner = new StringJoiner(" | ");
        joiner.add("Name: " + name);
        joiner.add("Description: " + description);
        joiner.add("Price: " + price);
        joiner.add("isUsed: " + isUsed);
        joiner.add("Item type: " + this.getClass().getSimpleName());
        String joinedString = joiner.toString();

        return joinedString;
    }
}
