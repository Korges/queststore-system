package models;

import DAO.LevelExperienceDAO;

import java.sql.SQLException;
import java.util.List;

public class LevelExperience {
    Integer level;
    Integer experience;
    Integer id;

    public LevelExperience(Integer experience, Integer level) throws SQLException {
        if (!checkDuplicate(experience, level)){
            this.level = level;
            this.experience = experience;
        } else {
            throw new IllegalArgumentException("This level experience already exists!");
        }
    }
    //todo implement contructor, add it to template create levels

    public LevelExperience(Integer id, Integer experience, Integer level) {
        this.id = id;
        this.experience = experience;
        this.level = level;
    }

    private boolean checkDuplicate(Integer experience, Integer level) throws SQLException {
        LevelExperienceDAO lvlDAO = new LevelExperienceDAO();
        List<LevelExperience> levels = lvlDAO.get();
        LevelExperience levelToCheck = new LevelExperience(experience, level);

        for(LevelExperience lvl: levels) {
            if (lvl.equals(levelToCheck)) {
                return true;
            }
        }
        return false;
    }
}