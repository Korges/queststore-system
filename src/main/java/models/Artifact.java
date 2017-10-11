package models;

import java.util.StringJoiner;

public abstract class Artifact {

    private Integer id;
    private String name;
    private Integer price;
    private boolean isUsed;
    private String description;
    private boolean isMagic;

    Artifact(String name, String description, Integer price, boolean isMagic ) {

        this.price = price;
        this.name = name;
        this.description = description;
        this.isMagic = isMagic;
        isUsed = false;
    }

    Artifact(Integer id, String name, String description, Integer price, boolean isMagic ) {

        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.isMagic = isMagic;
        isUsed = false;
    }

        public Integer getID() {
        return id;
    }


    public String toString() {

        StringJoiner joiner = new StringJoiner(" | ");
        joiner.add("ID: " + id);
        joiner.add("Name: " + name);
        joiner.add("Description: " + description);
        joiner.add("Price: " + price);
        joiner.add("Item type: " + this.getClass().getSimpleName());
        String joinedString = joiner.toString();

        return joinedString;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsMagic() {
        return isMagic;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(Integer price){
        this.price = price;
    }

    public void setCategory(boolean category){
        this.isMagic = category;
    }

}
