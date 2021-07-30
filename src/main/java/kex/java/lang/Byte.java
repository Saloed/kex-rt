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

public class Byte extends Number implements Comparable<Byte> {
    public static final int SIZE = 8;
    public static final int BYTES = SIZE / java.lang.Byte.SIZE;
    public static final byte MIN_VALUE = -128;
    public static final byte MAX_VALUE = 127;

    private final byte value;

    /**
     * static methods
     */
    public static String toString(byte b) {
        return ObjectIntrinsics.int2String((int)b, 10);
    }

    public static Byte valueOf(byte b) {
        return new Byte(b);
    }

    public static byte parseByte(String s, int radix) throws NumberFormatException {
        int i = Integer.parseInt(s, radix);
        if (i < MIN_VALUE || i > MAX_VALUE)
            throw new NumberFormatException("Value out of range. Value:\"" + s + "\" Radix:" + radix);
        return (byte)i;
    }
    public static byte parseByte(String s) throws NumberFormatException {
        return parseByte(s, 10);
    }
    public static Byte valueOf(String s, int radix) throws NumberFormatException {
        return valueOf(parseByte(s, radix));
    }
    public static Byte valueOf(String s) throws NumberFormatException {
        return valueOf(s, 10);
    }
    public static Byte decode(String nm) throws NumberFormatException {
        int i = Integer.decode(nm).intValue();
        if (i < MIN_VALUE || i > MAX_VALUE)
            throw new NumberFormatException(
                    "Value " + i + " out of range from input " + nm);
        return valueOf((byte)i);
    }
    public static int hashCode(byte value) {
        return (int)value;
    }
    public static int compare(byte x, byte y) {
        return x - y;
    }
    public static int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
    }
    public static long toUnsignedLong(byte x) {
        return ((long) x) & 0xffL;
    }

    /**
     * non-static methods
     */
    public Byte(byte value) {
        this.value = value;
    }

    public Byte(String s) throws NumberFormatException {
        this.value = parseByte(s, 10);
    }

    @Override
    public byte byteValue() {
        return value;
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    @Override
    public String toString() {
        return Integer.toString((int)value);
    }

    @Override
    public int hashCode() {
        return java.lang.Byte.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        return ObjectIntrinsics.equals(this, obj);
    }

    @Override
    public int compareTo(Byte anotherByte) {
        return compare(this.value, anotherByte.value);
    }
}
