/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package kex.sun.misc;

public class FloatConsts {
    private FloatConsts() {
    }

    public static final float POSITIVE_INFINITY = java.lang.Float.POSITIVE_INFINITY;
    public static final float NEGATIVE_INFINITY = java.lang.Float.NEGATIVE_INFINITY;
    public static final float NaN = java.lang.Float.NaN;
    public static final float MAX_VALUE = java.lang.Float.MAX_VALUE;
    public static final float MIN_VALUE = java.lang.Float.MIN_VALUE;
    public static final float MIN_NORMAL = 1.17549435E-38f;
    public static final int SIGNIFICAND_WIDTH = 24;
    public static final int MAX_EXPONENT = 127;
    public static final int MIN_EXPONENT = -126;
    public static final int MIN_SUB_EXPONENT = MIN_EXPONENT - (SIGNIFICAND_WIDTH - 1);
    public static final int EXP_BIAS = 127;
    public static final int SIGN_BIT_MASK = 0x80000000;
    public static final int EXP_BIT_MASK = 0x7F800000;
    public static final int SIGNIF_BIT_MASK = 0x007FFFFF;
}

