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

import org.jetbrains.research.kex.CollectionIntrinsics;

import java.util.*;

public class HashSet<E>
        extends AbstractSet<E>
        implements Set<E>, Cloneable, java.io.Serializable {
    private ArrayList<E> inner;

    public HashSet() {
        inner = new ArrayList<>();
    }

    public HashSet(Collection<? extends E> c) {
        inner = new ArrayList<>();
        addAll(c);
    }

    public HashSet(int initialCapacity, float loadFactor) {
        inner = new ArrayList<>(initialCapacity);
    }

    public HashSet(int initialCapacity) {
        inner = new ArrayList<>(initialCapacity);
    }

    HashSet(int initialCapacity, float loadFactor, boolean dummy) {
        inner = new ArrayList<>(initialCapacity);
    }

    @Override
    public Iterator<E> iterator() {
        return inner.iterator();
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return inner.contains(o);
    }

    @Override
    public boolean add(E e) {
        if (!inner.contains(e)) {
            return inner.add(e);
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return inner.remove(o);
    }

    @Override
    public void clear() {
        inner.clear();
    }

    @Override
    public Object clone() {
        HashSet<E> v = new HashSet<>();
        v.inner = (ArrayList<E>) inner.clone();
        return v;
    }

    @Override
    public Spliterator<E> spliterator() {
        return inner.spliterator();
    }
}
