package controller;

import DAO.GroupDAO;
import DAO.MentorDAO;
import DAO.StudentDAO;
import UI.StudentUI;
import models.Mentor;
import models.Student;

public class StudentController {


    public void startController(){
        handleMenu();
    }
    public StudentDAO student = new StudentDAO();

    public void handleMenu() {

        String choice;
        do {

            StudentUI.printMenu();
            choice = StudentUI.getChoice();

            switch (choice){

                case "1": {
                    checkWallet();
                    break;
                }
                case "2": {
                    buyArtifact();
                    break;
                }
                case "3": {
                    donateFundraise();
                    break;
                }
            }
        } while(!choice.equals("0"));
    }

    public void checkWallet() {

//        Integer balance = wallet.getBalance();
//        System.out.println("Your balance: " + balance);
    }

    public void buyArtifact() {

    }

    public void donateFundraise() {

    }

    public void checkExperience() {

    }
}
