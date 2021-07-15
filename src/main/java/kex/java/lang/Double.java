/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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
import kex.sun.misc.DoubleConsts;

public class Double extends Number implements Comparable<Double> {
    public static final double POSITIVE_INFINITY = 1.0 / 0.0;
    public static final double NEGATIVE_INFINITY = -1.0 / 0.0;
    public static final double NaN = 0.0d / 0.0;
    public static final double MAX_VALUE = 0x1.fffffffffffffP+1023;
    public static final double MIN_NORMAL = 0x1.0p-1022;
    public static final double MIN_VALUE = 0x0.0000000000001P-1022;
    public static final int MAX_EXPONENT = 1023;
    public static final int MIN_EXPONENT = -1022;
    public static final int SIZE = 64;
    public static final int BYTES = SIZE / Byte.SIZE;

    private final double value;

    /**
     * static methods
     */
    public static String toString(double d) {
        return ObjectIntrinsics.double2String(d);
    }
    public static Double valueOf(String s) throws NumberFormatException {
        return new Double(parseDouble(s));
    }
    public static Double valueOf(double d) {
        return new Double(d);
    }
    public static double parseDouble(String s) throws NumberFormatException {
        return ObjectIntrinsics.string2Double(s);
    }

    public static boolean isNaN(double v) {
        return (v != v);
    }
    public static boolean isInfinite(double v) {
        return (v == POSITIVE_INFINITY) || (v == NEGATIVE_INFINITY);
    }
    public static boolean isFinite(double d) {
        return Math.abs(d) <= DoubleConsts.MAX_VALUE;
    }
    public static double sum(double a, double b) {
        return a + b;
    }
    public static double max(double a, double b) {
        return Math.max(a, b);
    }
    public static double min(double a, double b) {
        return Math.min(a, b);
    }
    public static int compare(double d1, double d2) {
        if (d1 < d2)
            return -1;           // Neither val is NaN, thisVal is smaller
        if (d1 > d2)
            return 1;            // Neither val is NaN, thisVal is larger

        // Cannot use doubleToRawLongBits because of possibility of NaNs.
        long thisBits    = java.lang.Double.doubleToLongBits(d1);
        long anotherBits = java.lang.Double.doubleToLongBits(d2);

        return (thisBits == anotherBits ?  0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                        1));                          // (0.0, -0.0) or (NaN, !NaN)
    }

    /**
     * non-static methods
     */
    public Double(double value) {
        this.value = value;
    }
    public Double(String s) throws NumberFormatException {
        value = parseDouble(s);
    }

    public boolean isNaN() {
        return isNaN(value);
    }
    public boolean isInfinite() {
        return isInfinite(value);
    }

    @Override
    public String toString() {
        return toString(value);
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
        return (long)value;
    }

    @Override
    public float floatValue() {
        return (float)value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return java.lang.Double.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        return ObjectIntrinsics.equals(this, obj);
    }

    @Override
    public int compareTo(Double aDouble) {
        return compare(value, aDouble.value);
    }
}
