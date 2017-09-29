package DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import models.Mentor;


public class MentorDAO implements InterfaceDAO<Mentor> {

    connectDB connect = DAO.connectDB.getInstance();

    public void add(Mentor mentor){



        String sql = String.format("INSERT INTO users " +
                "(first_name, last_name, email, password, role, klass)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", mentor.getFirstName(), mentor.getLastName(), mentor.getEmail(), mentor.getPassword(), "mentor", mentor.getKlass());
        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.out.println("Something went wrong, propably database is occupied by another process, shutting down...");
            System.exit(0);
        }

    }

    public ArrayList get(){
        ArrayList<Mentor> mentorList = new ArrayList<>();
        try {

            ResultSet result = connect.getResult("SELECT * FROM users where role like 'mentor'");

            while (result.next()) {
                int  id = result.getInt("id");
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                String email = result.getString("email");
                String password = result.getString("password");
                String klass = result.getString("klass");
                Mentor mentor = new Mentor(id,first_name,last_name,email,password,klass);
                mentorList.add(mentor);
            }
            } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return mentorList;
    }

    public void set(Mentor mentor) {
        try {
            String sql = String.format("UPDATE users SET first_name='%s',last_name = '%s',email = '%s', password = '%s' WHERE id = %s",mentor.getFirstName(),mentor.getLastName(),mentor.getEmail(),mentor.getPassword(),mentor.getID());
            connect.addRecord(sql);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void remove(Mentor mentor) {
        try {
            String sql = String.format("DELETE from users WHERE id = %s", mentor.getID());
            connect.addRecord(sql);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }


    }
}

