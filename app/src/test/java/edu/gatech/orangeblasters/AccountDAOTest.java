package edu.gatech.orangeblasters;
// sandy xie J Unit

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

public class AccountDAOTest {

    @Before
    public void setUp() {

    }
    @Test
    public void testConditionOne() {

    }
    @Test
    public void testConditionTwo() {

    }
    @Test
    public void testConditionThree() {

    }
    @Test
    public void testConditionFour() {

    }

    //Tests that the update method works as expected for the filtered list
    @Test
    public void testUpdate() {
        FilteredList<String> list = new FilteredList<>(String.class,
                (query, string) -> ((query == null) || string.contains(query)) ? 1 : 0,
                String::compareTo, String::equals, null);

        //List exists
        Assert.assertNotNull(list.getSortedList());
        //List empty
        Assert.assertEquals(0, list.getSortedList().size());

        //Force an item in
        list.getSortedList().add("ITEM");
        //Make the list represent the data source
        list.update();

        //List exists
        Assert.assertNotNull(list.getSortedList());
        //List empty
        Assert.assertEquals(0, list.getSortedList().size());

        //Search while dataSource is null
        list.setFilterText("filter");

        //List exists
        Assert.assertNotNull(list.getSortedList());
        //List empty
        Assert.assertEquals(0, list.getSortedList().size());

        //Empty dataSource
        list.setDataSource(Stream::empty);

        //List exists
        Assert.assertNotNull(list.getSortedList());
        //List empty
        Assert.assertEquals(0, list.getSortedList().size());

        list.setDataSource(() -> Stream.of("filter_1", "notFilter_1", "filter_2", "notFilter_2"));

        //List exists
        Assert.assertNotNull(list.getSortedList());
        //List empty
        Assert.assertEquals(2, list.getSortedList().size());
        Assert.assertEquals("filter_1", list.getSortedList().get(0));
        Assert.assertEquals("filter_2", list.getSortedList().get(1));
    }

}
