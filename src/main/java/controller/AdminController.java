package controller;

import java.util.ArrayList;

import UI.AdminUI;
import UI.MentorUI;
import DAO.MentorDAO;
import DAO.GroupDAO;
import models.Mentor;
import models.Group;

public class AdminController {

    public void startController(){
        handleMenu();
    }
    public MentorDAO mDAO = new MentorDAO();
    public GroupDAO gDAO = new GroupDAO();
    public void handleMenu() {

        String choice;

        do {

            AdminUI.printMenu();
            choice = AdminUI.getChoice();

            switch (choice){
                case "1":{
                    createMentor();
                    break;
                }
                case "2":{
                    createGroup();
                    break;
                }
                case "3":{
                    editMentor();
                    break;
                }
                case "4":{
                    viewMentor();
                    break;
                }
            }
        }while(!choice.equals("0"));
    }

    public void createMentor() {

        String firstName = MentorUI.getString("Enter First Name: ");
        String lastName = MentorUI.getString("Enter Last Name: ");
        String password = MentorUI.getString("Enter Password: ");
        String email = MentorUI.getEmail();
        String klass = MentorUI.getString("Enter Klass number: ");
        Mentor newMentor = new Mentor(firstName, lastName, email, password, klass);
        mDAO.add(newMentor);
    }

    public void createGroup() {

        String name = AdminUI.getString("Enter new group name: ");
        Group newGroup = new Group(name);
        gDAO.add(newGroup);
        UI.AdminUI.showMessage("Successfully added group");
    }

    public void showGroup(){
        ArrayList<Group> groupList = gDAO.get();
        if(groupList.size() == 0){
            UI.UI.showMessage("Mentor list is empty!");
        } else {
            for(Group group: groupList){
                System.out.println(group.toString());
            }
        }
    }


    public void editMentor() {

        ArrayList<Mentor> mentorList = mDAO.get();
        viewMentor();

        if(mentorList.size() != 0){
            Integer ID = UI.UI.getInteger("Choose Mentor by ID");

            for(Mentor mentor: mentorList) {
                if (ID.equals(mentor.getID())) {

                    if (UI.UI.getBoolean("Do you want change Mentor's first name? [Y/N]")) {
                        mentor.setFirstName(UI.UI.getString("Enter new First Name: "));
                    }

                    if (UI.UI.getBoolean("Do you want change Mentor's last name? [Y/N]")) {
                        mentor.setLastName(UI.UI.getString("Enter new Last Name: "));
                    }

                    if (UI.UI.getBoolean("Do you want change Mentor's email? [Y/N]")) {
                        mentor.setEmail(UI.UI.getString("Enter new Email: "));
                    }

                    if (UI.UI.getBoolean("Do you want change Mentor's password? [Y/N]")) {
                        mentor.setPassword(UI.UI.getString("Enter new Password: "));
                    }
                    mDAO.set(mentor);
                }

            }

        }
    }



    public void viewMentor() {

        ArrayList<Mentor> mentorList = mDAO.get();
        if(mentorList.size() == 0){
            UI.UI.showMessage("Mentor list is empty!");
        } else {
            for(Mentor mentor: mentorList){
                System.out.println(mentor.toString());
            }
        }
    }
}
