package edu.gatech.orangeblasters;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class LiveList<T> extends LiveData<List<T>> implements List<T> {

    private List<T> backing;

    public LiveList(List<T> backing) {
        this.backing = backing;
    }

    @Override
    public int size() {
        return backing.size();
    }

    @Override
    public boolean isEmpty() {
        return backing.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backing.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return backing.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return backing.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] a) {
        return backing.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (backing.add(t)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (backing.remove(o)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return backing.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        if (backing.addAll(c)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        if (backing.addAll(index, c)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        if (backing.removeAll(c)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        if (backing.retainAll(c)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        if (!isEmpty()) {
            backing.clear();
            update();
        }
    }

    @Override
    public T get(int index) {
        return backing.get(index);
    }

    @Override
    public T set(int index, T element) {
        T ret = backing.set(index, element);
        update();
        return ret;
    }

    @Override
    public void add(int index, T element) {
        backing.add(index, element);
        update();
    }

    @Override
    public T remove(int index) {
        T ret = backing.remove(index);
        update();
        return ret;
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        return new LstIter(backing.listIterator());
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return new LstIter(backing.listIterator(index));
    }

    @NonNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        LiveList<T> subList = new LiveList<>(backing.subList(fromIndex, toIndex));
        subList.observeForever((c) -> update());
        return subList;
    }

    @Override
    public int indexOf(Object o) {
        return backing.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return backing.lastIndexOf(o);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        backing.replaceAll(operator);
        update();
    }

    @Override
    public void sort(Comparator<? super T> c) {
        backing.sort(c);
        update();
    }

    @Override
    public Spliterator<T> spliterator() {
        return backing.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        if (backing.removeIf(filter)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "LiveList(" + backing.toString() + ")";
    }

    @Override
    public int hashCode() {
        return backing.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return backing.equals(obj);
    }

    private void update() {
        super.postValue(new ArrayList<>(backing));
    }

    private class LstIter implements ListIterator<T> {

        private ListIterator<T> backingIter;

        private LstIter(ListIterator<T> backingIter) {
            this.backingIter = backingIter;
        }

        @Override
        public boolean hasNext() {
            return backingIter.hasNext();
        }

        @Override
        public T next() {
            return backingIter.next();
        }

        @Override
        public boolean hasPrevious() {
            return backingIter.hasPrevious();
        }

        @Override
        public T previous() {
            return backingIter.previous();
        }

        @Override
        public int nextIndex() {
            return backingIter.nextIndex();
        }

        @Override
        public int previousIndex() {
            return backingIter.previousIndex();
        }

        @Override
        public void remove() {
            backingIter.remove();
            update();
        }

        @Override
        public void set(T t) {
            backingIter.set(t);
            update();
        }

        @Override
        public void add(T t) {
            backingIter.add(t);
            update();
        }
    }
}
