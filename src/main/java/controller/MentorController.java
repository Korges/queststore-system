package controller;

import DAO.ArtifactDAO;
import DAO.QuestDAO;
import DAO.StudentDAO;
import UI.MentorUI;
import models.*;
import UI.UI;
import java.util.ArrayList;

public class MentorController {

    private StudentDAO studentDAO = new StudentDAO();
    private QuestDAO questDAO = new QuestDAO();
    private ArtifactDAO artifactDAO = new ArtifactDAO();

    public void startController(){
        handleMenu();
    }
    public void handleMenu() {

        String choice;

        do {
            MentorUI.printMenu();
            choice = MentorUI.getChoice();

            switch (choice) {

                case "1": {
                    listAllStudents();
                    break;
                }
                case "2": {
                    createStudent();
                    break;
                }
                case "3": {
                    printQuests();
                    createQuest();
                    break;
                }
                case "4": {
                    editQuest();
                    break;

                }case "5": {
                    listArtifacts();
                    createArtifact();
                    break;
                }
                case "6": {
                    editArtifact();
                    break;
                }
                case "7": {
                    markSubmission();
                    break;
                }
                case "8": {
                    markArtifact();
                    break;
                }
//                case "9": {
//                    invigilate();
//                    break;
//                }
                case "9": {
                    editStudent();
                    break;
                }
                case "10": {
                    deleteStudent();
                    break;
                }


            }
        } while(!choice.equals("0"));
    }

    public void createStudent() {


        String firstName = UI.getString("Enter First Name: ");
        String lastName = UI.getString("Enter Last Name: ");
        String password = UI.getString("Enter Password: ");
        String email = UI.getEmail();
        String klass = UI.getString("Enter Klass number: ");
        Student newStudent = new Student(firstName, lastName, email, password, klass);
        studentDAO.add(newStudent);

    }

    public void editStudent() {

        ArrayList<Student> studentList = studentDAO.get();
        listAllStudents();

        if(studentList.size() != 0){
            Integer ID = UI.getInteger("Choose Student by ID");

            for(Student student: studentList) {
                if (ID.equals(student.getID())) {

                    if (UI.getBoolean("Do you want change Mentor's first name? [Y/N]")) {
                        student.setFirstName(UI.getString("Enter new First Name: "));
                    }

                    if (UI.getBoolean("Do you want change Mentor's last name? [Y/N]")) {
                        student.setLastName(UI.getString("Enter new Last Name: "));
                    }

                    if (UI.getBoolean("Do you want change Mentor's email? [Y/N]")) {
                        student.setEmail(UI.getString("Enter new Email: "));
                    }

                    if (UI.getBoolean("Do you want change Mentor's password? [Y/N]")) {
                        student.setPassword(UI.getString("Enter new Password: "));
                    }
                    studentDAO.set(student);
                }
            }
        }
    }



    public void createQuest() {

        String name = UI.getString("Enter name: ");
        String description = UI.getString("Enter description: ");
        Integer value = UI.getInteger("Enter Value: "); // todo input integer
        String categoryName = UI.getString("Enter category name: ");
        QuestCategory category = new QuestCategory(categoryName);
        Quest quest = new Quest(name, description, value, category);
        questDAO.add(quest);
        System.out.println(quest.toString());
    }

    public void editQuest() {

    }

    public void createArtifact() {

        String name = UI.getString("Enter Artifact Name: ");
        String description = UI.getString("Enter Artifact Description: ");
        Integer price = UI.getInteger("Enter Artifact Price: ");
        boolean isMagic = UI.getBoolean("Is item Magic? [Y/N]: ");
        if(!isMagic) {
            BasicItem newItem = new BasicItem(name, description, price, isMagic);
            artifactDAO.add(newItem);
        } else {
            MagicItem newItem = new MagicItem(name, description, price, isMagic);
            artifactDAO.add(newItem);
        }
    }




    public void editArtifact() {

    }

    public void markSubmission() {

    }

    public void markArtifact() {

    }

    public void invigilate() {

    }

    private void printQuests() {

        printList(questDAO.get());
    }

    private<T> void printList(ArrayList<T> list) {

        if (list.size() < 1) {
            System.out.println("List is empty.");
        }

        int i = 1;

        for (T t:  list ) {

            System.out.println(Integer.toString(i) + ". " + t);
            i++;
        }
    }

    public void listAllStudents() {

        ArrayList<Student> studentList = studentDAO.get();
        if(studentList.size() == 0){
            UI.showMessage("Student list is empty!");
        } else {
            for(Student student: studentList){
                System.out.println(student.toString());
            }
        }
    }

    public void listArtifacts() {

        printList(artifactDAO.get());
    }

    public void deleteStudent() {

        ArrayList<Student> studentList = studentDAO.get();
        listAllStudents();

        if(studentList.size() != 0) {
            Integer ID = UI.getInteger("Choose Student by ID");

            for (Student student : studentList) {
                if (ID.equals(student.getID())) {

                    studentDAO.remove(student);
                }
            }
        }

    }


}
