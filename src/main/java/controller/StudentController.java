package controller;

import UI.StudentUI;
import models.Student;

public class StudentController {

    Student student;
    public StudentController(Student student)
    {
        this.student = student;
    }
    public void startController(){
        handleMenu();
    }
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

        Integer balance = student.wallet.getBalance();
        System.out.println("Your balance: " + balance);
    }

    public void buyArtifact() {

    }

    public void donateFundraise() {

    }

    public void checkExperience() {

    }
}
