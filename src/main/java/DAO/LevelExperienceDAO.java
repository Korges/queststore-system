package DAO;

import java.sql.SQLException;

public class LevelExperienceDAO {

    private connectDB connect;

    public LevelExperienceDAO()throws SQLException{
        connect = DAO.connectDB.getInstance();
    }


    public void add(Integer level, Integer experience) throws SQLException{
        String sql = String.format("INSERT INTO level_experience " +
                "(level, exp)" +
                " VALUES ('%d', '%d')", level, experience);
            connect.addRecord(sql);
    }

}
