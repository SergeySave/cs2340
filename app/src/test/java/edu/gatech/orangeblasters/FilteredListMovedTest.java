//Riley Goodling's JUnit

package edu.gatech.orangeblasters;

import org.junit.Test;

import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class FilteredListMovedTest {
    //Tests that the update method works as expected for the filtered list
    @Test
    public void testMoved() {
        FilteredList<String> list = new FilteredList<>(String.class,
                (query, string) -> query == null || string.contains(query) ? 1 : 0,
                String::compareTo, String::equals, null);

        //List exists
        assertNotNull(list.getSortedList());
        //List empty
        assertEquals(0, list.getSortedList().size());

        //Force an item in
        list.getSortedList().add("ITEM");
        //Make the list represent the data source
        list.update();

        //List exists
        assertNotNull(list.getSortedList());
        //List empty
        assertEquals(0, list.getSortedList().size());

        //Search while dataSource is null
        list.setFilterText("filter");

        //List exists
        assertNotNull(list.getSortedList());
        //List empty
        assertEquals(0, list.getSortedList().size());

        //Empty dataSource
        list.setDataSource(Stream::empty);

        //List exists
        assertNotNull(list.getSortedList());
        //List empty
        assertEquals(0, list.getSortedList().size());

        list.setDataSource(() -> Stream.of("filter_2", "notFilter_1", "filter_1", "notFilter_2"));

        //List exists
        assertNotNull(list.getSortedList());
        //List empty
        assertEquals(2, list.getSortedList().size());
        assertEquals("filter_1", list.getSortedList().get(0));
        assertEquals("filter_2", list.getSortedList().get(1));
    }
}
