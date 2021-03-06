package models;

import DAO.LevelExperienceDAO;

import java.sql.SQLException;
import java.util.List;

public class LevelExperience {
    Integer level;
    Integer experience;
    Integer id;

    public LevelExperience(Integer experience, Integer level) throws SQLException {
        if (!checkDuplicate(level)){
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

    private boolean checkDuplicate(Integer level) throws SQLException {
        LevelExperienceDAO lvlDAO = new LevelExperienceDAO();
        List<LevelExperience> levels = lvlDAO.get();

        for(LevelExperience lvl: levels) {
            if (lvl.getLevel().equals(level)) {
                return true;
            }
        }
        return false;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}