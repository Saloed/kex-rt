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
import org.jetbrains.research.kex.intrinsics.UnknownIntrinsics;

import java.util.*;
import java.util.function.Consumer;

/**
 * Doubly-linked list implementation of the {@code List} and {@code Deque}
 * interfaces.  Implements all optional list operations, and permits all
 * elements (including {@code null}).
 *
 * <p>All of the operations perform as could be expected for a doubly-linked
 * list.  Operations that index into the list will traverse the list from
 * the beginning or the end, whichever is closer to the specified index.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a linked list concurrently, and at least
 * one of the threads modifies the list structurally, it <i>must</i> be
 * synchronized externally.  (A structural modification is any operation
 * that adds or deletes one or more elements; merely setting the value of
 * an element is not a structural modification.)  This is typically
 * accomplished by synchronizing on some object that naturally
 * encapsulates the list.
 *
 * If no such object exists, the list should be "wrapped" using the
 * {@link Collections#synchronizedList Collections.synchronizedList}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the list:<pre>
 *   List list = Collections.synchronizedList(new LinkedList(...));</pre>
 *
 * <p>The iterators returned by this class's {@code iterator} and
 * {@code listIterator} methods are <i>fail-fast</i>: if the list is
 * structurally modified at any time after the iterator is created, in
 * any way except through the Iterator's own {@code remove} or
 * {@code add} methods, the iterator will throw a {@link
 * ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than
 * risking arbitrary, non-deterministic behavior at an undetermined
 * time in the future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:   <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @see     List
 * @see     ArrayList
 * @since 1.2
 * @param <E> the type of elements held in this collection
 */
public class LinkedList<E>
        extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, Cloneable, java.io.Serializable
{
    private ArrayList<E> inner;

    public LinkedList() {
        inner = new ArrayList<>();
    }

    public LinkedList(Collection<? extends E> c) {
        inner = new ArrayList<>(c);
    }

    @Override
    public void addFirst(E e) {
        AssertIntrinsics.kexNotNull(inner);
        inner.add(0, e);
    }

    @Override
    public void addLast(E e) {
        AssertIntrinsics.kexNotNull(inner);
        inner.add(e);
    }

    @Override
    public boolean contains(Object o) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.contains(o);
    }

    @Override
    public boolean offerFirst(E e) {
        AssertIntrinsics.kexNotNull(inner);
        inner.add(0, e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        AssertIntrinsics.kexNotNull(inner);
        inner.add(e);
        return true;
    }

    @Override
    public E removeFirst() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.remove(0);
    }

    @Override
    public E removeLast() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.remove(inner.size() - 1);
    }

    @Override
    public E pollFirst() {
        AssertIntrinsics.kexNotNull(inner);
        E res = inner.get(0);
        inner.remove(0);
        return res;
    }

    @Override
    public E pollLast() {
        AssertIntrinsics.kexNotNull(inner);
        E res = inner.get(inner.size() - 1);
        inner.remove(inner.size() - 1);
        return res;
    }

    @Override
    public E getFirst() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.get(0);
    }

    @Override
    public E getLast() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.get(inner.size() - 1);
    }

    @Override
    public E peekFirst() {
        AssertIntrinsics.kexNotNull(inner);
        return getFirst();
    }

    @Override
    public E peekLast() {
        AssertIntrinsics.kexNotNull(inner);
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        AssertIntrinsics.kexNotNull(inner);
        return UnknownIntrinsics.kexUnknownBoolean();
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return getFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.addAll(index, c);
    }

    @Override
    public void clear() {
        AssertIntrinsics.kexNotNull(inner);
        inner.clear();
    }

    @Override
    public E get(int index) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.get(index);
    }

    @Override
    public E set(int index, E element) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        AssertIntrinsics.kexNotNull(inner);
        inner.add(index, element);
    }

    @Override
    public E remove(int index) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.indexOf(o);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.size();
    }

    @Override
    public Iterator<E> descendingIterator() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.listIterator(i);
    }

    @Override
    public Object clone() {
        AssertIntrinsics.kexNotNull(inner);
        LinkedList<E> v = new LinkedList<>();
        v.inner = (ArrayList<E>) inner.clone();
        return v;
    }

    @Override
    public Object[] toArray() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.toArray(a);
    }

    @Override
    public Spliterator<E> spliterator() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.spliterator();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.subList(fromIndex, toIndex);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        AssertIntrinsics.kexNotNull(inner);
        inner.forEach(action);
    }
}
