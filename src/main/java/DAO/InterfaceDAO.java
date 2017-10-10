package DAO;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InterfaceDAO<T> {

    void add(T data) throws SQLException;
    void set(T data) throws SQLException;
    ArrayList<T> get() throws SQLException;
}
