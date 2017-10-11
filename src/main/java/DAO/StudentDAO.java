package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Student;
import models.Wallet;

public class StudentDAO implements InterfaceDAO<Student> {

    ConnectDB connect;

    public StudentDAO() throws SQLException{

        connect = DAO.ConnectDB.getInstance();
    }


    public void add(Student student) throws SQLException{

        String sql = String.format("INSERT INTO users " +
                "(first_name, last_name, email, password, role, klass)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword(), "student", student.getKlass());
        String wallet = "INSERT INTO wallets (student_id,money, experience, level) VALUES((SELECT id FROM users ORDER BY id DESC LIMIT 1),0,0,0)";

        connect.addRecord(sql);
        connect.addRecord(wallet);
    }


    public ArrayList<Student> get() throws SQLException{

        ArrayList<Student> studentList = new ArrayList<>();

        ResultSet result = connect.getResult("SELECT * from users join wallets on users.id = wallets.student_id WHERE role like 'student';");
        while (result.next()) {
            Student student = createStudent(result);
            studentList.add(student);
        }

        return studentList;
    }


    public Student createStudent(ResultSet result) throws SQLException{

        int  id = result.getInt("id");
        String first_name = result.getString("first_name");
        String last_name = result.getString("last_name");
        String email = result.getString("email");
        String password = result.getString("password");
        String klass = result.getString("klass");
        Integer money = result.getInt("money");
        Integer level = result.getInt("level");
        Wallet wallet = new Wallet(money,money,level);
        Student student = new Student(id,first_name,last_name,email,password,klass);
        student.setWallet(wallet);

        return student;
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

    public void editWalletValue(Student student) {

        try {
            String sql = String.format("UPDATE wallets SET money = '%d',level = %s where id = %s", student.wallet.getBalance(),student.wallet.getLevel(), student.getID());
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Student getStudentById(Integer id) {
        Student student = null;
        try {
            String sql = String.format("SELECT * from users join wallets on users.id = wallets.student_id WHERE users.id = %d", id);
            ResultSet result = connect.getResult(sql);
            result.next();
            student = createStudent(result);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return student;
    }

    private void setLevelExperience(Student student){

        try{
            String sql = String.format("SELECT MAX(level) from level_experience WHERE exp <=%s", student.wallet.getLevel());
            ResultSet result = connect.getResult(sql);

            Integer level = result.getInt("MAX(level)");
            student.wallet.setLevel(level);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void setWalletDetail(Integer studentId, Integer experience){
        Student student = getStudentById(studentId);
        student.wallet.add(experience); //todo
        setLevelExperience(student);
        editWalletValue(student);
    }


}
