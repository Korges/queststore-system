package controller.Mentor;

import DAO.StudentDAO;
import DAO.WebTemplateDao;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.*;

public class ViewStudent implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            new WebTemplateDao(); //todo is it obligatory? Pasted from class ListMentor
            String response = "";
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {

                response = getStudentsList();
            }
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (SQLException e ){
        }
    }

    public String getStudentsList() throws SQLException{

        List<String[]> attributes = getStudentsAttributes();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/view-student.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("students", attributes);

        String response = ""; //todo should it be empty?
        try {
            response = template.render(model);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    private List<String[]> getStudentsAttributes() throws SQLException {
        //todo deeper encapsulation(?)
        StudentDAO sDAO = new StudentDAO();

        List<Student> students= new ArrayList<>(sDAO.get());
        List<String[]> attributes = new LinkedList<>();
        //is it ok, can it be better - faster, instead of String[]
        for(Student student: students) {
            String id = student.getID().toString();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            String email = student.getEmail();
            String klass = student.getKlass();

            String[] record = {id, firstName, lastName, email, klass};
            attributes.add(record);
        }
        return attributes;
    }

}
