package DAO;

import java.util.ArrayList;

public class GroupDAO<T> implements InterfaceDAO<T> {

    private ArrayList<T> groupList = new ArrayList<T>();

    public void add(T group){
        groupList.add(group);
    }

    public ArrayList get(){
        return groupList;
    }

    public void set(T group) {

        for(T item : groupList) {
            System.out.println(item);
        }
    }
}
