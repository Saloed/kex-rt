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
import org.jetbrains.research.kex.intrinsics.ObjectIntrinsics;

import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;

public class Arrays {
    private static final int MIN_ARRAY_SORT_GRAN = 1 << 13;

    public static boolean equals(long[] a, long[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static boolean equals(int[] a, int[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static boolean equals(short[] a, short[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static boolean equals(char[] a, char[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static boolean equals(byte[] a, byte[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static boolean equals(boolean[] a, boolean[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static boolean equals(double[] a, double[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static boolean equals(float[] a, float[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static boolean equals(Object[] a, Object[] a2) {
        return ObjectIntrinsics.equals(a, a2);
    }

    public static void fill(long[] a, long val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(long[] a, int fromIndex, int toIndex, long val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex, index -> a[index] = val);
    }

    public static void fill(int[] a, int val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(int[] a, int fromIndex, int toIndex, int val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex , index -> a[index] = val);
    }

    public static void fill(short[] a, short val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(short[] a, int fromIndex, int toIndex, short val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex, index -> a[index] = val);
    }

    public static void fill(char[] a, char val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(char[] a, int fromIndex, int toIndex, char val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex, index -> a[index] = val);
    }

    public static void fill(byte[] a, byte val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(byte[] a, int fromIndex, int toIndex, byte val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex, index -> a[index] = val);
    }

    public static void fill(boolean[] a, boolean val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(boolean[] a, int fromIndex, int toIndex, boolean val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex, index -> a[index] = val);
    }

    public static void fill(double[] a, double val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(double[] a, int fromIndex, int toIndex, double val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex, index -> a[index] = val);
    }

    public static void fill(float[] a, float val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(float[] a, int fromIndex, int toIndex, float val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex, index -> a[index] = val);
    }

    public static void fill(Object[] a, Object val) {
        CollectionIntrinsics.forEach(0, a.length, index -> a[index] = val);
    }

    public static void fill(Object[] a, int fromIndex, int toIndex, Object val) {
        CollectionIntrinsics.forEach(fromIndex, toIndex, index -> a[index] = val);
    }

    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }

    // todo copies
    public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        T[] copy = (T[]) new Object[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = (T) original[index]);
        return copy;
    }

    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = original[index]);
        return copy;
    }

    public static short[] copyOf(short[] original, int newLength) {
        short[] copy = new short[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = original[index]);
        return copy;
    }

    public static int[] copyOf(int[] original, int newLength) {
        int[] copy = new int[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = original[index]);
        return copy;
    }

    public static long[] copyOf(long[] original, int newLength) {
        long[] copy = new long[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = original[index]);
        return copy;
    }

    public static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = original[index]);
        return copy;
    }

    public static float[] copyOf(float[] original, int newLength) {
        float[] copy = new float[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = original[index]);
        return copy;
    }

    public static double[] copyOf(double[] original, int newLength) {
        double[] copy = new double[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = original[index]);
        return copy;
    }

    public static boolean[] copyOf(boolean[] original, int newLength) {
        boolean[] copy = new boolean[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length, newLength), index -> copy[index] = original[index]);
        return copy;
    }


    @SuppressWarnings("unchecked")
    public static <T> T[] copyOfRange(T[] original, int from, int to) {
        return copyOfRange(original, from, to, (Class<? extends T[]>) original.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <T, U> T[] copyOfRange(U[] original, int from, int to, Class<? extends T[]> newType) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        T[] copy = (T[]) new Object[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = (T) original[from + index]);
        return copy;
    }

    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        byte[] copy = new byte[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = original[from + index]);
        return copy;
    }

    public static short[] copyOfRange(short[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        short[] copy = new short[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = original[from + index]);
        return copy;
    }

    public static int[] copyOfRange(int[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        int[] copy = new int[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = original[from + index]);
        return copy;
    }

    public static long[] copyOfRange(long[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        long[] copy = new long[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = original[from + index]);
        return copy;
    }

    public static char[] copyOfRange(char[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        char[] copy = new char[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = original[from + index]);
        return copy;
    }

    public static float[] copyOfRange(float[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        float[] copy = new float[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = original[from + index]);
        return copy;
    }

    public static double[] copyOfRange(double[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        double[] copy = new double[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = original[from + index]);
        return copy;
    }

    public static boolean[] copyOfRange(boolean[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        boolean[] copy = new boolean[newLength];
        CollectionIntrinsics.forEach(0, Math.max(original.length - from, newLength), index -> copy[index] = original[from + index]);
        return copy;
    }


    public static <T> void setAll(T[] array, IntFunction<? extends T> generator) {
        AssertIntrinsics.kexNotNull(array);
        CollectionIntrinsics.forEach(0, array.length, index -> array[index] = generator.apply(index));
    }

    public static <T> void parallelSetAll(T[] array, IntFunction<? extends T> generator) {
        AssertIntrinsics.kexNotNull(array);
        CollectionIntrinsics.forEach(0, array.length, index -> array[index] = generator.apply(index));
    }

    public static void setAll(int[] array, IntUnaryOperator generator) {
        AssertIntrinsics.kexNotNull(array);
        CollectionIntrinsics.forEach(0, array.length, index -> array[index] = generator.applyAsInt(index));
    }

    public static void parallelSetAll(int[] array, IntUnaryOperator generator) {
        AssertIntrinsics.kexNotNull(array);
        CollectionIntrinsics.forEach(0, array.length, index -> array[index] = generator.applyAsInt(index));
    }

    public static void setAll(long[] array, IntToLongFunction generator) {
        AssertIntrinsics.kexNotNull(array);
        CollectionIntrinsics.forEach(0, array.length, index -> array[index] = generator.applyAsLong(index));
    }

    public static void parallelSetAll(long[] array, IntToLongFunction generator) {
        AssertIntrinsics.kexNotNull(array);
        CollectionIntrinsics.forEach(0, array.length, index -> array[index] = generator.applyAsLong(index));
    }

    public static void setAll(double[] array, IntToDoubleFunction generator) {
        AssertIntrinsics.kexNotNull(array);
        CollectionIntrinsics.forEach(0, array.length, index -> array[index] = generator.applyAsDouble(index));
    }
    public static void parallelSetAll(double[] array, IntToDoubleFunction generator) {
        AssertIntrinsics.kexNotNull(array);
        CollectionIntrinsics.forEach(0, array.length - 1, index -> array[index] = generator.applyAsDouble(index));
    }

    private Arrays() {
    }
}
