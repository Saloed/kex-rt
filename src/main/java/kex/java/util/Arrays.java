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

// todo: contracts for: fill, setAll
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

    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }

    // todo copies
    public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        return CollectionIntrinsics.generateObjectArray(Math.max(original.length, newLength), index -> {
            return (T) original[index];
        });
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

    private Arrays() {
    }
}
