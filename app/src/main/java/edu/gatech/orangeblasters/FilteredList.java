package edu.gatech.orangeblasters;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Represents a filtered list
 *
 * @param <T> the type of the list
 */
public class FilteredList<T> {

    private Supplier<Stream<T>> dataSource;
    private final BiFunction<String, T, Integer> relFunc;
    private String filterText;
    private final SortedList<T> sortedList;

    /**
     * Sets the source of the data that will be used
     *
     * @param dataSource the source to get the data from
     */
    public void setDataSource(Supplier<Stream<T>> dataSource) {
        this.dataSource = dataSource;
        update();
    }

    /**
     * Sets the filter text
     *
     * @param filterText the text to be filtered
     */
    public void setFilterText(String filterText) {
        this.filterText = filterText;
        update();
    }

    /**
     * Updates teh list
     */
    public void update() {
        sortedList.beginBatchedUpdates();
        sortedList.clear();
        if (dataSource != null) {
            Stream<T> tStream = dataSource.get();
            Stream<T> filtered = tStream
                    .filter(donation -> relFunc.apply(filterText, donation) > 0);
            filtered.forEach(sortedList::add);
        }
        sortedList.endBatchedUpdates();
    }

    /**
     * Gets the sorted list
     *
     * @return the sorted list
     */
    public SortedList<T> getSortedList() {
        return sortedList;
    }

    /**
     * Create a new filtered list
     *
     * @param clazz the class of the stored type
     * @param relevanceFunction a function that determines relevance
     * @param comparator a comparison function for items
     * @param equivalence an equivalence function for items
     * @param listUpdater a callback for when the list is updated
     */
    public FilteredList(Class<T> clazz, BiFunction<String, T, Integer> relevanceFunction,
                        Comparator<T> comparator, BiPredicate<T, T> equivalence,
                        ListUpdateCallback listUpdater) {
        this.relFunc = relevanceFunction;
        sortedList = new SortedList<>(clazz, new SortedList.Callback<T>() {

            @Override
            public int compare(T o1, T o2) {
                Comparator<T> comparing = Comparator.comparing((t) -> relFunc.apply(filterText, t));
                Comparator<T> thenComparing = comparing.thenComparing(comparator);
                return thenComparing.compare(o1, o2);
            }

            @Override
            public void onChanged(int position, int count) {
                if (listUpdater != null) {
                    listUpdater.onChanged(position, count, null);
                }
            }

            @Override
            public boolean areContentsTheSame(T oldItem, T newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(T item1, T item2) {
                return equivalence.test(item1, item2);
            }

            @Override
            public void onInserted(int position, int count) {
                if (listUpdater != null) {
                    listUpdater.onInserted(position, count);
                }
            }

            @Override
            public void onRemoved(int position, int count) {
                if (listUpdater != null) {
                    listUpdater.onRemoved(position, count);
                }
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                if (listUpdater != null) {
                    listUpdater.onMoved(fromPosition, toPosition);
                }
            }
        });
    }
}
