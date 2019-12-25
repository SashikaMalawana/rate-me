package hello.com.eventratingapp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperty {

    private static String eventName = "Event Name";
    private static String countryOfOrigin = "Country Of Origin";
    private static String riginalLanguage = "Original Language";
    private static String genre = "Genre";

    public static String readPropertyFile() {
        String event = null;
        Properties property = new Properties();
        try {
            InputStream input = new FileInputStream("C:\\Users\\SASHIKA\\AndroidStudioProjects\\EventRatingApp\\app\\src\\main\\java\\hello\\com\\eventratingapp\\asset\\config.properties");
            property.load(input);
            event = property.getProperty("eventname");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public static String getEventName() {
        return eventName;
    }

    public static String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public static String getOriginalLanguage() {
        return riginalLanguage;
    }

    public static String getGenre() {
        return genre;
    }
}
