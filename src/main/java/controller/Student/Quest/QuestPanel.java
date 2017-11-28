package controller.Student.Quest;

import DAO.QuestDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Quest;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestPanel {






    public List<Quest> getQuestList()  {

        List<Quest> questList = null;
        try {
            QuestDAO fundraiseDAO = new QuestDAO();
            questList = fundraiseDAO.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questList;
    }


}

