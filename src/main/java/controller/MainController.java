package controller;

import DAO.StudentDAO;
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
        StudentDAO studentd = new StudentDAO();
        String sql = String.format("SELECT * FROM users WHERE email like '%s' and password like '%s'",login,password);
        ResultSet result = connectDB.getResult(sql);

        if (result.next()) {

            if (result.getString("role").equals("student")) {
                ResultSet studentResult = connectDB.getResult(String.format("SELECT users.id, first_name, last_name, email, password, role, klass, money, experience, level from users join wallets on users.id = wallets.id WHERE email like '%s' and password like '%s'",login,password));
                studentResult.next();
                Student student = studentd.createStudent(studentResult);
                StudentController studentController = new StudentController(student);
                studentController.startController();
            }

            if (result.getString("role").equals("admin")) {
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
