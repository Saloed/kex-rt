package kex.java.util;

import org.jetbrains.research.kex.intrinsics.AssertIntrinsics;
import org.jetbrains.research.kex.intrinsics.CollectionIntrinsics;
import org.jetbrains.research.kex.intrinsics.ObjectIntrinsics;
import org.jetbrains.research.kex.intrinsics.UnknownIntrinsics;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HashMap<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int TREEIFY_THRESHOLD = 8;
    static final int UNTREEIFY_THRESHOLD = 6;
    static final int MIN_TREEIFY_CAPACITY = 64;

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c;
            Type[] ts, as;
            ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (Type t : ts) {
                    if ((t instanceof ParameterizedType) &&
                            ((p = (ParameterizedType) t).getRawType() ==
                                    Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }

    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable) k).compareTo(x));
    }

    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static class Node<K, V> implements Map.Entry<K, V> {
        K key;
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

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

    Node<K, V>[] table;
    int size;

    public HashMap(int initialCapacity, float loadFactor) {
        this();
    }

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap() {
        int actualSize = UnknownIntrinsics.kexUnknownInt();
        table = new Node[actualSize];
    }

    public HashMap(Map<? extends K, ? extends V> m) {
        putAll(m);
    }

    private void ensureCapacityInternal(int minCapacity) {
        AssertIntrinsics.kexAssume(table.length > minCapacity);
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
    public V get(Object key) {
        return getNode(key).value;
    }

    final HashMap.Node<K, V> getNode(Object key) {
        Node<K, V> e = new Node<K, V>(null, null);
        CollectionIntrinsics.forEach(0, size, index -> {
            Node<K, V> t = table[index];
            if (ObjectIntrinsics.equals(t.key, key)) {
                e.key = t.key;
                e.value = t.value;
            }
        });
        return e;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key).key != null;
    }

    @Override
    public V put(K key, V value) {
        Node<K, V> e = new Node<K, V>(null, null);
        CollectionIntrinsics.forEach(0, size, index -> {
            Node<K, V> t = table[index];
            if (ObjectIntrinsics.equals(t.key, key)) {
                e.key = t.key;
                e.value = t.value;
                t.value = value;
            }
        });
        if (e.key == null) {
            ensureCapacityInternal(size);
            table[size] = new Node<>(key, value);
            ++size;
        }
        return e.value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Map.Entry<K, V>[] nodes = (Map.Entry<K, V>[]) m.entrySet().toArray();
        CollectionIntrinsics.forEach(0, nodes.length, index -> {
            put(nodes[index].getKey(), nodes[index].getValue());
        });
    }

    @Override
    public V remove(Object key) {
        Node<K, V> result = new Node<>(null, null);
        CollectionIntrinsics.forEach(0, size, index -> {
            if (ObjectIntrinsics.equals(table[index].key, key)) {
                Node<K, V> res = fastRemove(index);
                result.key = res.key;
                result.value = res.value;
            }
        });
        return result.value;
    }

    private Node<K, V> fastRemove(int index) {
        int numMoved = size - index - 1;
        Node<K, V> returned = table[index];
        if (numMoved > 0)
            CollectionIntrinsics.arrayCopy(table, index + 1, table, index, numMoved);
        table[--size] = null;
        return returned;
    }

    @Override
    public void clear() {
        CollectionIntrinsics.forEach(0, table.length, index -> {
            table[index] = null;
        });

        size = 0;
    }

    @Override
    public boolean containsValue(Object value) {
        Node<K, V> e = new Node<>(null, null);
        CollectionIntrinsics.forEach(0, size, index -> {
            Node<K, V> t = table[index];
            if (ObjectIntrinsics.equals(t.value, value)) {
                e.key = t.key;
                e.value = t.value;
            }
        });
        return e.value != null;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> ks = new HashSet<>();
        CollectionIntrinsics.forEach(0, size, index -> {
            ks.add(table[index].key);
        });
        return ks;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> vs = new ArrayList<>();
        CollectionIntrinsics.forEach(0, size, index -> {
            vs.add(table[index].value);
        });
        return vs;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        HashSet<Map.Entry<K, V>> ks = new HashSet<>();
        CollectionIntrinsics.forEach(0, size, index -> {
            ks.add(table[index]);
        });
        return ks;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        Node<K, V> e = getNode(key);
        return e.key == null ? defaultValue : e.value;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        Node<K, V> e = getNode(key);
        if (e.key == null) {
            put(key, value);
        }
        return e.value;
    }

    @Override
    public boolean remove(Object key, Object value) {
        Node<K, V> e = getNode(key);
        if (ObjectIntrinsics.equals(e.value, value)) {
            remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Node<K, V> e = getNode(key);
        if (ObjectIntrinsics.equals(e.value, oldValue)) {
            remove(key);
            put(key, newValue);
            return true;
        }
        return false;
    }

    @Override
    public V replace(K key, V value) {
        Node<K, V> e = getNode(key);
        if (e.value != null) {
            remove(key);
            put(key, value);
            return e.value;
        }
        return null;
    }

    @Override
    public V computeIfAbsent(K key,
                             Function<? super K, ? extends V> mappingFunction) {
        return UnknownIntrinsics.kexUnknown();
    }

    @Override
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
        CollectionIntrinsics.forEach(0, size, index -> {
            Node<K, V> n = table[index];
            action.accept(n.key, n.value);
        });
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        // nothing
    }

    @Override
    public Object clone() {
        HashMap<K, V> m = new HashMap<>();
        CollectionIntrinsics.arrayCopy(table, 0, m.table, 0, size);
        m.size = size;
        return m;
    }
}