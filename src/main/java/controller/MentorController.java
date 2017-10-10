package controller;

import DAO.ArtifactDAO;
import DAO.FundraiseDAO;
import DAO.QuestDAO;
import DAO.StudentDAO;
import DAO.SubmissionDAO;
import UI.MentorUI;
import models.*;
import UI.UI;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MentorController {

    private StudentDAO studentDAO = new StudentDAO();
    private QuestDAO questDAO = new QuestDAO();
    private ArtifactDAO artifactDAO = new ArtifactDAO();
    private FundraiseDAO fundraiseDAO = new FundraiseDAO();
    private SubmissionDAO submissionDAO = new SubmissionDAO();

    public void startController(){

        handleMainMenu();
    }

    public void handleMainMenu() {

        String choice;

        do {
            MentorUI.printLabel(MentorUI.mainMenuLabel);
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
                case "5": {
                    fundraisePanel();
                    break;
                }

            }
        } while(!choice.equals("0"));
    }

    private void studentPanel() {
        String choice;
        do {
            MentorUI.printLabel(MentorUI.studentMenuLabel);
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
                    printAllStudents();
                    break;
                }

            }
        } while(!choice.equals("0"));

    }

    private void artifactPanel() {
        String choice;
        do {
            MentorUI.printLabel(MentorUI.artifactMenuLabel);
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
                    printAllArtifacts();
                }

            }
        } while(!choice.equals("0"));

    }

    private void questPanel() {
        String choice;
        do {
            MentorUI.printLabel(MentorUI.questMenuLabel);
            MentorUI.printMenu(MentorUI.menuQuestOptions);
            choice = MentorUI.getChoice();

            switch (choice) {
                case "1": {
                    createQuest();
                    break;
                }
                case "2": {
                    editQuest();
                    break;
                }
                case "3": {
                    markSubmission();
                    break;
                }
            }
        } while(!choice.equals("0"));

    }

    public void fundraisePanel() {
        String choice;
        do {
            MentorUI.printLabel(MentorUI.fundraiseMenuLabel);
            MentorUI.printMenu(MentorUI.menuFundraiseOptions);
            choice = MentorUI.getChoice();

            switch (choice) {
                case "1": {
                    listAllExistingFundraise();
                    break;
                }
                case "2": {

                    break;
                }
                case "3": {

                    break;
                }

            }
        } while(!choice.equals("0"));

    }



    private void createStudent() {


        String firstName = UI.getString("Enter First Name: ");
        String lastName = UI.getString("Enter Last Name: ");
        String password = HashSystem.getStringFromSHA256(UI.getString("Enter Password: "));
        String email = UI.getEmail();
        String klass = UI.getString("Enter Klass number: ");
        Student newStudent = new Student(firstName, lastName, email, password, klass);
        studentDAO.add(newStudent);

    }

    private void editStudent() {

        ArrayList<Student> studentList = studentDAO.get();
        printAllArtifacts();
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
        Integer value = UI.getInteger("Enter Value: ");
        Boolean isMagic = UI.getBoolean("Is quest Extra(y) or basic(n)?");
        String category = generateCategory(isMagic);
        Quest quest = new Quest(name, description, value, value, category);
        questDAO.add(quest);
        System.out.println(quest.toString());
    }

    private void editQuest() {

        ArrayList<Quest> questList =  questDAO.get() ;
        printAllQuests();


        if(questList.size() != 0){
            Integer id = UI.getInteger("Choose quest by ID");

            for(Quest quest: questList) {
                if (id.equals(quest.getId())) {

                    if (UI.getBoolean("Do you want change Quests name? [Y/N]")) {
                        quest.setName(UI.getString("Enter new Name: "));
                    }

                    if (UI.getBoolean("Do you want change Quests description? [Y/N]")) {
                        quest.setDescription(UI.getString("Enter new description: "));
                    }

                    if (UI.getBoolean("Do you want change Quests value? [Y/N]")) {
                        quest.setValue(UI.getInteger("Enter new value: "));
                    }

                    if (UI.getBoolean("Do you want change Quests experience? [Y/N]")) {
                        quest.setExperience(UI.getInteger("Enter new experience: "));
                    }

                    if (UI.getBoolean("Do you want change Quests category? " +
                            "Currently:" + quest.getCategory() + " [Y/N]")) {
                        quest.switchCategory();
                    }

                    questDAO.set(quest);
                }
            }
        }



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
        printAllArtifacts();

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
        ArrayList<Submission> submissionList = submissionDAO.get();
        printAllSubmissions();

        if (submissionList.size() != 0) {
            Integer id = UI.getInteger("Choose submission by submission ID");

            for (Submission submission : submissionList) {
                if (id.equals(submission.getId())) {

                    Integer studentId = submission.getStudentId();
                    Integer experience = submissionDAO.getSubmissionValue(submission.getId());
                    submission.setMarked(true);
                    studentDAO.setWalletDetail(studentId, experience);
                    submissionDAO.set(submission);
                }
            }
        }
    }

    private void markArtifact() {

    }

    public void invigilate() {

    }

    private void printAllSubmissions() {
        ArrayList<Submission> subList = submissionDAO.get();

        UI.printList(subList);
    }

    private<T> void printList(ArrayList<T> list) {

        if (list.size() < 1) {
            System.out.println("List is empty.");
        }
    }

    private void printAllStudents() {
        ArrayList<Student> studentList = studentDAO.get();

        UI.printList(studentList);
    }

    private void printAllArtifacts() {
        ArrayList<Artifact> artifactList = artifactDAO.get();

        UI.printList(artifactList);
    }

    private void printAllQuests() {
        ArrayList<Quest> questList = questDAO.get();

        UI.printList(questList);
    }

    private void deleteStudent() {

        ArrayList<Student> studentList = studentDAO.get();
        printAllStudents();

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
        printAllArtifacts();

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

    private void listAllExistingFundraise() {

        ArrayList<Fundraise> fundraiseList = fundraiseDAO.get();
        if(fundraiseList.size() == 0){
            UI.showMessage("Artifact list is empty!");
        } else {
            for(Fundraise fundraise: fundraiseList){
                System.out.println(fundraise.toString());
            }
        }
    }
}
