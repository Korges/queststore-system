package DAO;

import java.sql.*;

public class connectDB {

    private static connectDB connectDB = null;

    private static Connection connection = null;

    private connectDB() throws SQLException{
        connection = connect();
    }

    public static connectDB getInstance() throws SQLException{

        if (connectDB == null) {
            connectDB = new connectDB();
        }
        return connectDB;
    }


    public Connection connect() throws SQLException{

        String url = "jdbc:sqlite:src/main/resources/db/database.db";
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        return conn;
    }



    public ResultSet getResult(String sql) throws SQLException{

        ResultSet result = null;
        Statement stmt = connection.createStatement();
        result = stmt.executeQuery(sql);
        return result;
    }


    public void addRecord(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

}
