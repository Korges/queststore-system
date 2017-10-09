package models;

import java.util.ArrayList;

public class Wallet {

    private Integer money;
    private Integer experience;
    private ArrayList<BasicItem> artifactList;

    public Wallet() {

        this.money = 0;
        this.experience = 0;
        artifactList = new ArrayList<>();
    }


    public Wallet(Integer money,Integer experience){
        this.money = money;
        this.experience = experience;
    }


    void add(Integer amount) {

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

    void add(BasicItem item) {

        this.artifactList.add(item);
    }
}
