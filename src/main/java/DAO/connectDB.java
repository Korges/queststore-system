package DAO;

import java.sql.*;

public class connectDB {

    private static connectDB connectDB = null;
    private static Connection connection = null;

    private connectDB(){
        connection = connect();
    }

    public static connectDB getInstance() {
        if (connectDB == null) {
            connectDB = new connectDB();
        }
        return connectDB;
    }


    public Connection connect(){
        String url = "jdbc:sqlite:src/main/resources/db/database.db";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }



        return conn;
    }

    public void close() throws SQLException{
        connection.close();
    }

    public ResultSet getResult(String sql) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql);

        return result;
    }

    public void addRecord(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

}
