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
    private String generateCategory(Boolean isMagic) {
        String category;
        if (isMagic) {
            category = "magicQuest";
        } else {
            category = "theQuest";
        }

        return category;
    }

    private void createQuest() {

        String name = UI.getString("Enter name: ");
        String description = UI.getString("Enter description: ");
        Integer value = UI.getInteger("Enter Value: "); // todo input integer
        Boolean isMagic = UI.getBoolean("Is quest Extra(y) or basic(n)?");
        String category = generateCategory(isMagic);
        Quest quest = new Quest(name, description, value, value, category);
        questDAO.add(quest);
        System.out.println(quest.toString());
    }

    private void editQuest() {

    }

    private void createArtifact() {

        String choice;
        boolean toContinue = true;

        while (toContinue) {

            toContinue = false;
            choice = UI.getString("(1)Basic or (2)Magic? (0)Back to menu");

            if (choice.equals("1")) {
               createBasicItem();

            } else if (choice.equals("2")) {
               createMagicItem();

            } else if (choice.equals("0")) {
                toContinue = false;

            } else {
                toContinue = true;
            }
        }
    }

    private void createBasicItem() {

        String name = UI.getString("Enter name: ");
        String description = UI.getString("Enter description: ");
        Integer price = 10; // todo input integer
        BasicItem basicItem = new BasicItem(null, name, description, price);
        artifactDAO.add(basicItem); //todo raw type
    }

    private void createMagicItem() {

        String name = UI.getString("Enter name: ");
        String description = UI.getString("Enter description: ");
        Integer price = 10; // todo input integer
        MagicItem magicItem = new MagicItem(null, name, description, price);
        artifactDAO.add(magicItem); //todo raw type
    }


    private void editArtifact() {

    }

    private void markSubmission() {

    }

    private void markArtifact() {

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

    private void listAllStudents() {

        ArrayList<Student> studentList = studentDAO.get();
        if(studentList.size() == 0){
            UI.showMessage("Student list is empty!");
        } else {
            for(Student student: studentList){
                System.out.println(student.toString());
            }
        }
    }

    private void listArtifacts() {

        printList(artifactDAO.get());
    }

    private void deleteStudent() {

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
