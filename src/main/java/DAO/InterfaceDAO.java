package DAO;
import java.util.ArrayList;

public interface InterfaceDAO<T> {

    void add(T data);
    void set(T data);
    ArrayList<T> get();
}
