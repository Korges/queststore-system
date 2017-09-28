package models;

public class BasicItem extends Artifact {

    public BasicItem(Integer id, String name, String description, Integer price, boolean isMagic) {

        super(id, name, description, price, isMagic);
    }

    public BasicItem(String name, String description, Integer price, boolean isMagic) {

        super(name, description, price, isMagic);
    }
}
