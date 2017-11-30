package controller;

import DAO.*;
import com.sun.net.httpserver.HttpHandler;
import controller.Student.Fundraise.FundraisePanel;
import controller.Student.Quest.QuestPanel;
import controller.Student.Store.StorePanel;
import controller.helpers.ParseForm;
import controller.helpers.ResponseGenerator;
import controller.helpers.Sessions;
import models.*;

import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.Map;

public class StudentHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        String response = "";
        String method = httpExchange.getRequestMethod();
        String sessionId = Sessions.getSessionIdFromCookie(httpExchange);

        if (method.equals("GET") && Sessions.checkSession(sessionId, "Student")) {
            Student student = getStudentModel(sessionId);
            response = getResponse(path, student);

        } else if (method.equals("POST") && Sessions.checkSession(sessionId, "Student")){
            Student student = getStudentModel(sessionId);
            Map<String, String> parsedPost = ParseForm.parsePost(httpExchange);
            response = handleParsedPostResponse(path, parsedPost, student);
        } else {
            Sessions.redirect(httpExchange);
        }

        if(!Sessions.checkSession(sessionId, "Student")){
            Sessions.redirect(httpExchange);

        }
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getHandleResponse(boolean handleStatus) {
        if (handleStatus) {
            return WebTemplate.getSiteContent("templates/success-student.twig");
        } else {
            return WebTemplate.getSiteContent("templates/error-sql-student.twig");
        }
    }

    public String getResponse(String path, Student student) {
        FundraisePanel fundraise = new FundraisePanel();
        StorePanel store = new StorePanel();
        QuestPanel quest = new QuestPanel();

        String response = "";
        if (path.equals("/student")) {
            response = ResponseGenerator.generateModelResponse(student, "user", "templates/student/profile.twig");
        } else if (path.equals("/student/create-fundraise")) {
            response = ResponseGenerator.generateModelResponse(student, "user", fundraise.getMagicItemList(), "magicItemList", "templates/student/create-fundraise.twig");
        } else if (path.equals("/student/join-fundraise")) {
            response = ResponseGenerator.generateModelResponse(student, "user", fundraise.getFundraiseList(), "fundraiseList", "templates/student/join-fundraise.twig");
        } else if (path.equals("/student/leave-fundraise")) {
            response = ResponseGenerator.generateModelResponse(student, "user", fundraise.getJoinedFundraiseList(student.getID()), "joinedFundraiseList", "templates/student/leave-fundraise.twig");
        } else if (path.equals("/student/view-fundraise")) {
            response = ResponseGenerator.generateModelResponse(student, "user", fundraise.getFundraiseList(), "fundraiseList", "templates/student/view-fundraise.twig");
        } else if (path.equals("/student/buy-artifact")) {
            response = ResponseGenerator.generateModelResponse(student, "user", store.getBasicItemList(), "basicItemList", "templates/student/buy-artifact.twig");
        } else if (path.equals("/student/view-basic-items")) {
            response = ResponseGenerator.generateModelResponse(student, "user", store.getBasicItemList(), "basicItemList", "templates/student/view-basic-items.twig");
        } else if (path.equals("/student/view-inventory")) {
            response = ResponseGenerator.generateModelResponse(student, "user", store.getStudentInventoryList(student), "inventoryList", "templates/student/view-inventory.twig");
        } else if (path.equals("/student/view-quest")) {
            response = ResponseGenerator.generateModelResponse(student, "user", quest.getQuestList(), "questList", "templates/student/view-quest.twig");
        } else if (path.equals("/student/create-submission")) {
            response = ResponseGenerator.generateModelResponse(student, "user", quest.getQuestList(), "questList", "templates/student/complete-quest.twig");
        } else if (path.equals("/student/view-student-submission")) {
            response = ResponseGenerator.generateModelResponse(student, "user", quest.getSubmissionList(student), "submissionList", "templates/student/view-student-submission.twig");
        }


        return response;
    }

    public String handleParsedPostResponse(String path, Map<String, String> parsedForm, Student student) {
        FundraisePanel fundraise = new FundraisePanel();
        StorePanel store = new StorePanel();
        QuestPanel quest = new QuestPanel();
        String response = "";
        if(parsedForm.containsKey("logout")){
            logout(student);
        }

        else if (path.equals("/student/create-fundraise")) {
            response = getHandleResponse(fundraise.createFundraise(parsedForm));
        } else if (path.equals("/student/join-fundraise")) {

            response = getHandleResponse(fundraise.joinFundraise(parsedForm, student));
        } else if (path.equals("/student/leave-fundraise")) {
            response = getHandleResponse(fundraise.leaveFundraise(parsedForm, student));
        } else if (path.equals("/student/buy-artifact")) {
            response = getHandleResponse(store.buyArtifact(parsedForm, student));
        } else if (path.equals("/student/create-submission")) {
            response = getHandleResponse(quest.createSubmission(parsedForm, student));
        }
        return response;
    }


    private Student getStudentModel(String session) {
        Integer id = Integer.valueOf(Sessions.getIdBySession(session));
        Student student = null;
        try {
            StudentDAO studentDAO = new StudentDAO();
            student = studentDAO.getStudentById(id);
        } catch (SQLException e) {
            return student;
        }
        return student;
    }

    public void logout(Student student){
        try {
            ConnectDB connectDB = ConnectDB.getInstance();
            String sql = String.format("DELETE FROM sessions WHERE user_id like '%s'",student.getID());
             connectDB.addRecord(sql);
        } catch (Exception e) {
        }
    }
}
