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

import org.jetbrains.research.kex.intrinsics.AssertIntrinsics;
import org.jetbrains.research.kex.intrinsics.CollectionIntrinsics;
import org.jetbrains.research.kex.intrinsics.UnknownIntrinsics;
import org.jetbrains.research.kex.intrinsics.ObjectIntrinsics;

import java.util.*;
import java.util.function.Consumer;

/**
 * Resizable-array implementation of the <tt>List</tt> interface.  Implements
 * all optional list operations, and permits all elements, including
 * <tt>null</tt>.  In addition to implementing the <tt>List</tt> interface,
 * this class provides methods to manipulate the size of the array that is
 * used internally to store the list.  (This class is roughly equivalent to
 * <tt>Vector</tt>, except that it is unsynchronized.)
 *
 * <p>The <tt>size</tt>, <tt>isEmpty</tt>, <tt>get</tt>, <tt>set</tt>,
 * <tt>iterator</tt>, and <tt>listIterator</tt> operations run in constant
 * time.  The <tt>add</tt> operation runs in <i>amortized constant time</i>,
 * that is, adding n elements requires O(n) time.  All of the other operations
 * run in linear time (roughly speaking).  The constant factor is low compared
 * to that for the <tt>LinkedList</tt> implementation.
 *
 * <p>Each <tt>ArrayList</tt> instance has a <i>capacity</i>.  The capacity is
 * the size of the array used to store the elements in the list.  It is always
 * at least as large as the list size.  As elements are added to an ArrayList,
 * its capacity grows automatically.  The details of the growth policy are not
 * specified beyond the fact that adding an element has constant amortized
 * time cost.
 *
 * <p>An application can increase the capacity of an <tt>ArrayList</tt> instance
 * before adding a large number of elements using the <tt>ensureCapacity</tt>
 * operation.  This may reduce the amount of incremental reallocation.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access an <tt>ArrayList</tt> instance concurrently,
 * and at least one of the threads modifies the list structurally, it
 * <i>must</i> be synchronized externally.  (A structural modification is
 * any operation that adds or deletes one or more elements, or explicitly
 * resizes the backing array; merely setting the value of an element is not
 * a structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the list.
 *
 * If no such object exists, the list should be "wrapped" using the
 * {@link Collections#synchronizedList Collections.synchronizedList}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the list:<pre>
 *   List list = Collections.synchronizedList(new ArrayList(...));</pre>
 *
 * <p><a name="fail-fast">
 * The iterators returned by this class's {@link #iterator() iterator} and
 * {@link #listIterator(int) listIterator} methods are <em>fail-fast</em>:</a>
 * if the list is structurally modified at any time after the iterator is
 * created, in any way except through the iterator's own
 * {@link ListIterator#remove() remove} or
 * {@link ListIterator#add(Object) add} methods, the iterator will throw a
 * {@link ConcurrentModificationException}.  Thus, in the face of
 * concurrent modification, the iterator fails quickly and cleanly, rather
 * than risking arbitrary, non-deterministic behavior at an undetermined
 * time in the future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:  <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Collection
 * @see     List
 * @see     LinkedList
 * @see     Vector
 * @since   1.2
 */
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    Object[] elementData;

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
        AssertIntrinsics.kexNotNull(elementData);
        return elementData.length;
    }

    @Override
    public boolean isEmpty() {
        AssertIntrinsics.kexNotNull(elementData);
        return elementData.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        AssertIntrinsics.kexNotNull(elementData);
        return CollectionIntrinsics.contains(elementData, o);
    }

    @Override
    public int indexOf(Object o) {
        AssertIntrinsics.kexNotNull(elementData);
        if (CollectionIntrinsics.contains(elementData, o)) {
            int result = UnknownIntrinsics.kexUnknownInt();
            AssertIntrinsics.kexAssume(result >= 0);
            AssertIntrinsics.kexAssume(result < elementData.length);
            if (o == null) {
                AssertIntrinsics.kexAssert(elementData[result] == null);
            } else {
                AssertIntrinsics.kexAssert(o.equals(elementData[result]));
            }
            return result;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        AssertIntrinsics.kexNotNull(elementData);
        if (CollectionIntrinsics.contains(elementData, o)) {
            int result = UnknownIntrinsics.kexUnknownInt();
            AssertIntrinsics.kexAssume(result >= 0);
            AssertIntrinsics.kexAssume(result < elementData.length);
            if (o == null) {
                AssertIntrinsics.kexAssert(elementData[result] == null);
            } else {
                AssertIntrinsics.kexAssert(o.equals(elementData[result]));
            }
            return result;
        }
        return -1;
    }

    @Override
    public Object clone() {
        ArrayList<?> v = new ArrayList<>();
        AssertIntrinsics.kexNotNull(elementData);
        v.elementData = Arrays.copyOf(elementData, elementData.length);
        v.modCount = 0;
        return v;
    }

    @Override
    public Object[] toArray() {
        AssertIntrinsics.kexNotNull(elementData);
        return Arrays.copyOf(elementData, elementData.length);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        AssertIntrinsics.kexNotNull(elementData);
        return (T[]) Arrays.copyOf(elementData, elementData.length, a.getClass());
    }

    private void rangeCheck(int index) {
        AssertIntrinsics.kexAssume(index < elementData.length);
    }

    private void rangeCheckForAdd(int index) {
        AssertIntrinsics.kexAssume(index >= 0);
        AssertIntrinsics.kexAssume(index < elementData.length);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        AssertIntrinsics.kexNotNull(elementData);
        rangeCheck(index);
        return (E) elementData[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        AssertIntrinsics.kexNotNull(elementData);
        rangeCheck(index);

        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public boolean add(E e) {
        AssertIntrinsics.kexNotNull(elementData);
        int oldLength = elementData.length;
        elementData = CollectionIntrinsics.generateObjectArray(oldLength + 1, index -> {
            if (index < oldLength) return elementData[index];
            else return null;
        });
        elementData[oldLength] = e;
        return true;
    }

    @Override
    public void add(int index, E element) {
        AssertIntrinsics.kexNotNull(elementData);
        int oldLength = elementData.length;
        elementData = CollectionIntrinsics.generateObjectArray(oldLength + 1, i -> {
            if (i < index) return elementData[i];
            else if (i == index) return null;
            else return elementData[i - 1];
        });
        elementData[index] = element;
    }

    @Override
    public E remove(int index) {
        AssertIntrinsics.kexNotNull(elementData);
        rangeCheck(index);

        E oldValue = (E) elementData[index];

        int numMoved = elementData.length - index - 1;
        if (numMoved > 0)
            elementData = CollectionIntrinsics.generateObjectArray(elementData.length - 1, i -> {
                if (i < index) return elementData[i];
                else return elementData[i - 1];
            });
        return oldValue;
    }

    @Override
    public boolean remove(Object o) {
//        CollectionIntrinsics.forEach(0, size, index -> {
//            if (ObjectIntrinsics.equals(elementData[index], o)) {
//                fastRemove(index);
//            }
//        });
        return contains(o);
    }

    @Override
    public void clear() {
        AssertIntrinsics.kexNotNull(elementData);
        elementData = new Object[0];
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        AssertIntrinsics.kexNotNull(elementData);
        Object[] a = c.toArray();
        int numNew = a.length;
        int oldSize = elementData.length;
        elementData = CollectionIntrinsics.generateObjectArray(oldSize + numNew, i -> {
            if (i < oldSize) return elementData[i];
            return a[i - oldSize];
        });
        return numNew != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        AssertIntrinsics.kexNotNull(elementData);
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        int oldSize = elementData.length;

        int numMoved = oldSize - index;
        elementData = CollectionIntrinsics.generateObjectArray(oldSize + numNew, i -> {
            if (i < index) return elementData[i];
            else if (i < index + numMoved) return a[i - index];
            else if (i + numMoved < elementData.length) return elementData[i + numMoved];
            else return null;
        });
        return numNew != 0;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        AssertIntrinsics.kexNotNull(elementData);
        int numMoved = toIndex - fromIndex;
        elementData = CollectionIntrinsics.generateObjectArray(elementData.length - numMoved, i -> {
            if (i < fromIndex) return elementData[i];
            else if (i + numMoved < elementData.length) return elementData[i + numMoved];
            else return null;
        });
    }

    @Override
    public boolean removeAll(Collection<?> c) {
//        AssertIntrinsics.kexNotNull(c);
//        Object[] other = c.toArray();
//        CollectionIntrinsics.forEach(0, other.length, index -> {
//            if (CollectionIntrinsics.contains(elementData, other[index])) {
//                remove(other[index]);
//            }
//        });
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
//        AssertIntrinsics.kexNotNull(c);
//        Object[] other = c.toArray();
//        CollectionIntrinsics.forEach(0, other.length, index -> {
//            if (!CollectionIntrinsics.contains(elementData, other[index])) {
//                remove(other[index]);
//            }
//        });
        return true;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > elementData.length)
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

    class Itr implements Iterator<E> {
        int cursor = 0;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            AssertIntrinsics.kexNotNull(elementData);
            return cursor < elementData.length;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            int i = cursor;
            AssertIntrinsics.kexNotNull(elementData);
            Object[] elementData = ArrayList.this.elementData;
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void remove() {
            AssertIntrinsics.kexNotNull(elementData);
            if (lastRet < 0)
                throw new IllegalStateException();

            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    class ListItr extends ArrayList<E>.Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            AssertIntrinsics.kexNotNull(elementData);
            return cursor != 0;
        }

        @Override
        public int nextIndex() {
            AssertIntrinsics.kexNotNull(elementData);
            return cursor;
        }

        @Override
        public int previousIndex() {
            AssertIntrinsics.kexNotNull(elementData);
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            AssertIntrinsics.kexNotNull(elementData);
            Object[] elementData = ArrayList.this.elementData;
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void set(E e) {
            AssertIntrinsics.kexNotNull(elementData);
            if (lastRet < 0) throw new IllegalStateException();
            ArrayList.this.set(lastRet, e);
        }

        @Override
        public void add(E e) {
            AssertIntrinsics.kexNotNull(elementData);
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
//        AssertIntrinsics.kexNotNull(action);
//        final int expectedModCount = modCount;
//        @SuppressWarnings("unchecked") final E[] elementData = (E[]) this.elementData;
//        final int size = this.size;
//        CollectionIntrinsics.forEach(0, size, index -> {
//            action.accept(elementData[index]);
//        });
//        if (modCount != expectedModCount) {
//            throw new ConcurrentModificationException();
//        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return UnknownIntrinsics.kexUnknown();
    }
}
