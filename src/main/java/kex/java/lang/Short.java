/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

public class Short extends Number implements Comparable<Short> {
    public static final int SIZE = 16;
    public static final int BYTES = SIZE / Byte.SIZE;
    public static final short MIN_VALUE = -32768;
    public static final short MAX_VALUE = 32767;

    private final short value;

    /**
     * static methods
     */
    public static String toString(short s) {
        return Integer.toString((int) s, 10);
    }
    public static short parseShort(String s, int radix) throws NumberFormatException {
        int i = Integer.parseInt(s, radix);
        if (i < MIN_VALUE || i > MAX_VALUE)
            throw new NumberFormatException("Value out of range. Value:\"" + s + "\" Radix:" + radix);
        return (short) i;
    }
    public static short parseShort(String s) throws NumberFormatException {
        return parseShort(s, 10);
    }
    public static Short valueOf(String s, int radix) throws NumberFormatException {
        return valueOf(parseShort(s, radix));
    }
    public static Short valueOf(String s) throws NumberFormatException {
        return valueOf(s, 10);
    }
    public static Short valueOf(short s) {
        return new Short(s);
    }
    public static Short decode(String nm) throws NumberFormatException {
        int i = Integer.decode(nm).intValue();
        if (i < MIN_VALUE || i > MAX_VALUE)
            throw new NumberFormatException(
                    "Value " + i + " out of range from input " + nm);
        return valueOf((short)i);
    }
    public static int hashCode(short value) {
        return (int)value;
    }
    public static int compare(short x, short y) {
        return x - y;
    }
    public static short reverseBytes(short i) {
        return (short) (((i & 0xFF00) >> 8) | (i << 8));
    }
    public static int toUnsignedInt(short x) {
        return ((int) x) & 0xffff;
    }
    public static long toUnsignedLong(short x) {
        return ((long) x) & 0xffffL;
    }

    /**
     * non-static methods
     */
    public Short(short value) {
        this.value = value;
    }
    public Short(String s) throws NumberFormatException {
        this.value = parseShort(s, 10);
    }

    @Override
    public byte byteValue() {
        return (byte)value;
    }

    @Override
    public short shortValue() {
        return value;
    }

    @Override
    public int intValue() {
        return (int)value;
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
        return Integer.toString((int)value);
    }

    @Override
    public int hashCode() {
        return Short.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        return ObjectIntrinsics.equals(this, obj);
    }

    @Override
    public int compareTo(Short anotherShort) {
        return compare(this.value, anotherShort.value);
    }
}
