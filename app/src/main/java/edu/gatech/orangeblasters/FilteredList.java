package edu.gatech.orangeblasters;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FilteredList<T> {

    private Supplier<Stream<T>> dataSource;
    private BiFunction<String, T, Integer> relFunc;
    private String filterText;
    private SortedList<T> sortedList;

    public void setDataSource(Supplier<Stream<T>> dataSource) {
        this.dataSource = dataSource;
        update();
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
        update();
    }

    public void update() {
        sortedList.beginBatchedUpdates();
        sortedList.clear();
        if (dataSource != null) {
            dataSource.get()
                    .filter(donation -> relFunc.apply(filterText, donation) > 0)
                    .forEach(sortedList::add);
        }
        sortedList.endBatchedUpdates();
    }

    public SortedList<T> getSortedList() {
        return sortedList;
    }

    public FilteredList(Class<T> clazz, BiFunction<String, T, Integer> relevanceFunction, Comparator<T> comparator, BiPredicate<T, T> equivalence, ListUpdateCallback listUpdater) {
        this.relFunc = relevanceFunction;
        sortedList = new SortedList<>(clazz, new SortedList.Callback<T>() {

            @Override
            public int compare(T o1, T o2) {
                return Comparator.comparing((Function<T, Integer>)((t) -> relFunc.apply(filterText, t)))
                        .thenComparing(comparator)
                        .compare(o1, o2);
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
