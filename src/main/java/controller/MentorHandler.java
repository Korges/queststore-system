package controller;

import DAO.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.Mentor.Fundraise.FundraiseHelper;
import controller.Mentor.Quest.QuestPanel;
import controller.Mentor.Submission.SubmissionPanel;
import controller.helpers.*;
import models.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        QuestPanel quest = new QuestPanel();
        SubmissionPanel submission = new SubmissionPanel();
        if(path.equals("/mentor")){
            response = ResponseGenerator.generateModelResponse("templates/mentor/nav.twig");
        }
        else if(path.equals("/mentor/create-artifact")){
            response = ResponseGenerator.generateModelResponse("templates/mentor/create-artifact.twig");
        }
        else if(path.equals("/mentor/create-student")){
            response = ResponseGenerator.generateModelResponse("templates/mentor/create-student.twig");
        }
        else if(path.equals("/mentor/create-quest")){
            response = ResponseGenerator.generateModelResponse("templates/mentor/create-quest.twig");
        }
        else if (path.equals("/mentor/view-artifact")) {
            response = ResponseGenerator.generateModelResponse(getArtifacts(),"artifacts","templates/mentor/view-artifact.twig");
        }
        else if (path.equals("/mentor/view-student")) {
            response = ResponseGenerator.generateModelResponse(getStudentList(),"students","templates/mentor/view-student.twig");
        }
        else if (path.equals("/mentor/view-quest")) {
            response = ResponseGenerator.generateModelResponse(quest.getQuests(),"quests","templates/mentor/view-quest.twig");
        }
        else if(path.equals("/mentor/fundraise-list")){
            response = ResponseGenerator.generateModelResponse(getFundraiseList(),"fundraises","templates/mentor/view-all-fundraise.twig");
        }
        else if(path.equals("/mentor/delete-fundraise")){
            response = ResponseGenerator.generateModelResponse(getFundraiseList(),"fundraises","templates/mentor/delete-fundraise.twig");
        }
        else if(path.equals("/mentor/delete-quest")) {
            response = ResponseGenerator.generateModelResponse(quest.getFullQuestList(),"questList","templates/mentor/delete-quest.twig");
        }
        else if(path.equals("/mentor/finalize-fundraise")){
            response = ResponseGenerator.generateModelResponse(getFundraiseList(),"fundraises","templates/mentor/finalize-fundraise.twig");
        }
        else if(path.equals("/mentor/view-submission")) {
            response = ResponseGenerator.generateModelResponse(submission.getUnfinishedSubmissionList(), "unfinishedSubmissionList", "templates/mentor/view-submission.twig");
        }

        return response;
    }



    private List<Artifact> getArtifacts() {
        List<Artifact> artifacts = null;
        try {
            ArtifactDAO artifactDAO = new ArtifactDAO();
            artifacts = artifactDAO.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artifacts;

    }

    private String handleParsedPostResponse(String path, Map<String, String> parsedForm) {
        String response = "sss";
        FundraiseHelper fundraiseHelper = new FundraiseHelper();
        QuestPanel quest = new QuestPanel();
        SubmissionPanel submission = new SubmissionPanel();
        if(path.equals("/mentor/create-student")){
            response = getHandleResponse(createStudent(parsedForm));
        }
        else if(path.equals("/mentor/create-artifact")){
            response = getHandleResponse(createArtifact(parsedForm));
        }
        else if(path.equals("/mentor/create-quest")){
            response = getHandleResponse(quest.addNewQuestToDatabase(parsedForm));
        }
        else if(path.equals("/mentor/view-student") && !parsedForm.containsKey("first-name")){
            response = getEditStudentResponse(parsedForm.get("id"));
        }
        else if(path.equals("/mentor/view-student") && parsedForm.containsKey("first-name")){
            response = getHandleResponse(submitEditStudent(parsedForm));
        }
        else if(path.equals("/mentor/view-artifact") && !parsedForm.containsKey("name")){
            response = getEditArtifactResponse(parsedForm.get("id"));
        }
        else if(path.equals("/mentor/view-artifact") && parsedForm.containsKey("name")){
            response = getHandleResponse(submitEditArtifact(parsedForm));
        }
        else if(path.equals("/mentor/view-quest") && !parsedForm.containsKey("value")){
            response = getEditQuestResponse(parsedForm.get("id"));
        }
        else if(path.equals("/mentor/view-quest") && parsedForm.containsKey("value")){
            response = getHandleResponse(submitEditQuest(parsedForm));
        }
        else if(path.equals("/mentor/delete-fundraise")){
            response = getHandleResponse(deleteFundraise(Integer.parseInt(parsedForm.get("id"))));
        }
        else if(path.equals("/mentor/finalize-fundraise")){
            response = getHandleResponse(fundraiseHelper.finalizeFundraise(parsedForm));
        }
        else if(path.equals("/mentor/delete-quest")) {
            response = getHandleResponse(quest.deleteQuest(parsedForm));
        }
        else if(path.equals("/mentor/view-submission")) {
            response = getHandleResponse(submission.completeSubmission(parsedForm));
        }
        return response;
    }


    private boolean createArtifact(Map<String, String> parsedForm) {
        //todo
        return true;
    }

    private boolean submitEditQuest(Map<String, String> parsedForm) {
        try{
            QuestDAO questDAO = new QuestDAO();
            Integer id = Integer.parseInt(parsedForm.get("id"));
            String name = parsedForm.get("name");
            String description = parsedForm.get("description");
            Integer value = Integer.parseInt(parsedForm.get("value"));
            Integer experience = Integer.parseInt(parsedForm.get("experience"));
            String category = parsedForm.get("category");
            Quest quest = new Quest(id, name, description, value, experience, category) {
            };
            questDAO.set(quest);
        } catch (SQLException e) {
            return false;
        }
        return true;

    }

    private String getEditQuestResponse(String id) {
        String response;
        try{
            QuestDAO questDAO = new QuestDAO();
            Quest quest = questDAO.getQuestById(Integer.parseInt(id));
            response = ResponseGenerator.generateModelResponse(quest,"quest","templates/mentor/edit-quest.twig");

        } catch (SQLException e) {
            return ResponseGenerator.generateModelResponse("templates/error-sql.twig");

        }
        return response;
    }

    private boolean submitEditArtifact(Map<String, String> parsedForm) {
        try{
            ArtifactDAO artifactDAO = new ArtifactDAO();
            Integer id = Integer.parseInt(parsedForm.get("id"));
            String name = parsedForm.get("name");
            String description = parsedForm.get("description");
            Integer price = Integer.parseInt(parsedForm.get("price"));
            Boolean isMagic = parseArtifactType(parsedForm.get("isMagic"));
            Artifact artifact = new MagicItem(id, name, description, price, isMagic) {
            };
            artifactDAO.set(artifact);
        } catch (SQLException e) {
            return false;
        }
        return true;

    }

    private Boolean parseArtifactType(String type) { // todo zrobic zeby w twigu zwracal w polu magic "yes" albo "no"
        if (type.equals("yes")) {
            return true;
        }
            else{
                return false;
        }
    }

    private String getEditArtifactResponse(String id) {
        String response;
        try{
            ArtifactDAO artifactDAO = new ArtifactDAO();
            Artifact artifact = artifactDAO.getArtifactById(Integer.parseInt(id));
            response = ResponseGenerator.generateModelResponse(artifact,"artifact","templates/mentor/edit-artifact.twig");

        } catch (SQLException e) {
            return ResponseGenerator.generateModelResponse("templates/error-sql.twig");

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
            String klass = parsedForm.get("class");
            Student student = new Student(id,firstName,lastName,email,password,klass);
            studentDAO.set(student);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    private String getEditStudentResponse(String id) {
        String response;
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
            return WebTemplate.getSiteContent("templates/mentor/success.twig");
        }
        else{
            return WebTemplate.getSiteContent("templates/mentor/error-sql.twig");
        }
    }

    private ArrayList<Student> getStudentList(){
        StudentDAO studentDAO;
        ArrayList<Student> studentList = null;
        try {
            studentDAO = new StudentDAO();
            studentList = studentDAO.get();
        } catch (SQLException e) {
            return studentList;
        }
        return studentList;
    }

    private ArrayList<Fundraise> getFundraiseList(){
        ArrayList<Fundraise> fundraiseList = null;
        FundraiseDAO fundraiseDAO;
        try {
            fundraiseDAO = new FundraiseDAO();
            fundraiseList =  fundraiseDAO.getFundraiseList();
        } catch (SQLException e) {
            return fundraiseList;
        }
        return fundraiseList;
    }

    private boolean deleteFundraise(Integer fundraiseID){
        FundraiseDAO fundraiseDAO;
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
