package Infra.Common;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties;

    private static void loadProperties() {
        properties = new Properties();
        String resourceFile = "/settings.properties";
        InputStream in = Config.class.getResourceAsStream(resourceFile);

        try {
            properties.load(in);
            assert in != null;
            in.close();
        } catch (Exception e) {
            Common.Report("Unable to read resource file", Common.MessageColor.RED);
        }
    }

    public static String getProperty(String propertyName) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(propertyName);
    }

    public static void setProperty(String propertyName, String propertyValue) {

        if (properties == null) {
            loadProperties();
        }
        properties.put(propertyName, propertyValue);
        //properties.setProperty(propertyName, propertyValue);
    }

}
