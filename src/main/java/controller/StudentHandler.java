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
        String sessionId = getSessionIdFromCookie(httpExchange);
        Student student = getStudentModel(sessionId);

        if (method.equals("GET") && Sessions.checkSession(sessionId, "Student")) {

                response = getResponse(path, student);

        } else if (method.equals("POST")) {
            Map<String, String> parsedPost = parsePost(httpExchange);
            response = handleParsedPostResponse(path, parsedPost, student);
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
            return WebTemplate.getSiteContent("templates/error-sql.twig");
        }
    }

    public String getResponse(String path,Student student) {
        FundraisePanel fundraise = new FundraisePanel();
        StorePanel store = new StorePanel();
        QuestPanel quest = new QuestPanel();

        String response = "";
        if(path.equals("/student")){
            response = ResponseGenerator.generateModelResponse(student,"user","templates/student/nav.twig");
        }
        else if (path.equals("/student/create-fundraise")) {
            response = ResponseGenerator.generateModelResponse(fundraise.getMagicItemList(), "magicItemList", "templates/student/create-fundraise.twig");
        }
        else if (path.equals("/student/join-fundraise")) {

            response = ResponseGenerator.generateModelResponse(fundraise.getFundraiseList(), "fundraiseList", "templates/student/join-fundraise.twig");
        }
        else if (path.equals("/student/leave-fundraise")) {
            response = ResponseGenerator.generateModelResponse(fundraise.getJoinedFundraiseList(student.getID()), "joinedFundraiseList", "templates/student/leave-fundraise.twig");
        }
        else if (path.equals("/student/view-fundraise")) {
            response = ResponseGenerator.generateModelResponse(fundraise.getFundraiseList(), "fundraiseList", "templates/student/view-fundraise.twig");
        }
        else if( path.equals("/student/buy-artifact")) {
            response = ResponseGenerator.generateModelResponse(store.getBasicItemList(), "basicItemList", "templates/student/buy-artifact.twig");
        }
        else if(path.equals("/student/view-basic-items")) {
            response = ResponseGenerator.generateModelResponse(store.getBasicItemList(), "basicItemList", "templates/student/view-basic-items.twig");
        }
        else if(path.equals("/student/view-inventory")) {
            response = ResponseGenerator.generateModelResponse(store.getStudentInventoryList(student), "inventoryList", "templates/student/view-inventory.twig");
        }
        else if(path.equals("/student/view-quest")) {
            response = ResponseGenerator.generateModelResponse(quest.getQuestList(), "questList", "templates/student/view-quest.twig");
        }



        return response;
    }

    public String handleParsedPostResponse(String path, Map<String, String> parsedForm, Student student) {
        FundraisePanel fundraise = new FundraisePanel();
        StorePanel store = new StorePanel();
        String response = "";

        if (path.equals("/student/create-fundraise")) {
            response = getHandleResponse(fundraise.createFundraise(parsedForm));
        }
        else if (path.equals("/student/join-fundraise")) {

            response = getHandleResponse(fundraise.joinFundraise(parsedForm, student));
        }
        else if (path.equals("/student/leave-fundraise")) {
            response = getHandleResponse(fundraise.leaveFundraise(parsedForm, student));

        } else if (path.equals("/student/buy-artifact")) {
            response = getHandleResponse(store.buyArtifact(parsedForm, student));
        }

            return response;
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

}
