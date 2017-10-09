package DAO;

import java.sql.SQLException;

public class LevelExperienceDAO {

    connectDB connect = DAO.connectDB.getInstance();

    public void add(Integer level, Integer experience){
        String sql = String.format("INSERT INTO level_experience " +
                "(level, exp)" +
                "VALUES ('%d', '%d')", level, experience);

        try {
            connect.addRecord(sql);
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(0);
        }


    }

}
