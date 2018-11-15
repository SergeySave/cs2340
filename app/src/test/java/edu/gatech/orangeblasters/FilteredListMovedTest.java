//Riley Goodling's JUnit

package edu.gatech.orangeblasters;

import android.support.v7.util.ListUpdateCallback;

import org.junit.Test;

import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;

public class FilteredListMovedTest {
    //Tests that the update method works as expected for the filtered list
    @Test
    public void testMoved() {
        int counter[] = new int[1];
        FilteredList<String> list = new FilteredList<>(String.class,
                (query, string) -> ((query == null) || string.contains(query)) ? 1 : 0,
                String::compareTo, String::equals, null);

        FilteredList<String> list2 = new FilteredList<>(String.class,
                (query, string) -> ((query == null) || string.contains(query)) ? 1 : 0,
                String::compareTo, String::equals, new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                counter[0] += count;
            }

            @Override
            public void onRemoved(int position, int count) {

            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {

            }

            @Override
            public void onChanged(int position, int count, Object payload) {

            }
        });


        list.setDataSource(() -> Stream.of("filter_2", "notFilter_1", "filter_1", "notFilter_2"));
        list2.setDataSource(() -> Stream.of("filter_2", "notFilter_1", "filter_1", "notFilter_2"));

        //List empty
        assertEquals(4, counter[0]);
    }
}
