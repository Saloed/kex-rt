/*
 * Copyright (c) 1994, 2012, Oracle and/or its affiliates. All rights reserved.
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

package kex.java.lang;

import org.jetbrains.research.kex.ObjectIntrinsics;


public class Integer extends Number implements Comparable<Integer> {
    public static final int SIZE = 32;
    public static final int BYTES = SIZE / Byte.SIZE;
    public static final int MIN_VALUE = 0x80000000;
    public static final int MAX_VALUE = 0x7fffffff;

    private final int value;

    /**
     * static methods
     */
    public static String toString(int i, int radix) {
        return ObjectIntrinsics.int2String(i, radix);
    }

    public static String toUnsignedString(int i, int radix)throws NumberFormatException {
        return Long.toUnsignedString(toUnsignedLong(i), radix);
    }

    public static String toHexString(int i) throws NumberFormatException {
        return ObjectIntrinsics.int2String(i, 16);
    }

    public static String toOctalString(int i) throws NumberFormatException {
        return ObjectIntrinsics.int2String(i, 8);
    }

    public static String toBinaryString(int i) throws NumberFormatException {
        return ObjectIntrinsics.int2String(i, 2);
    }

    public static String toString(int i) throws NumberFormatException {
        return ObjectIntrinsics.int2String(i, 10);
    }

    public static String toUnsignedString(int i)throws NumberFormatException {
        return Long.toString(toUnsignedLong(i));
    }

    public static int parseInt(String s, int radix) throws NumberFormatException {
        return ObjectIntrinsics.string2Int(s, radix);
    }

    public static int parseInt(String s) throws NumberFormatException {
        return parseInt(s, 10);
    }

    public static int parseUnsignedInt(String s, int radix) throws NumberFormatException {
        return ObjectIntrinsics.string2UnsignedInt(s, radix);
    }

    public static int parseUnsignedInt(String s) throws NumberFormatException {
        return parseUnsignedInt(s, 10);
    }

    public static Integer valueOf(String s, int radix) throws NumberFormatException {
        return new Integer(ObjectIntrinsics.string2Int(s, radix));
    }

    public static Integer valueOf(String s) throws NumberFormatException {
        return valueOf(s, 10);
    }

    public static Integer valueOf(int i) {
        return new Integer(i);
    }

    public static int hashCode(int value) {
        return value;
    }

    public static Integer decode(String nm) throws NumberFormatException {
        return new Integer(ObjectIntrinsics.decodeString2Int(nm));
    }

    /**
     * copied implementations from JDK
     */
    public static int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
    public static int compareUnsigned(int x, int y) {
        return compare(x + MIN_VALUE, y + MIN_VALUE);
    }
    public static long toUnsignedLong(int x) {
        return ((long) x) & 0xffffffffL;
    }
    public static int divideUnsigned(int dividend, int divisor) {
        return (int)(toUnsignedLong(dividend) / toUnsignedLong(divisor));
    }
    public static int remainderUnsigned(int dividend, int divisor) {
        return (int)(toUnsignedLong(dividend) % toUnsignedLong(divisor));
    }
    public static int highestOneBit(int i) {
        i |= (i >>  1);
        i |= (i >>  2);
        i |= (i >>  4);
        i |= (i >>  8);
        i |= (i >> 16);
        return i - (i >>> 1);
    }
    public static int lowestOneBit(int i) {
        return i & -i;
    }
    public static int numberOfLeadingZeros(int i) {
        if (i == 0)
            return 32;
        int n = 1;
        if (i >>> 16 == 0) { n += 16; i <<= 16; }
        if (i >>> 24 == 0) { n +=  8; i <<=  8; }
        if (i >>> 28 == 0) { n +=  4; i <<=  4; }
        if (i >>> 30 == 0) { n +=  2; i <<=  2; }
        n -= i >>> 31;
        return n;
    }
    public static int numberOfTrailingZeros(int i) {
        int y;
        if (i == 0) return 32;
        int n = 31;
        y = i <<16; if (y != 0) { n = n -16; i = y; }
        y = i << 8; if (y != 0) { n = n - 8; i = y; }
        y = i << 4; if (y != 0) { n = n - 4; i = y; }
        y = i << 2; if (y != 0) { n = n - 2; i = y; }
        return n - ((i << 1) >>> 31);
    }
    public static int bitCount(int i) {
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        return i & 0x3f;
    }
    public static int rotateLeft(int i, int distance) {
        return (i << distance) | (i >>> -distance);
    }
    public static int rotateRight(int i, int distance) {
        return (i >>> distance) | (i << -distance);
    }
    public static int reverse(int i) {
        i = (i & 0x55555555) << 1 | (i >>> 1) & 0x55555555;
        i = (i & 0x33333333) << 2 | (i >>> 2) & 0x33333333;
        i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f;
        i = (i << 24) | ((i & 0xff00) << 8) |
                ((i >>> 8) & 0xff00) | (i >>> 24);
        return i;
    }
    public static int signum(int i) {
        return (i >> 31) | (-i >>> 31);
    }
    public static int reverseBytes(int i) {
        return ((i >>> 24)           ) |
                ((i >>   8) &   0xFF00) |
                ((i <<   8) & 0xFF0000) |
                ((i << 24));
    }
    public static int sum(int a, int b) {
        return a + b;
    }
    public static int max(int a, int b) {
        return Math.max(a, b);
    }
    public static int min(int a, int b) {
        return Math.min(a, b);
    }

    /**
     * non-static methods
     */
    public Integer(int value) {
        this.value = value;
    }

    public Integer(String s) throws NumberFormatException {
        this.value = parseInt(s, 10);
    }

    @Override
    public byte byteValue() {
        return (byte)value;
    }

    @Override
    public short shortValue() {
        return (short)value;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return (long)value;
    }

    @Override
    public float floatValue() {
        return (float)value;
    }

    @Override
    public double doubleValue() {
        return (double)value;
    }

    @Override
    public String toString() {
        return toString(value);
    }

    @Override
    public int hashCode() {
        return hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        return ObjectIntrinsics.equals(this, obj);
    }

    @Override
    public int compareTo(Integer anotherInteger) {
        return compare(this.value, anotherInteger.value);
    }

}
