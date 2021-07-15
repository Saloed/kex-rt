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

import org.jetbrains.research.kex.UnknownIntrinsics;

import java.io.Serializable;
import java.util.*;

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

    public void addFirst(E e) {
        inner.add(0, e);
    }

    public void addLast(E e) {
        inner.add(e);
    }

    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    public E removeFirst() {
        return inner.remove(0);
    }

    public E removeLast() {
        return inner.remove(inner.size() - 1);
    }

    public E pollFirst() {
        return removeFirst();
    }

    public E pollLast() {
        return removeLast();
    }

    public E getFirst() {
        return inner.get(0);
    }

    public E getLast() {
        return inner.get(inner.size() - 1);
    }

    public E peekFirst() {
        return getFirst();
    }

    public E peekLast() {
        return getLast();
    }

    public boolean removeFirstOccurrence(Object o) {
        return inner.remove(o);
    }

    public boolean removeLastOccurrence(Object o) {
        return UnknownIntrinsics.kexUnknownBoolean();
    }

    public boolean add(E e) {
        return inner.add(e);
    }

    public boolean offer(E e) {
        return offerLast(e);
    }

    public E remove() {
        return removeFirst();
    }

    public E poll() {
        return pollFirst();
    }

    public E element() {
        return getFirst();
    }

    public E peek() {
        return peekFirst();
    }

    public void push(E e) {
        addFirst(e);
    }

    public E pop() {
        return removeFirst();
    }

    public int size() {
        return inner.size();
    }

    public boolean isEmpty() {
        return inner.isEmpty();
    }

    public Iterator<E> iterator() {
        return inner.iterator();
    }

    public Iterator<E> descendingIterator() {
        return inner.iterator();
    }

    public boolean contains(Object o) {
        return inner.contains(o);
    }

    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    public void clear() {
        inner.clear();
    }

    public Object[] toArray() {
        return inner.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return inner.toArray(a);
    }

    public ArrayDeque<E> clone() {
        ArrayDeque<E> v = new ArrayDeque<>();
        v.inner = (ArrayList<E>) inner.clone();
        return v;
    }

    public Spliterator<E> spliterator() {
        return inner.spliterator();
    }
}
