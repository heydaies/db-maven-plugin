package vs.db;

public interface DriverLoader {
    void load(String name) throws ClassNotFoundException;
}
