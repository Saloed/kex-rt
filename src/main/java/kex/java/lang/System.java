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

import org.jetbrains.research.kex.intrinsics.AssertIntrinsics;
import org.jetbrains.research.kex.intrinsics.CollectionIntrinsics;
import org.jetbrains.research.kex.intrinsics.UnknownIntrinsics;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.channels.Channel;
import java.util.Properties;

public class System {
    private System() {
    }

    public final static InputStream in = null;

    public final static PrintStream out = null;

    public final static PrintStream err = null;

    private static volatile SecurityManager security = null;

    public static void setIn(InputStream in) {
    }

    public static void setOut(PrintStream out) {
    }

    public static void setErr(PrintStream err) {
    }

    private static volatile Console cons = UnknownIntrinsics.kexUnknown();

    public static Console console() {
        AssertIntrinsics.kexNotNull(cons);
        return cons;
    }

    public static Channel inheritedChannel() throws IOException {
        return UnknownIntrinsics.kexUnknown();
    }

    public static void setSecurityManager(final SecurityManager s) {
        security = s;
    }

    public static SecurityManager getSecurityManager() {
        return security;
    }

    public static long currentTimeMillis() {
        return UnknownIntrinsics.kexUnknownLong();
    }

    public static long nanoTime() {
        return UnknownIntrinsics.kexUnknownLong();
    }

    public static void arraycopy(Object src, int srcPos,
                                 Object dest, int destPos,
                                 int length) {
        CollectionIntrinsics.arrayCopy(src, srcPos, dest, destPos, length);
    }

    public static int identityHashCode(Object x) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    public static Properties getProperties() {
        return UnknownIntrinsics.kexUnknown();
    }

    private static String lineSeparator = "\n";

    public static String lineSeparator() {
        return lineSeparator;
    }

    public static void setProperties(Properties props) {
        //
    }

    public static String getProperty(String key) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static String getProperty(String key, String def) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static String setProperty(String key, String value) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static String clearProperty(String key) {
        return UnknownIntrinsics.kexUnknown();
    }

    public static String getenv(String name) {
        return UnknownIntrinsics.kexUnknown();
    }

    // todo
    public static java.util.Map<String, String> getenv() {
        return UnknownIntrinsics.kexUnknown();
    }

    public static void exit(int status) {

    }

    public static void gc() {

    }

    public static void runFinalization() {

    }

    public static void runFinalizersOnExit(boolean value) {

    }

    public static void load(String filename) {

    }

    public static void loadLibrary(String libname) {
    }

    public static String mapLibraryName(String libname) {
        return UnknownIntrinsics.kexUnknown();
    }

}
