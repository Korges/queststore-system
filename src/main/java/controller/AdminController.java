package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.helpers.HashSystem;
import controller.helpers.Sessions;
import models.Mentor;
import models.Group;

public class AdminController  implements HttpHandler {

    private MentorDAO mDAO = new MentorDAO();
    private GroupDAO gDAO = new GroupDAO();

    public AdminController() throws SQLException{
        mDAO = new MentorDAO();
        gDAO = new GroupDAO();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            String method = httpExchange.getRequestMethod();

        try {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            String[] sessionID = cookieStr.split("sessionId=");
            String sessionIDFull = sessionID[1].replace("\"", "");

            if (method.equals("GET") && Sessions.checkSession(sessionIDFull,"Admin")) {
                response = WebTemplate.getSiteContent("templates/admin/nav.twig");
            }
            else{
                Sessions.redirect(httpExchange);

            }
        }catch (NullPointerException e){
            Sessions.redirect(httpExchange);
        }




            //response = WebTemplate.getSiteContent("static/login-page.html");

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
            os.close();
    }
    public void createMentor(String firstName,String lastName,String password, String email) throws SQLException,NoSuchAlgorithmException{
        String passwordHash = HashSystem.getStringFromSHA256(password);
        String klass = "2016";
        Mentor newMentor = new Mentor(firstName, lastName, email, passwordHash, klass);
        mDAO.add(newMentor);
    }

    public void createGroup(String name){
        Group newGroup = new Group(name);
        try {
            gDAO.add(newGroup);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


    public void editMentor() throws SQLException,NoSuchAlgorithmException{

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

    public ArrayList<ArrayList<String>> listAllMentors() throws SQLException{

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        ArrayList<String> record = new ArrayList<>();
        ArrayList<Mentor> mentorList = mDAO.get();
        if(mentorList.size() == 0){
            UI.UI.showMessage("Mentor list is empty!");
        } else {
            for(Mentor mentor: mentorList){
                record.add(mentor.getFirstName());
                record.add(mentor.getLastName());
                record.add(mentor.getEmail());
                data.add(record);
            }
        }
        return data;
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
