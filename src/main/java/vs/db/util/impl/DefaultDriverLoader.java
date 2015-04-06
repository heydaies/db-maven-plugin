package vs.db.util.impl;

import vs.db.util.DriverLoader;

public class DefaultDriverLoader implements DriverLoader {
    @Override
    public void load(String name) throws ClassNotFoundException {
        Class.forName(name);
    }
}
