package DAO;

import models.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDAO implements InterfaceDAO<Group> {

    private ConnectDB connect;

    public GroupDAO() throws SQLException{
        connect = DAO.ConnectDB.getInstance();
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


    public void set(Group group) throws SQLException{
        String sql = String.format("UPDATE klasses SET name='%s' WHERE id = %s",group.getName(),group.getID());
        System.out.println(sql);
        connect.addRecord(sql);
    }

    public Group getGroupById(String id) throws SQLException{
        String sql = String.format("SELECT * FROM klasses where id like '%s'",id);
        ResultSet result = connect.getResult(sql);
        Group group = null;

        if(result.next()){
            Integer idInt = result.getInt("id");
            String name = result.getString("name");
            group = new Group(idInt, name);
        }
        return group;

    }
}
