package controller;

import DAO.StudentDAO;
import DAO.WebTemplateDao;
import UI.UI;
import DAO.ConnectDB;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.helpers.HashSystem;
import controller.helpers.ParseForm;
import models.Student;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainController implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        WebTemplateDao webTemplateDao = new WebTemplateDao();
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = webTemplateDao.getSiteTemplate("static/login-page.html");
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if (method.equals("POST")) {
            String user = null;
            try {
                user = getUserType(httpExchange);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if(user.equals("Admin")){
                httpExchange.getResponseHeaders().set("Location", "/admin");
                httpExchange.sendResponseHeaders(302, -1);
            } else if (user.equals("Mentor")) {
                httpExchange.getResponseHeaders().set("Location", "/mentor");
                httpExchange.sendResponseHeaders(302, -1);
            }
                else if (user.equals("Student")) {
                httpExchange.getResponseHeaders().set("Location", "/student");
                httpExchange.sendResponseHeaders(302, -1);
            }

            else{
                response = webTemplateDao.getSiteTemplate("static/login-page2.html");
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
          
        }
    }

    public String loginToSystem(String login,String passwordGet) throws SQLException,NoSuchAlgorithmException{

        String password = HashSystem.getStringFromSHA256(passwordGet);
        ConnectDB connectDB = DAO.ConnectDB.getInstance();
        String sql = String.format("SELECT * FROM users WHERE email like '%s' and password like '%s'",login,password);
        ResultSet result = connectDB.getResult(sql);

        if (result.next()) {
            if (result.getString("role").equals("student")) {
                return "Student";
            }

            if (result.getString("role").equals("admin")) {
                return "Admin";
            }

            if (result.getString("role").equals("mentor")) {
                return "Mentor";
            }
        }

    return "backToLogin";
    }

    private String generateSessionID(){
        UUID SessionID = UUID.randomUUID();
        return String.valueOf(SessionID);
    }

    private String getUserType(HttpExchange httpExchange) throws IOException, SQLException, NoSuchAlgorithmException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),
                "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        Map<String,String> inputs = ParseForm.parseFormData(formData);
        String login = inputs.get("login");
        String password = inputs.get("password");

        String userType = loginToSystem(login,password);
        createSessions(httpExchange,login,password,userType);
        return userType;
    }

    private void createSessions(HttpExchange httpExchange, String login, String password, String user) throws SQLException, NoSuchAlgorithmException {
        String sessionID = generateSessionID();
        HttpCookie cookie = new HttpCookie("sessionId", sessionID);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        String getId = String.format("SELECT id FROM users WHERE email like '%s' and password like '%s'",login, HashSystem.getStringFromSHA256(password));
        String sql = String.format("INSERT INTO sessions values('%s','%s',(%s))",sessionID,user,getId);
        ConnectDB connectDB = ConnectDB.getInstance();
        connectDB.addRecord(sql);
    }
}

