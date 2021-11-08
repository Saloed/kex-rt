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
import org.jetbrains.research.kex.intrinsics.UnknownIntrinsics;

import java.lang.reflect.Array;
import java.util.*;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;
import java.util.function.*;
import java.util.stream.*;

/**
 * This class contains various methods for manipulating arrays (such as
 * sorting and searching). This class also contains a static factory
 * that allows arrays to be viewed as lists.
 *
 * <p>The methods in this class all throw a {@code NullPointerException},
 * if the specified array reference is null, except where noted.
 *
 * <p>The documentation for the methods contained in this class includes
 * briefs description of the <i>implementations</i>. Such descriptions should
 * be regarded as <i>implementation notes</i>, rather than parts of the
 * <i>specification</i>. Implementors should feel free to substitute other
 * algorithms, so long as the specification itself is adhered to. (For
 * example, the algorithm used by {@code sort(Object[])} does not have to be
 * a MergeSort, but it does have to be <i>stable</i>.)
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @author John Rose
 * @since  1.2
 */
public class Arrays {
    private static final int MIN_ARRAY_SORT_GRAN = 1 << 13;

    static final class NaturalOrder implements Comparator<Object> {
        @SuppressWarnings("unchecked")
        public int compare(Object first, Object second) {
            return ((Comparable<Object>) first).compareTo(second);
        }

        static final NaturalOrder INSTANCE = new NaturalOrder();
    }

    public static void sort(int[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(int[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(long[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(long[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(short[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(short[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(char[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(char[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(byte[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(byte[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(float[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(float[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(double[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void sort(double[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(byte[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(byte[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(char[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(char[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(short[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(short[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(int[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(int[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(long[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(long[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(float[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(float[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(double[] a) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length - 1, index -> a[index] <= a[index + 1])
        );
    }

    public static void parallelSort(double[] a, int fromIndex, int toIndex) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex - 1, index -> a[index] <= a[index + 1])
        );
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> void parallelSort(T[] a) {
        // nothing
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> void parallelSort(T[] a, int fromIndex, int toIndex) {
        // nothing
    }

    @SuppressWarnings("unchecked")
    public static <T> void parallelSort(T[] a, Comparator<? super T> cmp) {
        // nothing
    }

    @SuppressWarnings("unchecked")
    public static <T> void parallelSort(T[] a, int fromIndex, int toIndex, Comparator<? super T> cmp) {
        // nothing
    }

    public static void sort(Object[] a) {
        // nothing
    }

    public static void sort(Object[] a, int fromIndex, int toIndex) {
        // nothing
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        // nothing
    }

    public static <T> void sort(T[] a, int fromIndex, int toIndex,
                                Comparator<? super T> c) {
        // nothing
    }

    public static <T> void parallelPrefix(T[] array, BinaryOperator<T> op) {
        // nothing
    }

    public static <T> void parallelPrefix(T[] array, int fromIndex,
                                          int toIndex, BinaryOperator<T> op) {
        // nothing
    }

    public static void parallelPrefix(long[] array, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(long[] array, int fromIndex,
                                      int toIndex, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(int[] array, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(int[] array, int fromIndex,
                                      int toIndex, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(short[] array, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(short[] array, int fromIndex,
                                      int toIndex, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(byte[] array, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(byte[] array, int fromIndex,
                                      int toIndex, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(char[] array, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(char[] array, int fromIndex,
                                      int toIndex, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(float[] array, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(float[] array, int fromIndex,
                                      int toIndex, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(double[] array, LongBinaryOperator op) {
        // nothing
    }

    public static void parallelPrefix(double[] array, int fromIndex,
                                      int toIndex, LongBinaryOperator op) {
        // nothing
    }

    public static int binarySearch(long[] a, long key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(long[] a, int fromIndex, int toIndex, long key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(int[] a, int key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(int[] a, int fromIndex, int toIndex, int key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(short[] a, short key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(short[] a, int fromIndex, int toIndex, short key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(byte[] a, byte key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(byte[] a, int fromIndex, int toIndex, byte key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(char[] a, char key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(char[] a, int fromIndex, int toIndex, char key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(float[] a, float key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(float[] a, int fromIndex, int toIndex, float key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(double[] a, double key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(double[] a, int fromIndex, int toIndex, double key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(Object[] a, Object key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static int binarySearch(Object[] a, int fromIndex, int toIndex, Object key) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static <T> int binarySearch(T[] a, T key, Comparator<? super T> c) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }

    public static <T> int binarySearch(T[] a, int fromIndex, int toIndex, T key, Comparator<? super T> c) {
        int res = UnknownIntrinsics.kexUnknownInt();
        AssertIntrinsics.kexAssume(res >= -1 && res < a.length);
        return res;
    }


    public static boolean equals(long[] a, long[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> a[index] == a2[index]);
    }

    public static boolean equals(int[] a, int[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> a[index] == a2[index]);
    }

    public static boolean equals(short[] a, short[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> a[index] == a2[index]);
    }

    public static boolean equals(char[] a, char[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> a[index] == a2[index]);
    }

    public static boolean equals(byte[] a, byte[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> a[index] == a2[index]);
    }

    public static boolean equals(boolean[] a, boolean[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> a[index] == a2[index]);
    }

    public static boolean equals(double[] a, double[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> a[index] == a2[index]);
    }

    public static boolean equals(float[] a, float[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> a[index] == a2[index]);
    }

    public static boolean equals(Object[] a, Object[] a2) {
        if (a.length != a2.length) return false;
        return CollectionIntrinsics.forAll(0, a.length, index -> ObjectIntrinsics.equals(a[index], a2[index]));
    }

    public static void fill(long[] a, long val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> a[index] == val)
        );
    }

    public static void fill(long[] a, int fromIndex, int toIndex, long val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> a[index] == val)
        );
    }

    public static void fill(int[] a, int val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> a[index] == val)
        );
    }

    public static void fill(int[] a, int fromIndex, int toIndex, int val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> a[index] == val)
        );
    }

    public static void fill(short[] a, short val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> a[index] == val)
        );
    }

    public static void fill(short[] a, int fromIndex, int toIndex, short val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> a[index] == val)
        );
    }

    public static void fill(char[] a, char val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> a[index] == val)
        );
    }

    public static void fill(char[] a, int fromIndex, int toIndex, char val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> a[index] == val)
        );
    }

    public static void fill(byte[] a, byte val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> a[index] == val)
        );
    }

    public static void fill(byte[] a, int fromIndex, int toIndex, byte val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> a[index] == val)
        );
    }

    public static void fill(boolean[] a, boolean val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> a[index] == val)
        );
    }

    public static void fill(boolean[] a, int fromIndex, int toIndex, boolean val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> a[index] == val)
        );
    }

    public static void fill(float[] a, float val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> a[index] == val)
        );
    }

    public static void fill(float[] a, int fromIndex, int toIndex, float val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> a[index] == val)
        );
    }

    public static void fill(double[] a, double val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> a[index] == val)
        );
    }

    public static void fill(double[] a, int fromIndex, int toIndex, double val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> a[index] == val)
        );
    }

    public static void fill(Object[] a, Object val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(0, a.length, index -> ObjectIntrinsics.equals(a[index], val))
        );
    }

    public static void fill(Object[] a, int fromIndex, int toIndex, Object val) {
        AssertIntrinsics.kexAssume(
                CollectionIntrinsics.forAll(fromIndex, toIndex, index -> ObjectIntrinsics.equals(a[index], val))
        );
    }


    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }

    // todo copies
    public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        return CollectionIntrinsics.generateObjectArray(Math.max(original.length, newLength), index -> (T) original[index]);
    }

    public static byte[] copyOf(byte[] original, int newLength) {
        return CollectionIntrinsics.generateByteArray(Math.max(original.length, newLength), index -> {
            return original[index];
        });
    }

    public static short[] copyOf(short[] original, int newLength) {
        return CollectionIntrinsics.generateShortArray(Math.max(original.length, newLength), index -> {
            return original[index];
        });
    }

    public static int[] copyOf(int[] original, int newLength) {
        return CollectionIntrinsics.generateIntArray(Math.max(original.length, newLength), index -> {
            return original[index];
        });
    }

    public static long[] copyOf(long[] original, int newLength) {
        return CollectionIntrinsics.generateLongArray(Math.max(original.length, newLength), index -> {
            return original[index];
        });
    }

    public static char[] copyOf(char[] original, int newLength) {
        return CollectionIntrinsics.generateCharArray(Math.max(original.length, newLength), index -> {
            return original[index];
        });
    }

    public static float[] copyOf(float[] original, int newLength) {
        return CollectionIntrinsics.generateFloatArray(Math.max(original.length, newLength), index -> {
            return original[index];
        });
    }

    public static double[] copyOf(double[] original, int newLength) {
        return CollectionIntrinsics.generateDoubleArray(Math.max(original.length, newLength), index -> {
            return original[index];
        });
    }

    public static boolean[] copyOf(boolean[] original, int newLength) {
        return CollectionIntrinsics.generateBoolArray(Math.max(original.length, newLength), index -> {
            return original[index];
        });
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
        return CollectionIntrinsics.generateObjectArray(newLength, index -> {
            return (T) original[from + index];
        });
    }

    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        return CollectionIntrinsics.generateByteArray(newLength, index -> {
            return original[from + index];
        });
    }

    public static short[] copyOfRange(short[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        return CollectionIntrinsics.generateShortArray(newLength, index -> {
            return original[from + index];
        });
    }

    public static int[] copyOfRange(int[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        return CollectionIntrinsics.generateIntArray(newLength, index -> {
            return original[from + index];
        });
    }

    public static long[] copyOfRange(long[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        return CollectionIntrinsics.generateLongArray(newLength, index -> {
            return original[from + index];
        });
    }

    public static char[] copyOfRange(char[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        return CollectionIntrinsics.generateCharArray(newLength, index -> {
            return original[from + index];
        });
    }

    public static float[] copyOfRange(float[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        return CollectionIntrinsics.generateFloatArray(newLength, index -> {
            return original[from + index];
        });
    }

    public static double[] copyOfRange(double[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        return CollectionIntrinsics.generateDoubleArray(newLength, index -> {
            return original[from + index];
        });
    }

    public static boolean[] copyOfRange(boolean[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        return CollectionIntrinsics.generateBoolArray(newLength, index -> {
            return original[from + index];
        });
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> List<T> asList(T... a) {
        ArrayList<T> res = new ArrayList<T>();
        res.elementData = a;
        return res;
    }

    public static int hashCode(long a[]) {
        return 0;
    }

    public static int hashCode(int a[]) {
        return 0;
    }

    public static int hashCode(short a[]) {
        return 0;
    }

    public static int hashCode(char a[]) {
        return 0;
    }

    public static int hashCode(byte a[]) {
        return 0;
    }

    public static int hashCode(float a[]) {
        return 0;
    }

    public static int hashCode(double a[]) {
        return 0;
    }

    public static int hashCode(boolean a[]) {
        return 0;
    }

    public static int hashCode(Object a[]) {
        return 0;
    }

    public static int deepHashCode(Object a[]) {
        return 0;
    }

    public static boolean deepEquals(Object[] a1, Object[] a2) {
        if (a1 == a2)
            return true;
        if (a1 == null || a2==null)
            return false;
        int length = a1.length;
        if (a2.length != length)
            return false;

        return CollectionIntrinsics.forAll(0, length, index -> {

            Object e1 = a1[index];
            Object e2 = a2[index];

            if (e1 == e2)
                return true;
            if (e1 == null)
                return false;

            // Figure out whether the two elements are equal
            boolean eq = deepEquals0(e1, e2);

            return eq;
        });
    }

    static boolean deepEquals0(Object e1, Object e2) {
        assert e1 != null;
        boolean eq;
        if (e1 instanceof Object[] && e2 instanceof Object[])
            eq = deepEquals ((Object[]) e1, (Object[]) e2);
        else if (e1 instanceof byte[] && e2 instanceof byte[])
            eq = equals((byte[]) e1, (byte[]) e2);
        else if (e1 instanceof short[] && e2 instanceof short[])
            eq = equals((short[]) e1, (short[]) e2);
        else if (e1 instanceof int[] && e2 instanceof int[])
            eq = equals((int[]) e1, (int[]) e2);
        else if (e1 instanceof long[] && e2 instanceof long[])
            eq = equals((long[]) e1, (long[]) e2);
        else if (e1 instanceof char[] && e2 instanceof char[])
            eq = equals((char[]) e1, (char[]) e2);
        else if (e1 instanceof float[] && e2 instanceof float[])
            eq = equals((float[]) e1, (float[]) e2);
        else if (e1 instanceof double[] && e2 instanceof double[])
            eq = equals((double[]) e1, (double[]) e2);
        else if (e1 instanceof boolean[] && e2 instanceof boolean[])
            eq = equals((boolean[]) e1, (boolean[]) e2);
        else
            eq = e1.equals(e2);
        return eq;
    }

    public static String toString(long[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String toString(int[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String toString(short[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String toString(char[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String toString(byte[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String toString(boolean[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String toString(float[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String toString(double[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String toString(Object[] a) {
        return ObjectIntrinsics.any2String(a);
    }

    public static String deepToString(Object[] a) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static <T> void setAll(T[] array, IntFunction<? extends T> generator) {
        // nothing
    }

    public static <T> void parallelSetAll(T[] array, IntFunction<? extends T> generator) {
        // nothing
    }

    public static void setAll(int[] array, IntUnaryOperator generator) {
        // nothing
    }

    public static void setAll(long[] array, IntToLongFunction generator) {
        // nothing
    }

    public static void parallelSetAll(long[] array, IntToLongFunction generator) {
        // nothing
    }

    public static void setAll(double[] array, IntToDoubleFunction generator) {
        // nothing
    }

    public static void parallelSetAll(double[] array, IntToDoubleFunction generator) {
        // nothing
    }

    public static <T> Spliterator<T> spliterator(T[] array) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static <T> Spliterator<T> spliterator(T[] array, int startInclusive, int endExclusive) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static Spliterator.OfInt spliterator(int[] array) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static Spliterator.OfInt spliterator(int[] array, int startInclusive, int endExclusive) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static Spliterator.OfLong spliterator(long[] array) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static Spliterator.OfLong spliterator(long[] array, int startInclusive, int endExclusive) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static Spliterator.OfDouble spliterator(double[] array) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static Spliterator.OfDouble spliterator(double[] array, int startInclusive, int endExclusive) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static <T> Stream<T> stream(T[] array) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static <T> Stream<T> stream(T[] array, int startInclusive, int endExclusive) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static IntStream stream(int[] array) {
        return stream(array, 0, array.length);
    }

    public static IntStream stream(int[] array, int startInclusive, int endExclusive) {
        return StreamSupport.intStream(spliterator(array, startInclusive, endExclusive), false);
    }

    public static LongStream stream(long[] array) {
        return stream(array, 0, array.length);
    }

    public static LongStream stream(long[] array, int startInclusive, int endExclusive) {
        return StreamSupport.longStream(spliterator(array, startInclusive, endExclusive), false);
    }

    public static DoubleStream stream(double[] array) {
        return stream(array, 0, array.length);
    }

    public static DoubleStream stream(double[] array, int startInclusive, int endExclusive) {
        return StreamSupport.doubleStream(spliterator(array, startInclusive, endExclusive), false);
    }


    private Arrays() {
    }
}
