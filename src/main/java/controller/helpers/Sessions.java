package controller.helpers;

import DAO.ConnectDB;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sessions {

    public static boolean checkSession(String session,String controller) {

        String sqlQuery = String.format("SELECT * FROM sessions WHERE session_id like '%s' and role like '%s'",session,controller);
        ResultSet resultSet = null;
        try {
            ConnectDB connectDB = ConnectDB.getInstance();
            resultSet = connectDB.getResult(sqlQuery);
            if(resultSet.next()){
                if(resultSet.getString("role").equals(controller) && resultSet.getString("session_id").equals(session)){
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static void redirect(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().set("Location", "/login-page");
        httpExchange.sendResponseHeaders(302, -1);
    }

    public static String getIdBySession(String session){
        String sqlQuery = String.format("SELECT * FROM sessions WHERE session_id like '%s'",session);
        ResultSet resultSet = null;
        String id = "";
        try {
            ConnectDB connectDB = ConnectDB.getInstance();
            resultSet = connectDB.getResult(sqlQuery);
            if(resultSet.next()){
                id = resultSet.getString("user_id");
                }

        } catch (SQLException e) {
            return id;
        }

        return id;
    }
}
