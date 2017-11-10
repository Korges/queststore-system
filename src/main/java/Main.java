import DAO.StudentDAO;
import controller.Admin.CreateGroup;
import controller.AdminController;
import controller.MainController;
import com.sun.net.httpserver.HttpServer;
import controller.Admin.CreateMentor;
import controller.Admin.ListMentor;
import controller.Mentor.CreateStudent;
import controller.Mentor.EditStudent;
import controller.Mentor.ViewStudent;
import controller.MentorController;
import controller.Student.CreateFundraise;
import controller.Student.JoinFundraise;
import controller.Student.LeaveFundraise;
import controller.Student.ListAllFundraise;
import controller.StudentController;
import models.Student;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws IOException,SQLException{
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);



        // set routes
        server.createContext("/login-page", new MainController());
        server.createContext("/admin", new AdminController());
        server.createContext("/static", new Static());
        server.createContext("/admin/mentor-list", new ListMentor());
        server.createContext("/admin/create-mentor", new CreateMentor());
        server.createContext("/admin/create-group", new CreateGroup());
        server.createContext("/mentor", new MentorController());
        server.createContext("/mentor/create-student", new CreateStudent());
        server.createContext("/mentor/edit-student", new EditStudent());
        server.createContext("/mentor/view-student", new ViewStudent());
        server.createContext("/student", new StudentController());
        server.createContext("/student/fundraise-list", new ListAllFundraise());
        server.createContext("/student/create-fundraise", new CreateFundraise());
        server.createContext("/student/join-fundraise", new JoinFundraise());
        server.createContext("/student/leave-fundraise", new LeaveFundraise());



        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
       // MainController controller = new MainController();
        //controller.setUp();


    }
}