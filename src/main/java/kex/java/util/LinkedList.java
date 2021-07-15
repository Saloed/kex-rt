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

import org.jetbrains.research.kex.UnknownIntrinsics;

import java.util.*;
import java.util.function.Consumer;

/**
 * this implementation is VERY different from original, we simplify it w.r.t. static analysis
 */
public class LinkedList<E>
        extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, Cloneable, java.io.Serializable
{
    private ArrayList<E> inner;

    public LinkedList() {
        inner = new ArrayList<>();
    }

    @Override
    public void addFirst(E e) {
        inner.add(0, e);
    }

    @Override
    public void addLast(E e) {
        inner.add(e);
    }

    @Override
    public boolean contains(Object o) {
        return inner.contains(o);
    }

    @Override
    public boolean offerFirst(E e) {
        inner.add(0, e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        inner.add(e);
        return true;
    }

    @Override
    public E removeFirst() {
        return inner.remove(0);
    }

    @Override
    public E removeLast() {
        return inner.remove(inner.size() - 1);
    }

    @Override
    public E pollFirst() {
        E res = inner.get(0);
        inner.remove(0);
        return res;
    }

    @Override
    public E pollLast() {
        E res = inner.get(inner.size() - 1);
        inner.remove(inner.size() - 1);
        return res;
    }

    @Override
    public E getFirst() {
        return inner.get(0);
    }

    @Override
    public E getLast() {
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
        return inner.remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
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
        return inner.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return inner.addAll(index, c);
    }

    @Override
    public void clear() {
        inner.clear();
    }

    @Override
    public E get(int index) {
        return inner.get(index);
    }

    @Override
    public E set(int index, E element) {
        return inner.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        inner.add(index, element);
    }

    @Override
    public E remove(int index) {
        return inner.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return inner.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return inner.indexOf(o);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return inner.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return inner.listIterator(i);
    }

    @Override
    public Object clone() {
        LinkedList<E> v = new LinkedList<>();
        v.inner = (ArrayList<E>) inner.clone();
        return v;
    }

    @Override
    public Object[] toArray() {
        return inner.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return inner.toArray(a);
    }

    @Override
    public Spliterator<E> spliterator() {
        return inner.spliterator();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return inner.subList(fromIndex, toIndex);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        inner.forEach(action);
    }
}
