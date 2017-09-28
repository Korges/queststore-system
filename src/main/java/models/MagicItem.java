package models;

public class MagicItem extends Artifact {

    public MagicItem(Integer id, String name, String description, Integer price, boolean isMagic) {

        super(id, name, description, price, isMagic);
    }

    public MagicItem(String name, String description, Integer price, boolean isMagic) {

        super(name, description, price, isMagic);
    }
}
