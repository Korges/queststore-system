package controller;

import DAO.MentorDAO;
import DAO.WebTemplateDao;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Mentor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class MentorListWeb implements HttpHandler{
    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            WebTemplateDao webTemplateDao = new WebTemplateDao();
            String response = "";
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {
                JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/view-mentor.twig");
                ArrayList<ArrayList<String>> data = listAllMentors();
                JtwigModel model = JtwigModel.newModel();

                model.with("data", data);
                response = template.render(model);
            }

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (SQLException e ){

        }
    }

    public ArrayList<ArrayList<String>> listAllMentors() throws SQLException{

        MentorDAO mDAO = new MentorDAO();
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        ArrayList<String> record = new ArrayList<>();
        ArrayList<Mentor> mentorList = mDAO.get();
        if(mentorList.size() == 0){
            UI.UI.showMessage("Mentor list is empty!");
        } else {
            for(Mentor mentor: mentorList){
                record.add(mentor.getFirstName());
                record.add(mentor.getLastName());
                record.add(mentor.getEmail());
                data.add(record);
                record = new ArrayList<>();
            }
        }
        return data;
    }

}
