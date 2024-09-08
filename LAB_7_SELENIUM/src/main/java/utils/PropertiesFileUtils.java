package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils {
    private static final String PROP_FILE_PATH = "./configuration/configs.properties";
    private static Properties properties;

    static {
        try (FileInputStream input = new FileInputStream(PROP_FILE_PATH)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}