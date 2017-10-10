package DAO;

import models.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDAO implements InterfaceDAO<Group> {

    connectDB connect = DAO.connectDB.getInstance();

    public void add(Group group){

        String sql = String.format("INSERT INTO klasses " +
                "(name)" +
                " VALUES ('%s')",group.getName());
        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.out.println("Wrong");
            System.exit(0);
        }
    }


    public ArrayList<Group> get() {

        ArrayList<Group> groupList = new ArrayList<>();
        try {
            ResultSet result = connect.getResult("SELECT * FROM klasses");
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Group group = new Group(id,name);
                groupList.add(group);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return groupList;
    }


    public void set(Group group) {

    }
}
