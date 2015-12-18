package ru.fizteh.fivt.students.evlinkov.twitterstream;
/**
 * Created by evlinkov on 17.12.15.
 */
import com.google.maps.model.GeocodingResult;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.Bounds;
import com.google.maps.model.LatLng;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import static java.lang.Math.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class Location {
    private static final double RADIUS_OF_THE_EARTH = 6371;
    private GeocodingResult result;
    private double radius;
    Location(String place) throws Exception {
        if (!place.equals("nearby")) {
            String apiKey = getKeyFromProperties();
            GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
            result = GeocodingApi.geocode(context, place).await()[0];
            radius = calculateRadius();
        }
    }
    private String getKeyFromProperties() throws IOException {
        Properties property = new Properties();
        try (InputStream input = new FileInputStream("twitter4j.properties")) {
            property.load(input);
        } catch (FileNotFoundException e) {
            System.err.println("Not found the file : " + e.getMessage());
            throw e;
        } catch (IOException e) {
            System.err.println("Not read the file : " + e.getMessage());
            throw e;
        }
        return property.getProperty("googleApiKey");
    }
    public LatLng getLocation() {
        return result.geometry.location;
    }
    public double getRadius() {
        return radius;
    }
    private double calculateRadius() {
        LatLng pointerFirst = result.geometry.bounds.northeast;
        LatLng pointerSecond = result.geometry.bounds.southwest;
        double rad = 180.0 / PI;
        double x = cos(pointerFirst.lat / rad) * cos(pointerFirst.lng / rad) * cos(pointerSecond.lat / rad)
                * cos(pointerSecond.lng / rad);
        double y = cos(pointerFirst.lat / rad) * sin(pointerFirst.lng / rad) * cos(pointerSecond.lat / rad)
                * sin(pointerSecond.lng / rad);
        double z = sin(pointerFirst.lat / rad) * sin(pointerSecond.lat / rad);

        return RADIUS_OF_THE_EARTH * acos(x + y + z);
    }
    public final Bounds getBounds() {
        return result.geometry.bounds;
    }
}
