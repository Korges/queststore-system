package DAO;

import models.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDAO implements InterfaceDAO<Group> {

    private connectDB connect;

    public GroupDAO() throws SQLException{
        connect = DAO.connectDB.getInstance();
    }

    public void add(Group group) throws SQLException{

        String sql = String.format("INSERT INTO klasses " +
                "(name)" +
                " VALUES ('%s')",group.getName());
        connect.addRecord(sql);
    }


    public ArrayList<Group> get() throws SQLException{

        ArrayList<Group> groupList = new ArrayList<>();
        ResultSet result = connect.getResult("SELECT * FROM klasses");
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            Group group = new Group(id,name);
            groupList.add(group);
        }
        return groupList;
    }


    public void set(Group group) {

    }
}
