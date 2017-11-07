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

                response = listAllMentors();

            }

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (SQLException e ){

        }
    }

    public String listAllMentors() throws SQLException{

        MentorDAO mDAO = new MentorDAO();
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        ArrayList<String> record = new ArrayList<>();
        ArrayList<Mentor> mentorList = mDAO.get();
        for(Mentor mentor: mentorList){
            record.add(mentor.getFirstName());
            record.add(mentor.getLastName());
            record.add(mentor.getEmail());
            data.add(record);
            record = new ArrayList<>();
        }

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/view-mentor.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("data", data);
        String response = template.render(model);

        return response;
    }

}
