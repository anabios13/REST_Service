package by.anabios13.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE = "db.properties";

    static {
        loadProperties();
    }

    public PropertiesUtil() {
    }

    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static void setProperties(String key, String value) {
        PROPERTIES.setProperty(key, value);
        saveProperties();
    }

    private static void saveProperties() {
        try (OutputStream outFile = new FileOutputStream(PROPERTIES_FILE)) {
            PROPERTIES.store(outFile, null);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save properties", e);
        }
    }

    private static void loadProperties() {
        try (InputStream inFile = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            PROPERTIES.load(inFile);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

}
