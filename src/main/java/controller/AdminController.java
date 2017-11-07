package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.*;
import UI.AdminUI;
import UI.MentorUI;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Mentor;
import models.Group;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class AdminController  implements HttpHandler {

    private MentorDAO mDAO = new MentorDAO();
    private GroupDAO gDAO = new GroupDAO();

    public AdminController() throws SQLException{
        mDAO = new MentorDAO();
        gDAO = new GroupDAO();
    }

    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            WebTemplateDao webTemplateDao = new WebTemplateDao();
            String response = "";
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {
                JtwigTemplate template = JtwigTemplate.classpathTemplate("static/admin-page.html");
                ArrayList<ArrayList<String>> data = listAllMentors();
                JtwigModel model = JtwigModel.newModel();

                model.with("data", data);
                response = template.render(model);
            }

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (SQLException e ){

        }
    }
    public void createMentor() throws SQLException,NoSuchAlgorithmException{

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
                record = new ArrayList<>();
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
