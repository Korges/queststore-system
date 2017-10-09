package controller;

import DAO.GroupDAO;
import DAO.MentorDAO;
import DAO.StudentDAO;
import UI.StudentUI;
import UI.UI;
import models.Mentor;
import models.Student;

public class StudentController {

    private Student student_me;

    public StudentController(Student student){
        student_me = student;
    }


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
                case "4":{
                    checkExperience();
                    break;
                }
            }
        } while(!choice.equals("0"));
    }

    public void checkWallet() {

        Integer balance = student_me.wallet.getBalance();
        System.out.println("Your balance: " + balance);
    }

    public void buyArtifact() {

    }

    public void donateFundraise() {

    }

    public void checkExperience() {
        Integer experience = student_me.wallet.getExperience();
        UI.showMessage("Your experience: " + experience);

    }
}
