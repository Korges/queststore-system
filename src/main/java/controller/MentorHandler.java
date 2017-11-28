package controller;

import DAO.FundraiseDAO;
import DAO.StudentDAO;
import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.helpers.HashSystem;
import controller.helpers.ParseForm;
import controller.helpers.ResponseGenerator;
import controller.helpers.Sessions;
import models.Fundraise;
import models.Student;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class MentorHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        String response = "";
        String method = httpExchange.getRequestMethod();
        String sessionId = Sessions.getSessionIdFromCookie(httpExchange);

        if(method.equals("GET") && Sessions.checkSession(sessionId,"Mentor")){
            response = getResponse(path);
        } else if (method.equals("POST")){
            Map<String,String> parsedPost = ParseForm.parsePost(httpExchange);
            response = handleParsedPostResponse(path,parsedPost);
        } else {
            Sessions.redirect(httpExchange);
        }
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getResponse(String path) {
        String response = "";
        if(path.equals("/mentor")){
            response = ResponseGenerator.generateModelResponse("templates/mentor/nav.twig");
        }
        else if(path.equals("/mentor/create-student")){
            response = ResponseGenerator.generateModelResponse("templates/mentor/create-student.twig");
        }
        else if (path.equals("/mentor/view-student")) {
            response = ResponseGenerator.generateModelResponse(getStudentList(),"students","templates/mentor/view-student.twig");
        }
        else if(path.equals("/mentor/create-artifact")){
            response = "Brakuje twiga do zrobienia"; //todo
        }

        else if(path.equals("/mentor/fundraise-list")){
            response = ResponseGenerator.generateModelResponse(getFundraiseList(),"fundraises","templates/mentor/view-all-fundraise.twig");
        }

        else if(path.equals("/mentor/delete-fundraise")){
            response = ResponseGenerator.generateModelResponse(getFundraiseList(),"fundraises","templates/mentor/delete-fundraise.twig");
        }
        else if(path.equals("/mentor/finalize-fundraise")){
            response = ResponseGenerator.generateModelResponse(getFundraiseList(),"fundraises","templates/mentor/finalize-fundraise.twig");
        }

        return response;
    }

    private String handleParsedPostResponse(String path, Map<String, String> parsedForm) {
        String response = "";
        if(path.equals("/mentor/create-student")){
            response = getHandleResponse(createStudent(parsedForm));
        }
        else if(path.equals("/mentor/view-student") && !parsedForm.containsKey("first-name")){
            response = getEditStudentResponse(parsedForm.get("id"));
        }
        else if(path.equals("/mentor/view-student") && parsedForm.containsKey("first-name")){
            response = getHandleResponse(submitEditStudent(parsedForm));
        }
        else if(path.equals("/mentor/delete-fundraise")){
            response = getHandleResponse(deleteFundraise(Integer.parseInt(parsedForm.get("id"))));
        }
        return response;
    }

    private boolean submitEditStudent(Map<String, String> parsedForm) {
        try{
            Integer id = Integer.parseInt(parsedForm.get("id"));
            StudentDAO studentDAO = new StudentDAO();
            String firstName = parsedForm.get("first-name");
            String lastName = parsedForm.get("last-name");
            String email = parsedForm.get("email");
            String password = parsedForm.get("password");
            String passwordHash = HashSystem.getStringFromSHA256(password);
            String klass = parsedForm.get("class");
            Student student = new Student(id,firstName,lastName,email,passwordHash,klass);
            studentDAO.set(student);
        } catch (SQLException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return true;
    }

    private String getEditStudentResponse(String id) {
        String response = "";
        try{
            StudentDAO studentDAO = new StudentDAO();
            Student student = studentDAO.getStudentById(Integer.parseInt(id));
            response = ResponseGenerator.generateModelResponse(student,"student","templates/mentor/edit-student.twig");

        } catch (SQLException e) {
            return ResponseGenerator.generateModelResponse("templates/error-sql.twig");

        }
        return response;
    }

    private boolean createStudent(Map<String, String> parsedForm) {
        try {
            StudentDAO studentDAO = new StudentDAO();
            String firstName = parsedForm.get("first-name");
            String lastName = parsedForm.get("last-name");
            String email = parsedForm.get("email");
            String password = parsedForm.get("password");
            String passwordHash = HashSystem.getStringFromSHA256(password);
            String klass = parsedForm.get("class");
            Student student = new Student(firstName,lastName,email,passwordHash,klass);
            studentDAO.add(student);

        } catch (SQLException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return true;
    }

    private String getHandleResponse(boolean handleStatus) {
        if(handleStatus){
            return WebTemplate.getSiteContent("templates/success.twig");
        }
        else{
            return WebTemplate.getSiteContent("templates/error-sql.twig");
        }
    }

    public ArrayList<Student> getStudentList(){
        StudentDAO studentDAO = null;
        ArrayList<Student> studentList = null;
        try {
            studentDAO = new StudentDAO();
            studentList = studentDAO.get();
        } catch (SQLException e) {
            return studentList;
        }
        return studentList;
    }

    public ArrayList<Fundraise> getFundraiseList(){
        ArrayList<Fundraise> fundraiseList = null;
        FundraiseDAO fundraiseDAO = null;
        try {
            fundraiseDAO = new FundraiseDAO();
            fundraiseList =  fundraiseDAO.getFundraiseList();
        } catch (SQLException e) {
            return fundraiseList;
        }
        return fundraiseList;
    }

    private boolean deleteFundraise(Integer fundraiseID){
        FundraiseDAO fundraiseDAO = null;
        try {
            fundraiseDAO = new FundraiseDAO();
            fundraiseDAO.deleteFundraise(fundraiseID);
            fundraiseDAO.deleteFundraiseStudents(fundraiseID);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
