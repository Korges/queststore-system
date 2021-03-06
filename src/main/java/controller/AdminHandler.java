package controller;

import java.io.*;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import DAO.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.helpers.HashSystem;
import controller.helpers.ParseForm;
import controller.helpers.ResponseGenerator;
import controller.helpers.Sessions;
import models.*;

public class AdminHandler  implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        String response = "";
        String method = httpExchange.getRequestMethod();
        String sessionId = Sessions.getSessionIdFromCookie(httpExchange);
        Admin admin = getAdminModel(sessionId);

        if (method.equals("GET") && Sessions.checkSession(sessionId,"Admin")) {
            response = getResponse(path,admin);
        }
        else if (method.equals("POST")){
            Map<String,String> parsedPost = ParseForm.parsePost(httpExchange);
            response = handleParsedPostResponse(path,parsedPost,admin);
        }
        else{
            Sessions.redirect(httpExchange);
        }
        if(!Sessions.checkSession(sessionId, "Admin")){
            Sessions.redirect(httpExchange);
        }

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String getHandleResponse(Admin admin,boolean handleStatus) {
        if(handleStatus){
            return ResponseGenerator.generateModelResponse(admin,"user","templates/success.twig");
        }
        else{
            return ResponseGenerator.generateModelResponse(admin,"user","templates/error-sql.twig");
        }
    }


    public String getResponse(String path,Admin admin) {
        String response = "";
        if(path.equals("/admin")){
            response = ResponseGenerator.generateModelResponse(admin,"user","templates/admin/profile.twig");
        }
        else if (path.equals("/admin/create-group")){
            response = ResponseGenerator.generateModelResponse(admin,"user", getKlasses(), "groups", "templates/admin/create-group.twig");
        }
        else if (path.equals("/admin/view-group")){
            response = ResponseGenerator.generateModelResponse(admin,"user", getKlasses(), "groups", "templates/admin/view-group.twig");
        }
        else if (path.equals("/admin/create-mentor")){
            response = ResponseGenerator.generateModelResponse(admin,"user", getKlasses(), "groups", "templates/admin/create-mentor.twig");
        }
        else if (path.equals("/admin/view-mentor")) {
            response = ResponseGenerator.generateModelResponse(admin,"user", getMentorList(),"mentors","templates/admin/view-mentor.twig");
        }
        else if (path.equals("/admin/create-level")){
            response = ResponseGenerator.generateModelResponse(admin,"user", "templates/admin/create-level.twig");
        }
         else if (path.equals("/admin/view-level")) {
            response = ResponseGenerator.generateModelResponse(admin,"user", getLevels(), "levels","templates/admin/view-level.twig");
        }


        return response;
    }

    public String handleParsedPostResponse(String path, Map<String, String> parsedForm, Admin admin){
        String response = "";
        if(parsedForm.containsKey("logout")){
            logout(admin);
        }

        else if(path.equals("/admin/create-mentor")){
            response = getHandleResponse(admin, createMentor(parsedForm));
        }
        else if(path.equals("/admin/view-group") && !parsedForm.containsKey("group-name")){
            response = getEditGroupResponse(parsedForm.get("id"));
        }
        else if(path.equals("/admin/view-group") && parsedForm.containsKey("group-name")){
            response = getHandleResponse(admin, submitEditGroup(parsedForm));
        }
        else if(path.equals("/admin/view-mentor") && !parsedForm.containsKey("first-name")){
            response = getEditMentorResponse(parsedForm.get("id"));
        }
        else if(path.equals("/admin/view-mentor") && parsedForm.containsKey("first-name")){
            response = getHandleResponse(admin, submitEditMentor(parsedForm));
        }
        else if(path.equals("/admin/create-group")){
            response = getHandleResponse(admin, createGroup(parsedForm));
        }
        else if(path.equals("/admin/create-level")){
            response = getHandleResponse(admin, createLevel(parsedForm));
        }
        else if(path.equals("/admin/view-level") && !parsedForm.containsKey("level")){
            response = getEditLevelResponse(parsedForm.get("id"));
        }
        else if(path.equals("/admin/view-level") && parsedForm.containsKey("level")){
            response = getHandleResponse(admin, submitEditLevel(parsedForm));
        }
        return response;
    }

    private boolean createLevel(Map<String, String> parsedForm) {
        boolean status = false;
        Integer level = Integer.parseInt(parsedForm.get("level"));
        Integer experience = Integer.parseInt(parsedForm.get("experience"));

        LevelExperienceDAO levelDAO = null;
        try {
            LevelExperience levelExperience = new LevelExperience(experience, level);
            levelDAO = new LevelExperienceDAO();
            levelDAO.add(levelExperience);
            status = true;
        } catch (SQLException e) {
            return status;
        }
        return status;

    }

    private String getEditMentorResponse(String id) {
        String response = "";
        try {
            MentorDAO mentorDAO = new MentorDAO();
            Mentor mentor = mentorDAO.getMentorById(id);
            response = ResponseGenerator.generateModelResponse(mentor,"mentor","templates/admin/edit-mentor.twig");
        } catch (Exception e) {
            return ResponseGenerator.generateModelResponse("templates/error-sql.twig");
        }

        return response;
    }

    private String getEditGroupResponse(String id) {
        String response = "";
        try {
            GroupDAO groupDAO = new GroupDAO();
            Group group = groupDAO.getGroupById(id);
            response = ResponseGenerator.generateModelResponse(group,"group","templates/admin/edit-group.twig");
        } catch (Exception e) {
            return ResponseGenerator.generateModelResponse("templates/error-sql.twig");
        }

        return response;
    }
    private String getEditLevelResponse(String id) {
        String response = "";
        try {
            LevelExperienceDAO lvlDAO = new LevelExperienceDAO();
            LevelExperience lvlexp = lvlDAO.getLevelById(id);
            response = ResponseGenerator.generateModelResponse(lvlexp,"level","templates/admin/edit-level.twig");
        } catch (Exception e) {
            return ResponseGenerator.generateModelResponse("templates/error-sql.twig");
        }

        return response;
    }
    private boolean submitEditMentor(Map<String, String> parsedForm)       {
        MentorDAO mentorDAO = null;
        try {
        mentorDAO = new MentorDAO();
        Integer id = Integer.parseInt(parsedForm.get("id"));
        String firstName = parsedForm.get("first-name");
        String lastName = parsedForm.get("last-name");
        String email = parsedForm.get("email");
        String password = parsedForm.get("password");
        String klass = parsedForm.get("class");
        Mentor mentor = new Mentor(id,firstName, lastName, email, password, klass);
        mentorDAO.set(mentor);
    } catch (SQLException e) {
        return false;
    }
        return true;
}
    private boolean submitEditGroup(Map<String, String> parsedForm)       {
        GroupDAO groupDAO = null;
        try {
            groupDAO = new GroupDAO();
            Integer id = Integer.parseInt(parsedForm.get("id"));
            String name = parsedForm.get("group-name");
            Group group = new Group(id,name);
            groupDAO.set(group);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    private boolean submitEditLevel(Map<String, String> parsedForm)       {
        LevelExperienceDAO lvlDAO = null;
        try {
            lvlDAO = new LevelExperienceDAO();
            Integer id = Integer.parseInt(parsedForm.get("id"));
            Integer exp = Integer.parseInt(parsedForm.get("experience"));
            Integer level = Integer.parseInt(parsedForm.get("level"));
            LevelExperience levelExperience = new LevelExperience(id, exp, level);
            lvlDAO.set(levelExperience);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    private boolean createGroup(Map<String, String> parsedForm) {
        boolean status = false;
        String groupName = parsedForm.get("group-name");
        GroupDAO groupDAO = null;
        try {
            Group newGroup = new Group(groupName);
            groupDAO = new GroupDAO();
            groupDAO.add(newGroup);
            status = true;
        } catch (SQLException e) {
            return status;
        }
        return status;
    }


    public boolean createMentor(Map<String, String> parsedForm){
        MentorDAO mentorDAO = null;
        try {
            mentorDAO = new MentorDAO();
            String firstName = parsedForm.get("first-name");
            String lastName = parsedForm.get("last-name");
            String email = parsedForm.get("email");
            String password = parsedForm.get("password");
            String passwordHash = HashSystem.getStringFromSHA256(password);
            String klass = parsedForm.get("class");
            Mentor mentor = new Mentor(firstName, lastName, email, passwordHash, klass);
            mentorDAO.add(mentor);
        } catch (SQLException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return true;
    }


    private List<Group> getKlasses(){
        List<Group> klasses = null;
        try {
            GroupDAO dao = new GroupDAO();
        klasses = dao.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return klasses;
    }

    private List<LevelExperience> getLevels(){
        List<LevelExperience> levels = null;
        try{
            LevelExperienceDAO dao = new LevelExperienceDAO();

            levels = dao.get();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return levels;
    }

    public ArrayList<Mentor> getMentorList(){
        MentorDAO mentorDAO = null;
        ArrayList<Mentor> mentorList = null;
        try {
            mentorDAO = new MentorDAO();
            mentorList = mentorDAO.get();
        } catch (SQLException e) {
            return mentorList;
        }
        return mentorList;
    }

    public Admin getAdminModel(String session){
        String id = Sessions.getIdBySession(session);
        Admin admin = null;
        try {
            AdminDAO adminDAO = new AdminDAO();
            admin = adminDAO.getAdminById(id);
        } catch (SQLException e) {
            return admin;
        }
        return admin;
    }

    public void logout(Admin admin){
        try {
            ConnectDB connectDB = ConnectDB.getInstance();
            String sql = String.format("DELETE FROM sessions WHERE user_id like '%s'",admin.getID());
            connectDB.addRecord(sql);
        } catch (Exception e) {
        }

    }
}
