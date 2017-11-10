package controller;

import DAO.StudentDAO;
import DAO.WebTemplateDao;
import UI.UI;
import DAO.ConnectDB;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.helpers.HashSystem;
import controller.helpers.ParseForm;
import models.Student;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(),
                    "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String,String> inputs = ParseForm.parseFormData(formData);
            String login = inputs.get("login");
            String password = inputs.get("password");
            String user = setUp(login,password);
            if(user.equals("Admin")){
                httpExchange.getResponseHeaders().set("Location", "/admin");
                httpExchange.sendResponseHeaders(302, -1);
            } else if (user.equals("Mentor")) {
                httpExchange.getResponseHeaders().set("Location", "/mentor");
                httpExchange.sendResponseHeaders(302, -1);

            } else if (user.equals("Student")) {
                httpExchange.getResponseHeaders().set("Location", "/student");
                httpExchange.sendResponseHeaders(302, -1);

            }
        }
    }

    public String setUp(String login,String password){
        String accountType = "";
        try{

            accountType = loginToSystem(login,password);
        } catch (SQLException e){

        }catch (NoSuchAlgorithmException e){

        }
        return accountType;
    }

    public String loginToSystem(String login,String passwordGet) throws SQLException,NoSuchAlgorithmException{
        String password = HashSystem.getStringFromSHA256(passwordGet);
        ConnectDB connectDB = DAO.ConnectDB.getInstance();
        StudentDAO studentd = new StudentDAO();
        String sql = String.format("SELECT * FROM users WHERE email like '%s' and password like '%s'",login,password);
        ResultSet result = connectDB.getResult(sql);

        if (result.next()) {

            if (result.getString("role").equals("student")) {
//                ResultSet studentResult = connectDB.getResult(String.format("SELECT users.id, first_name, last_name, email, password, role, klass, money, experience, level from users join wallets on users.id = wallets.id WHERE email like '%s' and password like '%s'",login,password));
//                studentResult.next();
//                Student student = studentd.createStudent(studentResult);
//                StudentController studentController = new StudentController(student);
//                System.out.println(student.getID());
                return "Student";
            }

            if (result.getString("role").equals("admin")) {
                return "Admin";
            }

            if (result.getString("role").equals("mentor")) {
//                MentorController mentorController = new MentorController();
//                mentorController.startController();
                return "Mentor";
            }



        } else {
            System.out.println("User doesn't exist");
        }

    return "dupa";
    }
}
