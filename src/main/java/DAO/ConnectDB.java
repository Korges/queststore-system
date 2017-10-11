package DAO;

import java.sql.*;

public class ConnectDB {

    private static ConnectDB connectDB = null;

    private static Connection connection = null;

    private ConnectDB() throws SQLException{
        connection = connect();
    }

    public static ConnectDB getInstance() throws SQLException{

        if (connectDB == null) {
            connectDB = new ConnectDB();
        }
        return connectDB;
    }


    public Connection connect() throws SQLException{

        String url = "jdbc:sqlite:src/main/resources/db/database.db";
        Connection conn;
        conn = DriverManager.getConnection(url);
        return conn;
    }



    public ResultSet getResult(String sql) throws SQLException{

        ResultSet result;
        Statement stmt = connection.createStatement();
        result = stmt.executeQuery(sql);
        return result;
    }


    public void addRecord(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

}
