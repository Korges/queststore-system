package controller;

import UI.UI;
import DAO.connectDB;
import models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    public void loginToSystem() throws SQLException{

        String login = UI.getLogin();
        String password = UI.getPassword();
        connectDB connectDB = DAO.connectDB.getInstance();
        String sql = String.format("SELECT * FROM users WHERE email like '%s' and password like '%s'",login,password);
        ResultSet result = connectDB.getResult(sql);

        if (result.next()) {

            if (result.getString("role").equals("student")) {
            //Student student = new Student(result.getString("name",));
            //StudentController studentController = new StudentController(student);
//            studentController.startController();
            }

            if (result.getString("role").equals("admin")) {
                //connectDB.close();
                AdminController adminController = new AdminController();
                adminController.startController();
            }

            if (result.getString("role").equals("mentor")) {
                MentorController mentorController = new MentorController();
                mentorController.startController();
            }



        } else {
            System.out.println("User doesn't exist");
        }

    }
}
