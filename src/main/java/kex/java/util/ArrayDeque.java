/*
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

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Josh Bloch of Google Inc. and released to the public domain,
 * as explained at http://creativecommons.org/publicdomain/zero/1.0/.
 */

package kex.java.util;

import org.jetbrains.research.kex.intrinsics.AssertIntrinsics;
import org.jetbrains.research.kex.intrinsics.UnknownIntrinsics;

import java.io.Serializable;
import java.util.*;
/**
 * Resizable-array implementation of the {@link Deque} interface.  Array
 * deques have no capacity restrictions; they grow as necessary to support
 * usage.  They are not thread-safe; in the absence of external
 * synchronization, they do not support concurrent access by multiple threads.
 * Null elements are prohibited.  This class is likely to be faster than
 * {@link Stack} when used as a stack, and faster than {@link LinkedList}
 * when used as a queue.
 *
 * <p>Most {@code ArrayDeque} operations run in amortized constant time.
 * Exceptions include {@link #remove(Object) remove}, {@link
 * #removeFirstOccurrence removeFirstOccurrence}, {@link #removeLastOccurrence
 * removeLastOccurrence}, {@link #contains contains}, {@link #iterator
 * iterator.remove()}, and the bulk operations, all of which run in linear
 * time.
 *
 * <p>The iterators returned by this class's {@code iterator} method are
 * <i>fail-fast</i>: If the deque is modified at any time after the iterator
 * is created, in any way except through the iterator's own {@code remove}
 * method, the iterator will generally throw a {@link
 * ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the
 * future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class and its iterator implement all of the
 * <em>optional</em> methods of the {@link Collection} and {@link
 * Iterator} interfaces.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch and Doug Lea
 * @since   1.6
 * @param <E> the type of elements held in this collection
 */
public class ArrayDeque<E>
        extends AbstractCollection<E>
        implements Deque<E>, Cloneable, Serializable {
    private static final int MIN_INITIAL_CAPACITY = 8;

    private ArrayList<E> inner;

    public ArrayDeque() {
        inner = new ArrayList<>();
    }

    public ArrayDeque(int numElements) {
        inner = new ArrayList<>(numElements);
    }

    public ArrayDeque(Collection<? extends E> c) {
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
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
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
        return removeFirst();
    }

    @Override
    public E pollLast() {
        return removeLast();
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
        return getFirst();
    }

    @Override
    public E peekLast() {
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return UnknownIntrinsics.kexUnknownBoolean();
    }

    @Override
    public boolean add(E e) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.add(e);
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
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
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
    public boolean isEmpty() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.iterator();
    }

    @Override
    public Iterator<E> descendingIterator() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.iterator();
    }

    @Override
    public boolean contains(Object o) {
        AssertIntrinsics.kexNotNull(inner);
        return inner.contains(o);
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public void clear() {
        AssertIntrinsics.kexNotNull(inner);
        inner.clear();
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
    public ArrayDeque<E> clone() {
        AssertIntrinsics.kexNotNull(inner);
        ArrayDeque<E> v = new ArrayDeque<>();
        v.inner = (ArrayList<E>) inner.clone();
        return v;
    }

    @Override
    public Spliterator<E> spliterator() {
        AssertIntrinsics.kexNotNull(inner);
        return inner.spliterator();
    }
}
