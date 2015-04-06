package vs.db.util;

public interface DriverLoader {
    void load(String name) throws ClassNotFoundException;
}
