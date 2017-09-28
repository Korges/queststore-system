package DAO;

import models.Artifact;
import models.Quest;
import java.util.ArrayList;

public class QuestDAO implements InterfaceDAO<Quest> {

    private ArrayList<Quest> questList = new ArrayList<>();

    public void add(Quest quest){
        questList.add(quest);
    }

    public ArrayList get(){
        return questList;
    }

    public void set(Quest quest) {

        for(Quest item : questList) {
            System.out.println(item);
        }
    }

    public Quest getByName(String name) {
        for (Quest quest : questList) {
            if (quest.getName().equals(name)) {
                return quest; // what if 2 quests with the very same name?
            }
        }
        return null;
    }
}
