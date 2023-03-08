package kex.java.util;

import java.util.Collection;

abstract class ArrayList<E> extends List<E> {

    public ArrayList() {
        super();
    }

    public ArrayList(int capacity) {
        super();
    }

    public ArrayList(Collection<? extends E> c) {
        super();
        addAll(c);
    }

    public void trimToSize() {
        // nothing
    }

    public void ensureCapacity(int minCapacity) {
    }
}
