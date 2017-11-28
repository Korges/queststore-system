package controller;

import DAO.StudentDAO;
import DAO.WebTemplate;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.helpers.HashSystem;
import controller.helpers.ParseForm;
import controller.helpers.ResponseGenerator;
import controller.helpers.Sessions;
import models.Student;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
        }
        else if (method.equals("POST")){
            Map<String,String> parsedPost = ParseForm.parsePost(httpExchange);
            response = handleParsedPostResponse(path,parsedPost);
        }

        else {
            Sessions.redirect(httpExchange);
        }


        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String handleParsedPostResponse(String path, Map<String, String> parsedForm) {
        String response = "cyc";
        if(path.equals("/mentor/create-student")){
            response = getHandleResponse(createStudent(parsedForm));
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

    private String getResponse(String path) {
        String response = "";
        if(path.equals("/mentor")){
            response = ResponseGenerator.generateModelResponse("templates/mentor/nav.twig");
        }
        if(path.equals("/mentor/create-student")){
            response = ResponseGenerator.generateModelResponse("templates/mentor/create-student.twig");
        }

        return response;
    }

    private String getHandleResponse(boolean handleStatus) {
        if(handleStatus){
            return WebTemplate.getSiteContent("templates/success.twig");
        }
        else{
            return WebTemplate.getSiteContent("templates/error-sql.twig");
        }
    }
}
