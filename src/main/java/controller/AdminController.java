package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.LevelExperienceDAO;
import UI.AdminUI;
import UI.MentorUI;
import DAO.MentorDAO;
import DAO.GroupDAO;
import models.Mentor;
import models.Group;

public class AdminController {

    private MentorDAO mDAO = new MentorDAO();
    private GroupDAO gDAO = new GroupDAO();

    public AdminController() throws SQLException{
        mDAO = new MentorDAO();
        gDAO = new GroupDAO();
    }

    public void startController() throws SQLException{
        handleMenu();
    }

    private void handleMenu() throws SQLException{

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
                    listAllMentors();
                    break;
                }
                case "5":{
                    deleteMentor();
                    break;
                }

                case "6":{
                    showGroup();
                    break;
                }
                case "7":{
                    createLevelOfExperience();
                    break;
                }
            }
        }while(!choice.equals("0"));
    }

    public void createMentor() throws SQLException{

        String firstName = MentorUI.getString("Enter First Name: ");
        String lastName = MentorUI.getString("Enter Last Name: ");
        String password = HashSystem.getStringFromSHA256(MentorUI.getString("Enter Password: "));
        String email = MentorUI.getEmail();
        showGroup();
        String klass = MentorUI.getString("Enter Klass id: ");
        Mentor newMentor = new Mentor(firstName, lastName, email, password, klass);
        mDAO.add(newMentor);
    }

    public void createGroup() throws SQLException{

        String name = AdminUI.getString("Enter new group name: ");
        Group newGroup = new Group(name);
        gDAO.add(newGroup);
        UI.AdminUI.showMessage("Successfully added group");
    }

    public void showGroup() throws SQLException{
        ArrayList<Group> groupList = gDAO.get();
        if(groupList.size() == 0){
            UI.UI.showMessage("Mentor list is empty!");
        } else {
            for(Group group: groupList){
                System.out.println(group.toString());
            }
        }
    }


    public void editMentor() throws SQLException{

        ArrayList<Mentor> mentorList = mDAO.get();
        listAllMentors();

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
                        mentor.setPassword(HashSystem.getStringFromSHA256(UI.UI.getString("Enter new Password: ")));
                    }
                    mDAO.set(mentor);
                }
            }
        }
    }

    public void listAllMentors() throws SQLException{

        ArrayList<Mentor> mentorList = mDAO.get();
        if(mentorList.size() == 0){
            UI.UI.showMessage("Mentor list is empty!");
        } else {
            for(Mentor mentor: mentorList){
                System.out.println(mentor.toString());
            }
        }
    }

    public void deleteMentor() throws SQLException{

        ArrayList<Mentor> mentorList = mDAO.get();
        listAllMentors();

        if(mentorList.size() != 0) {
            Integer ID = UI.UI.getInteger("Choose Mentor by ID");

            for (Mentor mentor : mentorList) {
                if (ID.equals(mentor.getID())) {
                    mDAO.remove(mentor);
                }
            }
        }
    }

    public void createLevelOfExperience() throws SQLException{
        Integer level = UI.UI.getInteger("Write level: ");
        Integer experience = UI.UI.getInteger("Write experience count");
        LevelExperienceDAO levelDao = new LevelExperienceDAO();
        levelDao.add(level,experience);

    }
}
