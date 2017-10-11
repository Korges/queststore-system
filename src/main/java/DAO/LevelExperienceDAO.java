package DAO;

import java.sql.SQLException;

public class LevelExperienceDAO {

    private ConnectDB connect;

    public LevelExperienceDAO()throws SQLException{
        connect = DAO.ConnectDB.getInstance();
    }


    public void add(Integer level, Integer experience) throws SQLException{
        String sql = String.format("INSERT INTO level_experience " +
                "(level, exp)" +
                " VALUES ('%d', '%d')", level, experience);
            connect.addRecord(sql);
    }

}
