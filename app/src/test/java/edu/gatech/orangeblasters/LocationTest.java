package edu.gatech.orangeblasters;
// sandy xie J Unit
import junit.framework.Assert;

import org.junit.Test;
import org.junit.Before;

import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationType;

import static junit.framework.Assert.assertNotNull;


public class LocationTest {

    public String password;
    private Location d1;
    public Location d2;
    private Location d3;
    public Location d4;

    @Before
    public void setUp() {
        /*
            d1
            id : 2
            location: location1
            location type: drop off
            long: 1
            lat: 1
            address: address
            phone number: 6786282904
         */
        /*
            d2
            id : 2
            location: location1
            location type: drop off
            long: 1
            lat: 1
            address: address
            phone number: 6786282904
         */
        /*
            d3
            id : 3
            location: location2
            location type: drop off
            long: 1.5
            lat: 1
            address: address
            phone number: 6786282904
         */
        /*
            d4
            id : 4
            location: location1
            location type: store
            long: 2
            lat: 1
            address: address
            phone number: 123
         */


        //d1 & d2 are the same
        d1 = new Location("2", "location1",LocationType.DROP_OFF, 1.0,
                1.0, "address", "6786282904");
        d2 = new Location("2", "location1",LocationType.DROP_OFF, 1.0,
                1.0, "address", "6786282904");

        // d3 & d4 are different
        d3 = new Location("3", "location2",LocationType.DROP_OFF, 1.5,
                1.0, "address", "6786282904");
        d4 = new Location("4", "location1",LocationType.STORE, 2.0,
                1.0, "", "123");

        }



    @Test
    public void testNull() {
        //test donations not empty
        Assert.assertFalse(d1.equals(null));
        Assert.assertFalse(d2.equals(null));
        Assert.assertFalse(d3.equals(null));

        assertNotNull(d1);
        assertNotNull(d2);
        assertNotNull(d3);
        assertNotNull(d4);


    }

    @Test
    public void testEquals() {
        //test donations that are the equivalent
        Assert.assertTrue(d1.equals(d2));

        //Donations equals itself
        Assert.assertTrue(d1.equals(d1));
        Assert.assertTrue(d2.equals(d2));
        Assert.assertTrue(d3.equals(d3));
        Assert.assertTrue(d4.equals(d4));


        Assert.assertEquals(d1,d2);
        Assert.assertEquals(d2,d1);

    }

    @Test
    public void testNotEquals() {
        Assert.assertFalse(d1.equals(d3));
        Assert.assertFalse(d1.equals(d4));
        Assert.assertFalse(d2.equals(d3));
        Assert.assertFalse(d1.equals(d4));


    }





}
