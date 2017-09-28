package DAO;
import java.util.ArrayList;

public interface InterfaceDAO<T> {

    public void add(T data);
    public ArrayList get();
    public void set(T data);
}
