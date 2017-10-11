package models;

import java.util.ArrayList;

public class Wallet {

    private Integer money;
    private Integer experience;
    private ArrayList<BasicItem> artifactList;
    private Integer level;

    public Wallet() {

        this.money = 0;
        this.experience = 0;
        this.level = 0;
        artifactList = new ArrayList<>();
    }


    public Wallet(Integer money,Integer experience, Integer level){
        this.money = money;
        this.experience = experience;
        this.level = level;

    }


    public void add(Integer amount) {

        this.money += amount;
    }


    public void substract(Integer amount) {

            this.money -= amount;

    }


    public Integer getBalance() {

        return money;
    }

    public Integer getExperience() {
        return experience;
    }

    public Integer getLevel() {

        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "money=" + money +
                ", experience=" + experience +
                ", artifactList=" + artifactList +
                ", level=" + level +
                '}';
    }
}
