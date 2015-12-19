package ru.fizteh.fivt.students.evlinkov.twitterstream;
/**
 * Created by evlinkov on 17.12.15.
 */
import com.google.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Test;

public class TestLocation {
    @Test
    public void test() {
        String place = "Volgograd";
        LatLng realLocation = new LatLng(48.7193900, 44.5018400);
        LatLng programLocation = new Location(place).getLocation();
        Assert.assertEquals(realLocation, programLocation);
    }
}
