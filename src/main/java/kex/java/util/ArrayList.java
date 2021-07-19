/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package kex.java.util;

import org.jetbrains.research.kex.AssertIntrinsics;
import org.jetbrains.research.kex.CollectionIntrinsics;
import org.jetbrains.research.kex.UnknownIntrinsics;
import org.jetbrains.research.kex.ObjectIntrinsics;

import java.util.*;
import java.util.function.Consumer;

public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    Object[] elementData;
    private int size;

    public ArrayList(int initialCapacity) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        int actualCapacity = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(actualCapacity > initialCapacity);
        this.elementData = new Object[actualCapacity];
    }

    public ArrayList() {
        super();
        int actualCapacity = UnknownIntrinsics.kexUnknownInt();
        this.elementData = new Object[actualCapacity];
    }

    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        size = elementData.length;
    }

    public void trimToSize() {
        // nothing
    }

    public void ensureCapacity(int minCapacity) {
        AssertIntrinsics.kexAssume(elementData.length > minCapacity);
    }

    private void ensureCapacityInternal(int minCapacity) {
        AssertIntrinsics.kexAssume(elementData.length > minCapacity);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return CollectionIntrinsics.contains(elementData, o);
    }

    @Override
    public int indexOf(Object o) {
        if (CollectionIntrinsics.contains(elementData, o)) {
            int result = UnknownIntrinsics.kexUnknownInt();
            AssertIntrinsics.kexAssume(result >= 0);
            AssertIntrinsics.kexAssume(result < elementData.length);
            return result;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (CollectionIntrinsics.contains(elementData, o)) {
            int result = UnknownIntrinsics.kexUnknownInt();
            AssertIntrinsics.kexAssume(result >= 0);
            AssertIntrinsics.kexAssume(result < elementData.length);
            return result;
        }
        return -1;
    }

    @Override
    public Object clone() {
        ArrayList<?> v = new ArrayList<>();
        v.elementData = Arrays.copyOf(elementData, size);
        v.modCount = 0;
        return v;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        CollectionIntrinsics.arrayCopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("" + index);
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("" + index);
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return (E) elementData[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);
        CollectionIntrinsics.arrayCopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        E oldValue = (E) elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            CollectionIntrinsics.arrayCopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = true;
        if (o == null) {
            result = CollectionIntrinsics.firstOfEach(0, size, (index, cond) -> {
                if (!cond) {
                    if (elementData[index] == null) {
                        fastRemove(index);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            });
        } else {
            result = CollectionIntrinsics.firstOfEach(0, size, (index, cond) -> {
                if (!cond) {
                    if (ObjectIntrinsics.equals(o, elementData[index])) {
                        fastRemove(index);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            });
        }
        return result;
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            CollectionIntrinsics.arrayCopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
    }

    @Override
    public void clear() {
        CollectionIntrinsics.forEach(0, elementData.length, index -> {
            elementData[index] = null;
        });

        size = 0;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        CollectionIntrinsics.arrayCopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);

        int numMoved = size - index;
        if (numMoved > 0)
            CollectionIntrinsics.arrayCopy(elementData, index, elementData, index + numNew, numMoved);

        CollectionIntrinsics.arrayCopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        int numMoved = size - toIndex;
        CollectionIntrinsics.arrayCopy(elementData, toIndex, elementData, fromIndex, numMoved);

        int newSize = size - (toIndex - fromIndex);
        CollectionIntrinsics.forEach(newSize, size, index -> {
            elementData[index] = null;
        });
        size = newSize;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        AssertIntrinsics.kexNotNull(c);
        Object[] other = c.toArray();
        return CollectionIntrinsics.anyOfEach(0, other.length, index -> {
            if (CollectionIntrinsics.contains(elementData, other[index])) {
                remove(other[index]);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        AssertIntrinsics.kexNotNull(c);
        Object[] other = c.toArray();
        return CollectionIntrinsics.anyOfEach(0, other.length, index -> {
            if (!CollectionIntrinsics.contains(elementData, other[index])) {
                remove(other[index]);
                return true;
            }
            return false;
        });
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index);
        return new ArrayList.ListItr(index);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ArrayList.ListItr(0);
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayList.Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    private class ListItr extends ArrayList<E>.Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void set(E e) {
            if (lastRet < 0) throw new IllegalStateException();
            ArrayList.this.set(lastRet, e);
        }

        @Override
        public void add(E e) {
            int i = cursor;
            ArrayList.this.add(i, e);
            cursor = i + 1;
            lastRet = -1;
        }
    }


    public List<E> subList(int fromIndex, int toIndex) {
        return UnknownIntrinsics.kexUnknown();
    }


    @Override
    public void forEach(Consumer<? super E> action) {
        AssertIntrinsics.kexNotNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked") final E[] elementData = (E[]) this.elementData;
        final int size = this.size;
        CollectionIntrinsics.forEach(0, size, index -> {
            action.accept(elementData[index]);
        });
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return UnknownIntrinsics.kexUnknown();
    }
}
