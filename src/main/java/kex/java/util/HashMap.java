package kex.java.util;
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

import org.jetbrains.annotations.NotNull;
import org.vorpal.research.kex.intrinsics.AssertIntrinsics;
import org.vorpal.research.kex.intrinsics.UnknownIntrinsics;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Hash table based implementation of the <tt>Map</tt> interface.  This
 * implementation provides all of the optional map operations, and permits
 * <tt>null</tt> values and the <tt>null</tt> key.  (The <tt>HashMap</tt>
 * class is roughly equivalent to <tt>Hashtable</tt>, except that it is
 * unsynchronized and permits nulls.)  This class makes no guarantees as to
 * the order of the map; in particular, it does not guarantee that the order
 * will remain constant over time.
 *
 * <p>This implementation provides constant-time performance for the basic
 * operations (<tt>get</tt> and <tt>put</tt>), assuming the hash function
 * disperses the elements properly among the buckets.  Iteration over
 * collection views requires time proportional to the "capacity" of the
 * <tt>HashMap</tt> instance (the number of buckets) plus its size (the number
 * of key-value mappings).  Thus, it's very important not to set the initial
 * capacity too high (or the load factor too low) if iteration performance is
 * important.
 *
 * <p>An instance of <tt>HashMap</tt> has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>.  The
 * <i>capacity</i> is the number of buckets in the hash table, and the initial
 * capacity is simply the capacity at the time the hash table is created.  The
 * <i>load factor</i> is a measure of how full the hash table is allowed to
 * get before its capacity is automatically increased.  When the number of
 * entries in the hash table exceeds the product of the load factor and the
 * current capacity, the hash table is <i>rehashed</i> (that is, internal data
 * structures are rebuilt) so that the hash table has approximately twice the
 * number of buckets.
 *
 * <p>As a general rule, the default load factor (.75) offers a good
 * tradeoff between time and space costs.  Higher values decrease the
 * space overhead but increase the lookup cost (reflected in most of
 * the operations of the <tt>HashMap</tt> class, including
 * <tt>get</tt> and <tt>put</tt>).  The expected number of entries in
 * the map and its load factor should be taken into account when
 * setting its initial capacity, so as to minimize the number of
 * rehash operations.  If the initial capacity is greater than the
 * maximum number of entries divided by the load factor, no rehash
 * operations will ever occur.
 *
 * <p>If many mappings are to be stored in a <tt>HashMap</tt>
 * instance, creating it with a sufficiently large capacity will allow
 * the mappings to be stored more efficiently than letting it perform
 * automatic rehashing as needed to grow the table.  Note that using
 * many keys with the same {@code hashCode()} is a sure way to slow
 * down performance of any hash table. To ameliorate impact, when keys
 * are {@link Comparable}, this class may use comparison order among
 * keys to help break ties.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a hash map concurrently, and at least one of
 * the threads modifies the map structurally, it <i>must</i> be
 * synchronized externally.  (A structural modification is any operation
 * that adds or deletes one or more mappings; merely changing the value
 * associated with a key that an instance already contains is not a
 * structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the map.
 * <p>
 * If no such object exists, the map should be "wrapped" using the
 * {@link Collections#synchronizedMap Collections.synchronizedMap}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the map:<pre>
 *   Map m = Collections.synchronizedMap(new HashMap(...));</pre>
 *
 * <p>The iterators returned by all of this class's "collection view methods"
 * are <i>fail-fast</i>: if the map is structurally modified at any time after
 * the iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a
 * {@link ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the
 * future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Doug Lea
 * @author Josh Bloch
 * @author Arthur van Hoff
 * @author Neal Gafter
 * @see Object#hashCode()
 * @see Collection
 * @see Map
 * @see TreeMap
 * @see Hashtable
 * @since 1.2
 */
public class HashMap<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {

    private static final long serialVersionUID = 362498820763181265L;
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int TREEIFY_THRESHOLD = 8;
    static final int UNTREEIFY_THRESHOLD = 6;
    static final int MIN_TREEIFY_CAPACITY = 64;

    static class Node<K, V> implements Map.Entry<K, V> {
        final K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }

    ArrayList<K> keys;
    ArrayList<V> values;

    public HashMap(int initialCapacity, float loadFactor) {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap() {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public HashMap(Map<? extends K, ? extends V> m) {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
        putMapEntries(m, false);
    }

    /**
     * Implements Map.putAll and Map constructor
     *
     * @param m     the map
     * @param evict false when initially constructing this map, else
     *              true (relayed to method afterNodeInsertion).
     */
    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        // TODO()
    }

    protected void contracts() {
        AssertIntrinsics.kexNotNull(keys);
        AssertIntrinsics.kexNotNull(values);
        AssertIntrinsics.kexAssume(keys.size() == values.size());
    }

    public int size() {
        contracts();
        return keys.size();
    }

    public boolean isEmpty() {
        contracts();
        return keys.isEmpty();
    }

    public V get(Object key) {
        contracts();
        int index = keys.indexOf(key);
        if (index == -1) return null;
        else return values.get(index);
    }

    public boolean containsKey(Object key) {
        contracts();
        return keys.contains(key);
    }

    public V put(K key, V value) {
        contracts();
        int index = keys.indexOf(key);
        if (index >= 0) {
            return values.set(index, value);
        } else {
            keys.add(key);
            values.add(value);
            return value;
        }
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        putMapEntries(m, true);
    }

    public V remove(Object key) {
        contracts();
        int index = keys.indexOf(key);
        if (index >= 0) {
            V retval = values.get(index);
            keys.remove(index);
            values.remove(index);
            return retval;
        } else {
            return null;
        }
    }

    public void clear() {
        contracts();
        keys.clear();
        values.clear();
    }

    public boolean containsValue(Object value) {
        contracts();
        return values.contains(value);
    }

    public Set<K> keySet() {
        return new KeySet();
    }

    final class KeySet extends AbstractSet<K> {
        public final int size() {
            return HashMap.this.size();
        }

        public final void clear() {
            HashMap.this.clear();
        }

        @NotNull
        public final Iterator<K> iterator() {
            return new KeyIterator();
        }

        public final boolean contains(Object o) {
            return containsKey(o);
        }

        public final boolean remove(Object key) {
            return HashMap.this.remove(key) != null;
        }

        public final Spliterator<K> spliterator() {
            HashMap.this.contracts();
            return UnknownIntrinsics.kexUnknown();
        }

        public final void forEach(Consumer<? super K> action) {
            HashMap.this.contracts();
        }
    }

    public Collection<V> values() {
        return new Values();
    }

    final class Values extends AbstractCollection<V> {
        public final int size() {
            return HashMap.this.size();
        }

        public final void clear() {
            HashMap.this.clear();
        }

        @NotNull
        public final Iterator<V> iterator() {
            return new ValueIterator();
        }

        public final boolean contains(Object o) {
            return HashMap.this.containsValue(o);
        }

        public final Spliterator<V> spliterator() {
            HashMap.this.contracts();
            return UnknownIntrinsics.kexUnknown();
        }

        public final void forEach(Consumer<? super V> action) {
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        public final int size() {
            return HashMap.this.size();
        }

        public final void clear() {
            HashMap.this.clear();
        }

        @NotNull
        public final Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Object key = e.getKey();
            return HashMap.this.containsKey(key) && HashMap.this.get(key).equals(e.getValue());
        }

        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                HashMap.this.remove(key, value);
            }
            return false;
        }

        public final Spliterator<Map.Entry<K, V>> spliterator() {
            return UnknownIntrinsics.kexUnknown();
        }

        public final void forEach(Consumer<? super Map.Entry<K, V>> action) {}
    }

    // Overrides of JDK8 Map extension methods

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        contracts();
        int index = keys.indexOf(key);
        if (index >= 0) {
            return values.get(index);
        } else {
            return defaultValue;
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        contracts();
        int index = keys.indexOf(key);
        if (index >= 0) {
            return values.get(index);
        } else {
            return put(key, value);
        }
    }

    @Override
    public boolean remove(Object key, Object value) {
        contracts();
        int index = keys.indexOf(key);
        if (index >= 0) {
            V storedValue = values.get(index);
            if (storedValue.equals(value)) {
                keys.remove(index);
                values.remove(index);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        contracts();
        int index = keys.indexOf(key);
        if (index >= 0) {
            V storedValue = values.get(index);
            if (storedValue.equals(oldValue)) {
                values.set(index, newValue);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public V replace(K key, V value) {
        contracts();
        int index = keys.indexOf(key);
        if (index >= 0) {
            V old = values.get(index);
            values.set(index, value);
            return old;
        } else {
            return put(key, value);
        }
    }

    @Override
    public V computeIfAbsent(K key,
                             Function<? super K, ? extends V> mappingFunction) {
        return UnknownIntrinsics.kexUnknown();
    }

    public V computeIfPresent(K key,
                              BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return UnknownIntrinsics.kexUnknown();
    }

    @Override
    public V compute(K key,
                     BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return UnknownIntrinsics.kexUnknown();
    }

    @Override
    public V merge(K key, V value,
                   BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return UnknownIntrinsics.kexUnknown();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
    }

    /* ------------------------------------------------------------ */
    // Cloning and serialization

    /**
     * Returns a shallow copy of this <tt>HashMap</tt> instance: the keys and
     * values themselves are not cloned.
     *
     * @return a shallow copy of this map
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        HashMap<K, V> result = new HashMap<>();
        result.keys = (ArrayList<K>) this.keys.clone();
        result.values = (ArrayList<V>) this.values.clone();
        return result;
    }

    /* ------------------------------------------------------------ */
    // iterators

    abstract class HashIterator<T> implements Iterator<T> {
        int cursor = 0;
        int lastRet = -1;

        public final boolean hasNext() {
            HashMap.this.contracts();
            return cursor < HashMap.this.size();
        }

        public final void remove() {
            HashMap.this.contracts();
            if (lastRet < 0)
                throw new IllegalStateException();

            HashMap.this.remove(keys.get(lastRet));
            cursor = lastRet;
            lastRet = -1;
        }
    }

    final class KeyIterator extends HashIterator<K> {
        public final K next() {
            HashMap.this.contracts();
            return keys.get(cursor++);
        }
    }

    final class ValueIterator extends HashIterator<V> {
        public final V next() {
            HashMap.this.contracts();
            return values.get(cursor++);
        }
    }


    final class EntryIterator extends HashIterator<Map.Entry<K, V>> {
        public final Map.Entry<K, V> next() {
            HashMap.this.contracts();
            return new Node<>(keys.get(cursor), values.get(cursor++));
        }
    }
}
