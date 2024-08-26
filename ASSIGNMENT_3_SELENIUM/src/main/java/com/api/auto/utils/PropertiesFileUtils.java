package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils {

    private static final String CONFIG_FILE = "configuration/configs.properties";
    private static final String TOKEN_FILE = "configuration/token.properties";

    public static String getProperty(String key) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveToken(String token) {
        Properties properties = new Properties();
        try (FileOutputStream output = new FileOutputStream(TOKEN_FILE)) {
            properties.setProperty("token", token);
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getToken() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(TOKEN_FILE)) {
            properties.load(input);
            return properties.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
