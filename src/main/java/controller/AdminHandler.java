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
import models.Group;
import models.Mentor;
import models.Admin;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class AdminHandler  implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        String response = "";
        String method = httpExchange.getRequestMethod();
        String sessionId = getSessionIdFromCookie(httpExchange);
        Admin admin = getAdminModel(sessionId);

        if (method.equals("GET") && Sessions.checkSession(sessionId,"Admin")) {
            response = getResponse(path,admin);
        }
        else if (method.equals("POST")){
            Map<String,String> parsedPost = parsePost(httpExchange);
            //boolean handleStatus = handleParsedPost(httpExchange, path, parsedPost);
            //response = getHandleResponse(handleStatus);
            response = handleParsedPostResponse(path,parsedPost);
        }
        else{
            Sessions.redirect(httpExchange);
        }
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String getHandleResponse(boolean handleStatus) {
        if(handleStatus){
            return WebTemplate.getSiteContent("templates/success.twig");
        }
        else{
            return WebTemplate.getSiteContent("templates/error.twig");
        }
    }


    public String getResponse(String path,Admin admin) {
        String response = "";
        if(path.equals("/admin")){
            response = ResponseGenerator.generateModelResponse(admin,"user","templates/admin/nav.twig");
        }
        else if (path.equals("/admin/create-group")){
            response = ResponseGenerator.generateModelResponse(getKlasses(), "klasses", "templates/admin/create-group.twig");
        }
        else if (path.equals("/admin/edit-group")){
            response = ResponseGenerator.generateModelResponse(getKlasses(), "klasses", "templates/admin/edit-group.twig");
        }
        else if (path.equals("/admin/create-mentor")){
            response = ResponseGenerator.generateModelResponse("templates/admin/create-mentor.twig");
        }
        else if (path.equals("/admin/mentor-list")) {
            response = getMentorListRespone();
        }
        else if (path.equals("/admin/create-level")){
            response = ResponseGenerator.generateModelResponse("templates/admin/create-level.twig");
        }
        else if (path.equals("/admin/edit-level")){
            response = ResponseGenerator.generateModelResponse("templates/admin/edit-level.twig");
        }

        return response;
    }

    public String handleParsedPostResponse(String path, Map<String, String> parsedForm){
        String response = "";
        if(path.equals("/admin/create-mentor")){
            response = getHandleResponse(createMentor(parsedForm));
        }
        else if(path.equals("/admin/create-group")){
            response = getHandleResponse(createGroup(parsedForm));
        }
        else if(path.equals("/admin/mentor-list") && !parsedForm.containsKey("first-name")){
            response = getEditResponse(parsedForm.get("id"));
        }
        else if(path.equals("/admin/mentor-list") && parsedForm.containsKey("first-name")){
            response = getHandleResponse(editMentor(parsedForm));
        }


        return response;
    }

    private String getEditResponse(String id) {
        String response = "";
        try {
            MentorDAO mentorDAO = new MentorDAO();
            Mentor mentor = mentorDAO.getMentorById(id);
            response = ResponseGenerator.generateModelResponse(mentor,"mentor","templates/admin/edit-mentor.twig");
        } catch (Exception e) {
            System.out.println("akkk");
            return ResponseGenerator.generateModelResponse("templates/error.twig");
        }

        return response;
    }

    private boolean editMentor(Map<String, String> parsedForm)       {
        MentorDAO mentorDAO = null;
        try {
        mentorDAO = new MentorDAO();
        Integer id = Integer.parseInt(parsedForm.get("id"));
        String firstName = parsedForm.get("first-name");
        String lastName = parsedForm.get("last-name");
        String email = parsedForm.get("email");
        String password = parsedForm.get("password");
        String passwordHash = HashSystem.getStringFromSHA256(password);
        String klass = parsedForm.get("class");
        Mentor mentor = new Mentor(id,firstName, lastName, email, passwordHash, klass);
        mentorDAO.set(mentor);
    } catch (SQLException e) {
        return false;
    } catch (NoSuchAlgorithmException e) {
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

    public Map<String, String> parsePost(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader;
        Map<String,String> inputs = null;
        try {
            inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(),
                    "utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String formData = br.readLine();
            System.out.println(formData);
            inputs = ParseForm.parseFormData(formData);
        } catch (UnsupportedEncodingException e) {
            return inputs;
        }
        return inputs;
    }

    public String getSessionIdFromCookie(HttpExchange httpExchange) throws IOException {
        String sessionIDFull = "";
        try {
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            String[] sessionID = cookieStr.split("sessionId=");
            sessionIDFull= sessionID[1].replace("\"", "");
        }catch (NullPointerException e){
            return sessionIDFull;
        }
        return sessionIDFull;
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

    public String getMentorListRespone(){
        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/view-mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        ArrayList<Mentor> mentorList= getMentorList();
        model.with("mentorList", mentorList);
        response = template.render(model);
        return response;
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
}
