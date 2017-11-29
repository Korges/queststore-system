
import controller.*;

import com.sun.net.httpserver.HttpServer;
import controller.StudentHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws IOException,SQLException{
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/login-page", new MainController());

        server.createContext("/admin", new AdminHandler());
        server.createContext("/mentor", new MentorHandler());
        server.createContext("/student", new StudentHandler());

        server.createContext("/static", new Static());

        server.setExecutor(null); // creates a default executor


        // start listening
        server.start();
    }
}