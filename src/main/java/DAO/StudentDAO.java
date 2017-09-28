package DAO;

import java.util.ArrayList;

public class StudentDAO<T> implements InterfaceDAO<T> {

    private ArrayList<T> studentList = new ArrayList<T>();

    public void add(T student){
        studentList.add(student);
    }

    public ArrayList get(){
        return  studentList;
    }

    public void set(T student) {

        for(T item : studentList){
            System.out.println(item);
        }
    }
}
