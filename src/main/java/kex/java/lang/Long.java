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

import org.jetbrains.research.kex.intrinsics.ObjectIntrinsics;

public class Long extends Number implements Comparable<Long> {
    public static final int SIZE = 64;
    public static final int BYTES = SIZE / Byte.SIZE;
    public static final long MIN_VALUE = 0x8000000000000000L;
    public static final long MAX_VALUE = 0x7fffffffffffffffL;

    private final long value;

    /**
     * static methods
     */
    public static String toString(long i, int radix) {
        return ObjectIntrinsics.long2String(i, radix);
    }

    public static String toUnsignedString(long i, int radix) {
        return ObjectIntrinsics.long2UnsignedString(i, radix);
    }

    public static String toHexString(long i) {
        return ObjectIntrinsics.long2String(i, 16);
    }

    public static String toOctalString(long i) {
        return ObjectIntrinsics.long2String(i, 8);
    }

    public static String toBinaryString(long i) {
        return ObjectIntrinsics.long2String(i, 2);
    }

    public static String toString(long i) {
        return ObjectIntrinsics.long2String(i, 10);
    }

    public static String toUnsignedString(long i) {
        return toUnsignedString(i, 10);
    }

    public static long parseLong(String s, int radix) throws NumberFormatException {
        return ObjectIntrinsics.string2Long(s, radix);
    }

    public static long parseLong(String s) throws NumberFormatException {
        return parseLong(s, 10);
    }

    public static long parseUnsignedLong(String s, int radix) throws NumberFormatException {
        return ObjectIntrinsics.string2UnsignedLong(s, radix);
    }

    public static long parseUnsignedLong(String s) throws NumberFormatException {
        return parseUnsignedLong(s, 10);
    }

    public static Long valueOf(String s, int radix) throws NumberFormatException {
        return Long.valueOf(parseLong(s, radix));
    }

    public static Long valueOf(String s) throws NumberFormatException {
        return Long.valueOf(parseLong(s, 10));
    }

    public static Long valueOf(long l) {
        return new Long(l);
    }

    public static Long decode(String nm) throws NumberFormatException {
        return valueOf(ObjectIntrinsics.decodeString2Long(nm));
    }

    public static int hashCode(long value) {
        return (int)(value ^ (value >>> 32));
    }

    /**
     * copied implementations from JDK
     */
    public static int compare(long x, long y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
    public static int compareUnsigned(long x, long y) {
        return compare(x + MIN_VALUE, y + MIN_VALUE);
    }

    public static long highestOneBit(long i) {
        // HD, Figure 3-1
        i |= (i >>  1);
        i |= (i >>  2);
        i |= (i >>  4);
        i |= (i >>  8);
        i |= (i >> 16);
        i |= (i >> 32);
        return i - (i >>> 1);
    }
    public static long lowestOneBit(long i) {
        return i & -i;
    }
    public static int numberOfLeadingZeros(long i) {
        if (i == 0)
            return 64;
        int n = 1;
        int x = (int)(i >>> 32);
        if (x == 0) { n += 32; x = (int)i; }
        if (x >>> 16 == 0) { n += 16; x <<= 16; }
        if (x >>> 24 == 0) { n +=  8; x <<=  8; }
        if (x >>> 28 == 0) { n +=  4; x <<=  4; }
        if (x >>> 30 == 0) { n +=  2; x <<=  2; }
        n -= x >>> 31;
        return n;
    }
    public static int numberOfTrailingZeros(long i) {
        int x, y;
        if (i == 0) return 64;
        int n = 63;
        y = (int)i; if (y != 0) { n = n -32; x = y; } else x = (int)(i>>>32);
        y = x <<16; if (y != 0) { n = n -16; x = y; }
        y = x << 8; if (y != 0) { n = n - 8; x = y; }
        y = x << 4; if (y != 0) { n = n - 4; x = y; }
        y = x << 2; if (y != 0) { n = n - 2; x = y; }
        return n - ((x << 1) >>> 31);
    }
    public static int bitCount(long i) {
        i = i - ((i >>> 1) & 0x5555555555555555L);
        i = (i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
        i = (i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        i = i + (i >>> 32);
        return (int)i & 0x7f;
    }
    public static long rotateLeft(long i, int distance) {
        return (i << distance) | (i >>> -distance);
    }
    public static long rotateRight(long i, int distance) {
        return (i >>> distance) | (i << -distance);
    }
    public static long reverse(long i) {
        i = (i & 0x5555555555555555L) << 1 | (i >>> 1) & 0x5555555555555555L;
        i = (i & 0x3333333333333333L) << 2 | (i >>> 2) & 0x3333333333333333L;
        i = (i & 0x0f0f0f0f0f0f0f0fL) << 4 | (i >>> 4) & 0x0f0f0f0f0f0f0f0fL;
        i = (i & 0x00ff00ff00ff00ffL) << 8 | (i >>> 8) & 0x00ff00ff00ff00ffL;
        i = (i << 48) | ((i & 0xffff0000L) << 16) |
                ((i >>> 16) & 0xffff0000L) | (i >>> 48);
        return i;
    }
    public static int signum(long i) {
        return (int) ((i >> 63) | (-i >>> 63));
    }
    public static long reverseBytes(long i) {
        i = (i & 0x00ff00ff00ff00ffL) << 8 | (i >>> 8) & 0x00ff00ff00ff00ffL;
        return (i << 48) | ((i & 0xffff0000L) << 16) |
                ((i >>> 16) & 0xffff0000L) | (i >>> 48);
    }
    public static long sum(long a, long b) {
        return a + b;
    }
    public static long max(long a, long b) {
        return Math.max(a, b);
    }
    public static long min(long a, long b) {
        return Math.min(a, b);
    }

    /**
     * non-static methods
     */
    public Long(long value) {
        this.value = value;
    }

    public Long(String s) throws NumberFormatException {
        this.value = parseLong(s, 10);
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
        return (int)value;
    }

    @Override
    public long longValue() {
        return value;
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
    public int compareTo(Long anotherLong) {
        return compare(this.value, anotherLong.value);
    }
}
