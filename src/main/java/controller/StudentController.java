package controller;

import DAO.*;
import UI.StudentUI;
import UI.UI;
import models.*;

import java.util.ArrayList;

public class StudentController {

    private ArtifactDAO artifactDAO = new ArtifactDAO();
    public StudentDAO studentDAO= new StudentDAO();
    public InventoryDAO inventoryDAO = new InventoryDAO();
    public FundraiseDAO fundraiseDAO = new FundraiseDAO();

    private Student student_me;

    public StudentController(Student student){

        student_me = student;
    }


    public void startController(){

        handleMainMenu();

    }


    public void handleMainMenu() {

        String choice;

        do {
            StudentUI.printLabel(StudentUI.mainMenuLabel);
            StudentUI.printMenu(StudentUI.menuMainOptions);
            choice = StudentUI.getChoice();

            switch (choice){

                case "1": {
                    artifactPanel();
                    break;
                }
                case "2": {
                    experience();
                    break;
                }
            }
        } while(!choice.equals("0"));
    }


    public void artifactPanel() {

        String choice;
        do {
            StudentUI.printLabel(StudentUI.artifactMenuLabel);
            StudentUI.printMenu(StudentUI.menuArtifactOptions);
            choice = StudentUI.getChoice();

            switch (choice) {

                case "1": {
                    listAllArtifacts();
                    break;
                }
                case "2": {
                    buyArtifact();
                    break;
                }
                case "3": {
                   fundraisePanel();
                    break;
                }
                case "4": {
                    checkBalance();
                    break;
                }
                case "5": {
                    checkStudentArtifacts();
                    break;
                }
            }
        } while(!choice.equals("0"));
    }


    public void fundraisePanel() {
        String choice;
        do {
            StudentUI.printLabel(StudentUI.fundraiseMenuLabel);
            StudentUI.printMenu(StudentUI.menuFundraiseOptions);
            choice = StudentUI.getChoice();

            switch (choice) {

                case "1": {
                    createFundraise();
                    break;
                }

                case "2": {
                    joinExistingFundraise();
                    break;
                }

                case "3": {
                    leaveFundraise();
                    break;
                }

                case "4": {
                    checkJoinedFundraises();
                    break;
                }

                case "5": {
                    listAllExistingFundraise();
                    break;
                }

            }
        } while(!choice.equals("0"));

    }



    public void checkBalance() {

        Integer balance = student_me.wallet.getBalance();
        System.out.println("Your balance: " + balance);
    }

    public boolean checkEnoughBalance(Artifact artifact) {

        boolean bool = false;

        Integer balance = student_me.wallet.getBalance();
        if (balance >= artifact.getPrice()) {
            bool = true;
        }
        else {
            UI.showMessage("Not Enough Money!");
        }
        return bool;
    }

    public void buyArtifact() {

        ArrayList<Artifact> artifactList = artifactDAO.get();
        listAllArtifacts();


        if(artifactList.size() != 0) {

            boolean isTrue = true;

            while (isTrue) {

                Integer ID = UI.getInteger("Choose Artifact by ID :");

                for (Artifact artifact : artifactList) {

                    if (ID.equals(artifact.getID())) {

                        isTrue = false;

                        if (checkEnoughBalance(artifact)) {

                            if (UI.getBoolean("Do you want to buy : " + artifact.getName() + " ?")) {
                                student_me.wallet.substract(artifact.getPrice());
                                studentDAO.editWalletValue(student_me);
                                Inventory inventory = new Inventory(student_me.getID(), artifact.getID(), UI.getCurrentDate());
                                inventoryDAO.add(inventory);
                            }
                        }
                    }
                }
            }
        }
    }

    public void createFundraise() {
        ArrayList<Artifact> artifactList = artifactDAO.getMagicItems();
        listAllMagicArtifacts();

        if(artifactList.size() != 0) {

            boolean isTrue = true;

            while (isTrue) {

                Integer ID = UI.getInteger("Choose Artifact by ID to create new Fundraise :");

                for (Artifact artifact : artifactList) {

                    if (ID.equals(artifact.getID())) {

                        isTrue = false;
                        String title = UI.getString("Enter Fundraise Title :");
                        Fundraise fundraise = new Fundraise(artifact.getID(), title);
                        fundraiseDAO.add(fundraise);

                    }
                }
            }
        }
    }

    public boolean isInFundraise(Student student_me) {
        ArrayList<Fundraise> fundraiseStudentList = fundraiseDAO.getFundraisesStudents();
        boolean bool = false;
        for(Fundraise fundraise : fundraiseStudentList) {
            if(student_me.getID().equals(fundraise.getStudentID())) {
                bool = true;
            }
        }
        return bool;
    }



    public void joinExistingFundraise() {
        ArrayList<Fundraise> fundraiseList = fundraiseDAO.get();

        listAllExistingFundraise();
        if (fundraiseList.size() != 0) {
            boolean isTrue = true;

            while (isTrue) {

                Integer ID = UI.getInteger("Join Existing Fundraise by ID  :");

                for (Fundraise fundraise : fundraiseList) {
                    if (ID.equals(fundraise.getFundraiseID())) {
                        isTrue = false;
                        if(!isInFundraise(student_me)) {
                            fundraiseDAO.join(fundraise, student_me);
                        }
                        else {
                            UI.showMessage("You are already member of the same Fundraise!");
                        }
                    }
                }
            }
        }
    }

    public void leaveFundraise() {
        ArrayList<Fundraise> fundraiseList = fundraiseDAO.getFundraisesStudents();
        checkJoinedFundraises();
        if (fundraiseList.size() != 0) {
            boolean isTrue = true;

            while (isTrue) {

                Integer ID = UI.getInteger("Leave Fundraise by ID  :");

                for (Fundraise fundraise : fundraiseList) {
                    if (ID.equals(fundraise.getFundraiseID())) {
                        isTrue = false;
                        fundraiseDAO.remove(fundraise);
                    }

                }

            }
        }

    }

    public void checkJoinedFundraises() {
        ArrayList<Fundraise> fundraiseList = fundraiseDAO.getFundraisesStudents();
        if(fundraiseList.size() == 0){
            UI.showMessage("Fundraise list is empty!");
        }
        else {
            for(Fundraise fundraise : fundraiseList) {
                if(student_me.getID().equals(fundraise.getStudentID())) {
                    System.out.println(fundraise.toStringCheck());
                }
                else {
                    UI.showMessage("You are not member of any Fundraise!");
                }
            }
        }
    }
    public void experience() {

    }

    public void checkExperience() {
        Integer experience = student_me.wallet.getExperience();
        UI.showMessage("Your experience: " + experience);

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

    private void listAllMagicArtifacts() {

        ArrayList<Artifact> artifactList = artifactDAO.getMagicItems();
        if(artifactList.size() == 0){
            UI.showMessage("Artifact list is empty!");
        } else {
            for(Artifact artifact: artifactList){
                System.out.println(artifact.toString());
            }
        }
    }

    private void listAllExistingFundraise() {
        ArrayList<Fundraise> fundraiseList = fundraiseDAO.get();
        if(fundraiseList.size() == 0){
            UI.showMessage("Fundraise list is empty!");
        } else {
            for(Fundraise fundraise: fundraiseList){
                System.out.println(fundraise.toString());
            }
        }
    }




    public void checkStudentArtifacts() {

        ArrayList<Inventory> inventoryList = inventoryDAO.getSingleStudent(student_me);
        int no = 0;
        if(inventoryList.size() == 0){
            UI.showMessage("Purchase list is empty!");
        } else {
           for(Inventory inventory: inventoryList) {
               no++;
               System.out.println(String.format("ID: %d | %s", no, inventory.toString()));


           }
        }
    }
}
