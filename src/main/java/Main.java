import controller.AdminController;
import controller.MainController;
import com.sun.net.httpserver.HttpServer;
import controller.MentorListWeb;

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
        server.createContext("/admin/mentor-list", new MentorListWeb());


        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
       // MainController controller = new MainController();
        //controller.setUp();


    }
}