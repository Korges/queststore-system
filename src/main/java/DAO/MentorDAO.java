package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Mentor;


public class MentorDAO implements InterfaceDAO<Mentor> {

    private ConnectDB connect;

    public MentorDAO() throws SQLException{
        connect = DAO.ConnectDB.getInstance();
    }

    public void add(Mentor mentor) throws SQLException{

        String sql = String.format("INSERT INTO users " +
                "(first_name, last_name, email, password, role, klass)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", mentor.getFirstName(), mentor.getLastName(), mentor.getEmail(), mentor.getPassword(), "mentor", mentor.getKlass());
        connect.addRecord(sql);
    }


    public ArrayList<Mentor> get() throws SQLException{

        ArrayList<Mentor> mentorList = new ArrayList<>();

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
        return mentorList;
    }

    public Mentor getMentorById(String id) throws SQLException {
        String sql = String.format("SELECT * FROM users where id like '%s'",id);
        ResultSet result = connect.getResult(sql);
        Mentor mentor = null;

        if(result.next()){
            int  idInt = result.getInt("id");
            String first_name = result.getString("first_name");
            String last_name = result.getString("last_name");
            String email = result.getString("email");
            String password = result.getString("password");
            String klass = result.getString("klass");
            mentor = new Mentor(idInt,first_name,last_name,email,password,klass);
        }
        return mentor;
    }


    public void set(Mentor mentor) throws SQLException{
        String sql = String.format("UPDATE users SET first_name='%s',last_name = '%s',email = '%s', password = '%s' WHERE id = %s",mentor.getFirstName(),mentor.getLastName(),mentor.getEmail(),mentor.getPassword(),mentor.getID());
        connect.addRecord(sql);
    }


    public void remove(Mentor mentor) throws SQLException {
        String sql = String.format("DELETE from users WHERE id = %s", mentor.getID());
        connect.addRecord(sql);
}
}

