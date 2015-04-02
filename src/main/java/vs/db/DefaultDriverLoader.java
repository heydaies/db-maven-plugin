package vs.db;

public class DefaultDriverLoader implements DriverLoader {
    @Override
    public void load(String name) throws ClassNotFoundException {
        Class.forName(name);
    }
}
