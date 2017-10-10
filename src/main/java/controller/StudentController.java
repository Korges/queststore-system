package controller;

import DAO.*;
import UI.StudentUI;
import UI.UI;
import models.*;

import java.util.ArrayList;

public class StudentController {

    private ArtifactDAO artifactDAO = new ArtifactDAO();
    private StudentDAO studentDAO= new StudentDAO();
    private InventoryDAO inventoryDAO = new InventoryDAO();
    private FundraiseDAO fundraiseDAO = new FundraiseDAO();

    private Student user;

    public StudentController(Student student){

        user = student;
    }


    public void startController(){

        handleMainMenu();

    }


    private void handleMainMenu() {

        String choice;

        do {
            StudentUI.printLabel(StudentUI.mainMenuLabel);
            StudentUI.printMenu(StudentUI.mainMenuOptions);
            choice = StudentUI.getChoice();

            switch (choice){

                case "1": {
                    artifactPanel();
                    break;
                }
                case "2": {
                    walletPanel();
                    break;
                }
            }
        } while(!choice.equals("0"));
    }


    private void artifactPanel() {

        String choice;
        do {
            StudentUI.printLabel(StudentUI.artifactMenuLabel);
            StudentUI.printMenu(StudentUI.artifactMenuOptions);
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


    private void fundraisePanel() {
        String choice;
        do {
            StudentUI.printLabel(StudentUI.fundraiseMenuLabel);
            StudentUI.printMenu(StudentUI.fundraiseMenuOptions);
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



    private void checkBalance() {

        Integer balance = user.getWallet().getBalance();
        System.out.println("Your balance: " + balance);
    }

    private boolean checkEnoughBalance(Artifact artifact) {

        boolean bool = false;

        Integer balance = user.getWallet().getBalance();
        if (balance >= artifact.getPrice()) {
            bool = true;
        }
        else {
            UI.showMessage("Not Enough Money!");
        }
        return bool;
    }

    private void buyArtifact() {

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
                                user.getWallet().substract(artifact.getPrice());
                                studentDAO.editWalletValue(user);
                                Inventory inventory = new Inventory(user.getID(), artifact.getID(), UI.getCurrentDate());
                                inventoryDAO.add(inventory);
                            }
                        }
                    }
                }
            }
        }
    }

    private void createFundraise() {
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

    private boolean isInFundraise(Student student_me) {
        ArrayList<Fundraise> fundraiseStudentList = fundraiseDAO.getFundraisesStudents();
        boolean bool = false;
        for(Fundraise fundraise : fundraiseStudentList) {
            if(student_me.getID().equals(fundraise.getStudentID())) {
                bool = true;
            }
        }
        return bool;
    }


    private void joinExistingFundraise() {
        ArrayList<Fundraise> fundraiseList = fundraiseDAO.get();

        listAllExistingFundraise();
        if (fundraiseList.size() != 0) {
            boolean isTrue = true;

            while (isTrue) {

                Integer ID = UI.getInteger("Join Existing Fundraise by ID  :");

                for (Fundraise fundraise : fundraiseList) {
                    if (ID.equals(fundraise.getFundraiseID())) {
                        isTrue = false;
                        if(!isInFundraise(user)) {
                            fundraiseDAO.join(fundraise, user);
                        }
                        else {
                            UI.showMessage("You are already member of the same Fundraise!");
                        }
                    }
                }
            }
        }
    }

    private void leaveFundraise() {
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

    private void checkJoinedFundraises() {
        ArrayList<Fundraise> fundraiseList = fundraiseDAO.getFundraisesStudents();
        if(fundraiseList.size() == 0){
            UI.showMessage("Fundraise list is empty!");
        }
        else {
            for(Fundraise fundraise : fundraiseList) {
                if(user.getID().equals(fundraise.getStudentID())) {
                    System.out.println(fundraise.toStringCheck());
                }
                else {
                    UI.showMessage("You are not member of any Fundraise!");
                }
            }
        }
    }
    private void walletPanel() {
        String choice;
        do {
            StudentUI.printLabel(StudentUI.walletMenuLabel);
            StudentUI.printMenu(StudentUI.walletMenuOptions);
            choice = StudentUI.getChoice();

            switch (choice) {

                case "1": {
                    printWalletStatus();
                    break;
                }


            }
        } while(!choice.equals("0"));


    }

    private void printWalletStatus() {
        System.out.println(user.getWallet().toString());
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
            UI.showMessage("Artifact list is empty!");
        } else {
            for(Fundraise fundraise: fundraiseList){
                System.out.println(fundraise.toString());
            }
        }
    }




    private void checkStudentArtifacts() {

        ArrayList<Inventory> inventoryList = inventoryDAO.getStudentInventory(user);
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
