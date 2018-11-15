package edu.gatech.orangeblasters;
//Ross's j unit

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;

import static junit.framework.Assert.assertNotNull;


public class DonationTest {

    private Donation d1;
    private Donation d2;
    private Donation d3;
    private Donation d4;

    @Before
    public void setUp() {

        OffsetDateTime time1 = OffsetDateTime.of(LocalDateTime.of(
                2017, 5, 12, 5, 45),
                ZoneOffset.ofHoursMinutes(6, 30));
        OffsetDateTime time2 = OffsetDateTime.of(LocalDateTime.of(
                2018, 2, 12, 5, 45),
                ZoneOffset.ofHoursMinutes(6, 30));
        BigDecimal bigDecimal1 = new BigDecimal("123");



        //d1 & d2 are the same
        d1 = new Donation("abc", time1, "location1", "chair",
                "big chair", bigDecimal1, DonationCategory.CLOTHING,
                "None", "12345" );
        d2 = new Donation("abc", time1, "location1", "chair",
                "big chair", bigDecimal1, DonationCategory.CLOTHING,
                "None", "12345" );


        // d3 & d4 are different
        d3 = new Donation("qwerty", time1, "location1", "chair",
                "big chair", bigDecimal1, DonationCategory.CLOTHING,
                "None", "12345" );
        d4 = new Donation("asdfg", time2, "location2", "desk",
                "big desk", bigDecimal1, DonationCategory.HOUSEHOLD,
                "None", "54321" );

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
