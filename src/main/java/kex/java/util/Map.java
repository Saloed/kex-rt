package kex.java.util;

import org.jetbrains.annotations.Nullable;
import org.vorpal.research.kex.intrinsics.AssertIntrinsics;

abstract class Map<K, V> implements java.util.Map<K, V> {
    private final java.util.List<K> keys = new java.util.ArrayList<K>();
    private final java.util.List<V> values = new java.util.ArrayList<V>();

    @Override
    public final int size() {
        AssertIntrinsics.kexNotNull(keys);
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        AssertIntrinsics.kexNotNull(keys);
        return keys.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        AssertIntrinsics.kexNotNull(keys);
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        AssertIntrinsics.kexNotNull(values);
        return values.contains(value);
    }

    @Override
    public V get(Object key) {
        AssertIntrinsics.kexNotNull(keys);
        AssertIntrinsics.kexNotNull(values);
        AssertIntrinsics.kexAssume(keys.size() == values.size());

        int index = keys.indexOf(key);
        if (index == -1) return null;
        else return values.get(index);
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        AssertIntrinsics.kexNotNull(keys);
        AssertIntrinsics.kexNotNull(values);
        AssertIntrinsics.kexAssume(keys.size() == values.size());

        int index = keys.indexOf(key);
        if (index >= 0) {
            return values.set(index, value);
        } else {
            keys.add(key);
            values.add(value);
            return value;
        }
    }

    @Override
    public V remove(Object key) {
        AssertIntrinsics.kexNotNull(keys);
        AssertIntrinsics.kexNotNull(values);
        AssertIntrinsics.kexAssume(keys.size() == values.size());

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

    @Override
    public void clear() {
        AssertIntrinsics.kexNotNull(keys);
        AssertIntrinsics.kexNotNull(values);
        keys.clear();
        values.clear();
    }
}
