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
        handleMainMenu();
    }
    public void handleMainMenu() {

        String choice;

        do {
            MentorUI.printMenu(MentorUI.menuMainOptions);
            choice = MentorUI.getChoice();

            switch (choice) {

                case "1": {
                    studentPanel();
                    break;
                }
                case "2": {
                    artifactPanel();
                    break;
                }
                case "3": {
                    questPanel();
                    break;
                }
                case "4":{
                    checkStudentsWallet();
                    break;

                }

            }
        } while(!choice.equals("0"));
    }

    public void studentPanel() {
        String choice;
        do {

            MentorUI.printMenu(MentorUI.menuStudentOptions);
            choice = MentorUI.getChoice();

            switch (choice) {

                case "1": {
                    createStudent();
                    break;
                }
                case "2": {
                    editStudent();
                    break;
                }
                case "3": {
                    deleteStudent();
                    break;
                }
                case "4": {
                    listAllStudents();
                    break;
                }

            }
        } while(!choice.equals("0"));

    }

    public void artifactPanel() {
        String choice;
        do {

            MentorUI.printMenu(MentorUI.menuArtifactOptions);
            choice = MentorUI.getChoice();

            switch (choice) {

                case "1": {
                    createArtifact();
                    break;
                }
                case "2": {
                    editArtifact();
                    break;
                }
                case "3": {
                    deleteArtifact();
                    break;
                }
                case "4": {
                    listAllArtifacts();
                }

            }
        } while(!choice.equals("0"));

    }

    public void questPanel() {
        String choice;
        do {

            MentorUI.printMenu(MentorUI.menuQuestOptions);
            choice = MentorUI.getChoice();

            switch (choice) {

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

        String name = UI.getString("Enter Artifact Name: ");
        String description = UI.getString("Enter Artifact Description: ");
        Integer price = UI.getInteger("Enter Artifact Price: ");
        boolean isMagic = UI.getBoolean("Is item Magic? [Y/N]: ");
        if(isMagic) {
            MagicItem newItem = new MagicItem(name, description, price, isMagic);
            artifactDAO.add(newItem);
        } else {
            BasicItem newItem = new BasicItem(name, description, price, isMagic);
            artifactDAO.add(newItem);
        }
    }




    private void editArtifact() {

        ArrayList<Artifact> artifactList = artifactDAO.get();
        listAllArtifacts();

        if(artifactList.size() != 0){
            Integer ID = UI.getInteger("Choose Artifact by ID");

            for(Artifact artifact: artifactList) {
                if (ID.equals(artifact.getID())) {

                    if (UI.getBoolean("Do you want change Artifact's name? [Y/N]")) {
                        artifact.setName(UI.getString("Enter new Name: "));
                    }

                    if (UI.getBoolean("Do you want change Artifact's description? [Y/N]")) {
                        artifact.setDescription(UI.getString("Enter new Description: "));
                    }

                    if (UI.getBoolean("Do you want change Artifact's price? [Y/N]")) {
                        artifact.setPrice(UI.getInteger("Enter new Price: "));
                    }

                    if (UI.getBoolean("Do you want change Artifact's category? [Y/N]")) {
                        artifact.setCategory(UI.getBoolean("Is item Magic? [Y/N]: "));
                    }
                    artifactDAO.set(artifact);
                }
            }
        }

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

    private void listAllArtifacts() {

        ArrayList<Artifact> artifactList = artifactDAO.get();
        if(artifactList.size() == 0){
            UI.showMessage("Artifact list is empty!");
        } else {
            for(Artifact artifact: artifactList){
                System.out.println(artifact.toString());
            }
        }
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

    private void deleteArtifact() {

        ArrayList<Artifact> artifactList = artifactDAO.get();
        listAllArtifacts();

        if(artifactList.size() != 0) {
            Integer ID = UI.getInteger("Choose Artifact by ID");

            for (Artifact artifact : artifactList) {
                if (ID.equals(artifact.getID())) {
                    artifactDAO.remove(artifact);
                }
            }
        }

    }

    private void checkStudentsWallet(){
        ArrayList<Student> students = studentDAO.get();
        for(Student student : students){
            UI.showMessage(student.getFullName() + " account balance : " + student.wallet.getBalance());
        }
    }


}
