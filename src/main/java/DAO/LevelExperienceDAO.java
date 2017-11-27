package DAO;

import models.LevelExperience;
import models.Mentor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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

    public List<LevelExperience> get() throws SQLException{

        List<LevelExperience> levels = new ArrayList<>();
        ResultSet result = connect.getResult("SELECT * FROM level_experience");

        while (result.next()) {
            Integer id = result.getInt("id");
            Integer level = result.getInt("level");
            Integer experience = result.getInt("exp");

            LevelExperience lvl = new LevelExperience(id, level, experience);
            levels.add(lvl);
        }

        return levels;

    }

    public LevelExperience getLevelById(String id) throws SQLException {
        String sql = String.format("SELECT * FROM level_experience where id like '%s'",id);
        ResultSet result = connect.getResult(sql);
        LevelExperience levelexp = null;

        if(result.next()){
            Integer  idInt = result.getInt("id");
            Integer level = result.getInt("level");
            Integer exp = result.getInt("exp");
            levelexp = new LevelExperience(idInt, exp, level);
        }
        return levelexp;
    }



}
