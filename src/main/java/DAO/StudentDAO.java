package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Mentor;
import models.Student;

public class StudentDAO implements InterfaceDAO<Student> {

    connectDB connect = DAO.connectDB.getInstance();


    public void add(Student student) {

        String sql = String.format("INSERT INTO users " +
                "(first_name, last_name, email, password, role, klass)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword(), "student", student.getKlass());
        String wallet = String.format("INSERT INTO wallet (student_id,money, experience) VALUES (%s,0,0)",student.getID());

        try {
            connect.addRecord(sql);
            connect.addRecord(wallet);
            System.out.println(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }


    public ArrayList get(){
        ArrayList<Student> studentList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult("SELECT * FROM users where role like 'student'");

            while (result.next()) {
                int  id = result.getInt("id");
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                String email = result.getString("email");
                String password = result.getString("password");
                String klass = result.getString("klass");
                Student student = new Student(id,first_name,last_name,email,password,klass);
                studentList.add(student);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return studentList;
    }

    public void set(Student student) {
        try {
            String sql = String.format("UPDATE users SET first_name='%s',last_name = '%s',email = '%s', password = '%s' WHERE id = %s",student.getFirstName(),student.getLastName(),student.getEmail(),student.getPassword(),student.getID());
            connect.addRecord(sql);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void remove(Student student) {
        try {
            String sql = String.format("DELETE from users WHERE id = %s", student.getID());
            connect.addRecord(sql);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }


    }
}
