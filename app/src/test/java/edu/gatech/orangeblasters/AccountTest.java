package edu.gatech.orangeblasters;
//Serena's junit

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.account.Manager;

import static junit.framework.Assert.assertNotNull;


public class AccountTest {

    private Account a1;
    private Account a2;
    private Account a3;
    private Account a4;

    @Before
    public void setUp() {

        //d1 & d2 are the same
        a1 = new Manager("21", "manager1", "manager3@gmail.com",
                "livelovegoodwill", AccountState.NORMAL);
        a2 = new Manager("21", "manager1", "manager3@gmail.com",
                "livelovegoodwill", AccountState.NORMAL);


        // d3 & d4 are different
        a3 = new LocationEmployee("301", "worker1", "employee1@gmail.com",
                "yeet", AccountState.NORMAL, "903311" );
        a4 = new LocationEmployee("302", "worker2", "employee2@gmail.com",
                "yeet", AccountState.LOCKED, "903312" );
    }



    @Test
    public void testNull() {
        //test donations not empty
        //Assert.assertFalse(a1.equals(null));
        //Assert.assertFalse(a2.equals(null));
        //Assert.assertFalse(a3.equals(null));

        assertNotNull(a1);
        assertNotNull(a2);
        assertNotNull(a3);
        assertNotNull(a4);


    }

    @Test
    public void testEquals() {
        //test accounts that are the equivalent
        Assert.assertTrue(a1.equals(a2));

        //account equals itself
        Assert.assertTrue(a1.equals(a1));
        Assert.assertTrue(a2.equals(a2));
        Assert.assertTrue(a3.equals(a3));
        Assert.assertTrue(a4.equals(a4));


        Assert.assertEquals(a1,a2);
        Assert.assertEquals(a2,a1);

    }

    @Test
    public void testNotEquals() {
        //tests accounts that aren't equivaleng
        Assert.assertFalse(a1.equals(a3));
        Assert.assertFalse(a1.equals(a4));
        Assert.assertFalse(a2.equals(a3));
        Assert.assertFalse(a1.equals(a4));


    }





}
