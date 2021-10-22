/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import kex.java.util.Arrays;
import org.jetbrains.research.kex.intrinsics.AssertIntrinsics;
import org.jetbrains.research.kex.intrinsics.CollectionIntrinsics;
import org.jetbrains.research.kex.intrinsics.UnknownIntrinsics;
import org.jetbrains.research.kex.intrinsics.ObjectIntrinsics;

/**
 * A mutable sequence of characters.
 * <p>
 * Implements a modifiable string. At any point in time it contains some
 * particular sequence of characters, but the length and content of the
 * sequence can be changed through certain method calls.
 *
 * <p>Unless otherwise noted, passing a {@code null} argument to a constructor
 * or method in this class will cause a {@link NullPointerException} to be
 * thrown.
 * <p>
 *
 * @author Michael McCloskey
 * @author Martin Buchholz
 * @author Ulf Zibis
 * @since 1.5
 */
public abstract class AbstractStringBuilder implements Appendable, CharSequence {
    /**
     * The value is used for character storage.
     */
    char[] value;

    /**
     * The count is the number of characters used.
     */
    int count;

    /**
     * This no-arg constructor is necessary for serialization of subclasses.
     */
    AbstractStringBuilder() {
        this(512);
    }

    /**
     * Creates an AbstractStringBuilder
     *
     * @param capacity is not used, created array is assumed to be always long enough
     */
    AbstractStringBuilder(int capacity) {
        int actualCapacity = UnknownIntrinsics.kexUnknownInt();
        value = new char[actualCapacity];

    }

    /**
     * Returns the length (character count).
     *
     * @return the length of the sequence of characters currently
     * represented by this object
     */
    @Override
    public int length() {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        return count;
    }

    /**
     * Returns the current capacity. The capacity is the amount of storage
     * available for newly inserted characters, beyond which an allocation
     * will occur.
     *
     * @return the current capacity
     */
    public int capacity() {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        return value.length;
    }

    /**
     * Assumes that the capacity is greater than the specified minimum.
     *
     * @param minimumCapacity the minimum desired capacity.
     */
    public void ensureCapacity(int minimumCapacity) {
        AssertIntrinsics.kexAssume(value.length > minimumCapacity);
    }

    /**
     * This method has the same contract as ensureCapacity, but is
     * never synchronized.
     */
    private void ensureCapacityInternal(int minimumCapacity) {
        AssertIntrinsics.kexAssume(value.length > minimumCapacity);
    }

    /**
     * This implements the expansion semantics of ensureCapacity with no
     * size check or synchronization.
     * <p>
     * Never used directly and only needed for compatibility with default JDK
     */
    void expandCapacity(int minimumCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
        AssertIntrinsics.kexAssume(newCapacity > 0);
        value = Arrays.copyOf(value, newCapacity);
    }

    /**
     * Attempts to reduce storage used for the character sequence.
     * If the buffer is larger than necessary to hold its current sequence of
     * characters, then it may be resized to become more space efficient.
     * Calling this method may, but is not required to, affect the value
     * returned by a subsequent call to the {@link #capacity()} method.
     */
    public void trimToSize() {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }

    /**
     * Sets the length of the character sequence.
     * The sequence is changed to a new character sequence
     * whose length is specified by the argument. For every nonnegative
     * index <i>k</i> less than {@code newLength}, the character at
     * index <i>k</i> in the new character sequence is the same as the
     * character at index <i>k</i> in the old sequence if <i>k</i> is less
     * than the length of the old character sequence; otherwise, it is the
     * null character {@code '\u005C\u0000'}.
     * <p>
     * In other words, if the {@code newLength} argument is less than
     * the current length, the length is changed to the specified length.
     * <p>
     * If the {@code newLength} argument is greater than or equal
     * to the current length, sufficient null characters
     * ({@code '\u005Cu0000'}) are appended so that
     * length becomes the {@code newLength} argument.
     * <p>
     * It assumed that {@code newLength} argument is greater than or equal
     * to {@code 0}.
     *
     * @param newLength the new length
     */
    public void setLength(int newLength) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        AssertIntrinsics.kexAssume(newLength >= 0);
        ensureCapacityInternal(newLength);

        value = CollectionIntrinsics.generateCharArray(value.length, i -> {
            if (i < newLength) return value[i];
            else return '\u0000';
        });

        count = newLength;
    }

    /**
     * Returns the {@code char} value in this sequence at the specified index.
     * The first {@code char} value is at index {@code 0}, the next at index
     * {@code 1}, and so on, as in array indexing.
     * <p>
     * The index argument must be greater than or equal to
     * {@code 0}, and less than the length of this sequence.
     *
     * <p>If the {@code char} value specified by the index is a
     * <a href="Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     *
     * @param index the index of the desired {@code char} value.
     * @return the {@code char} value at the specified index.
     * @throws IndexOutOfBoundsException if {@code index} is
     *                                   negative or greater than or equal to {@code length()}.
     */
    @Override
    public char charAt(int index) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        return value[index];
    }

    /**
     * Code points are not supported, implementation is similar with {@code charAt}
     */
    public int codePointAt(int index) {
        return charAt(index);
    }

    /**
     * Code points are not supported, implementation is similar with {@code charAt(index - 1)}
     */
    public int codePointBefore(int index) {
        return charAt(index - 1);
    }

    /**
     * Code points are not supported, returns an unknown value
     */
    public int codePointCount(int beginIndex, int endIndex) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    /**
     * Code points are not supported, returns an unknown value
     */
    public int offsetByCodePoints(int index, int codePointOffset) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    /**
     * Characters are copied from this sequence into the
     * destination character array {@code dst}. The first character to
     * be copied is at index {@code srcBegin}; the last character to
     * be copied is at index {@code srcEnd-1}. The total number of
     * characters to be copied is {@code srcEnd-srcBegin}. The
     * characters are copied into the subarray of {@code dst} starting
     * at index {@code dstBegin} and ending at index:
     * <pre>{@code
     * dstbegin + (srcEnd-srcBegin) - 1
     * }</pre>
     *
     * @param srcBegin start copying at this offset.
     * @param srcEnd   stop copying at this offset.
     * @param dst      the array to copy the data into.
     * @param dstBegin offset into {@code dst}.
     * @throws IndexOutOfBoundsException if any of the following is true:
     *                                   <ul>
     *                                   <li>{@code srcBegin} is negative
     *                                   <li>{@code dstBegin} is negative
     *                                   <li>the {@code srcBegin} argument is greater than
     *                                   the {@code srcEnd} argument.
     *                                   <li>{@code srcEnd} is greater than
     *                                   {@code this.length()}.
     *                                   <li>{@code dstBegin+srcEnd-srcBegin} is greater than
     *                                   {@code dst.length}
     *                                   </ul>
     */
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (srcBegin < 0)
            throw new StringIndexOutOfBoundsException(srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
            throw new StringIndexOutOfBoundsException(srcEnd);
        if (srcBegin > srcEnd)
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
        int length = srcEnd - srcBegin;
        char[] finalDst = dst;
        char[] result = CollectionIntrinsics.generateCharArray(length, index -> {
            if (index < dstBegin) return finalDst[index];
            else return value[index + srcBegin];
        });
        AssertIntrinsics.kexAssume(dst == result);
    }

    /**
     * The character at the specified index is set to {@code ch}. This
     * sequence is altered to represent a new character sequence that is
     * identical to the old character sequence, except that it contains the
     * character {@code ch} at position {@code index}.
     * <p>
     * The index argument must be greater than or equal to
     * {@code 0}, and less than the length of this sequence.
     *
     * @param index the index of the character to modify.
     * @param ch    the new character.
     * @throws IndexOutOfBoundsException if {@code index} is
     *                                   negative or greater than or equal to {@code length()}.
     */
    public void setCharAt(int index, char ch) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        value[index] = ch;
    }

    /**
     * Appends the string representation of the {@code Object} argument.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the intrinsic {@link ObjectIntrinsics#any2String(Object)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param obj an {@code Object}.
     * @return a reference to this object.
     */
    public AbstractStringBuilder append(Object obj) {
        return append(ObjectIntrinsics.any2String(obj));
    }

    /**
     * Appends the specified string to this character sequence.
     * <p>
     * The characters of the {@code String} argument are appended, in
     * order, increasing the length of this sequence by the length of the
     * argument. If {@code str} is {@code null}, then the four
     * characters {@code "null"} are appended.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at
     * index <i>k</i> in the new character sequence is equal to the character
     * at index <i>k</i> in the old character sequence, if <i>k</i> is less
     * than <i>n</i>; otherwise, it is equal to the character at index
     * <i>k-n</i> in the argument {@code str}.
     *
     * @param str a string.
     * @return a reference to this object.
     */
    public AbstractStringBuilder append(String str) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (str == null)
            return appendNull();
        char[] strChars = str.toCharArray();
        int len = strChars.length;
        ensureCapacityInternal(count + len);
        value = CollectionIntrinsics.generateCharArray(value.length, index -> {
            if (index < count) return value[index];
            else if (index < count + len) return strChars[index - count];
            else return value[index];
        });
        count += len;
        return this;
    }

    // Documentation in subclasses because of synchro difference
    public AbstractStringBuilder append(StringBuffer sb) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (sb == null)
            return appendNull();
        char[] strChars = sb.toCharArray();
        int len = strChars.length;
        ensureCapacityInternal(count + len);
        value = CollectionIntrinsics.generateCharArray(value.length, index -> {
            if (index < count) return value[index];
            else if (index < count + len) return strChars[index - count];
            else return value[index];
        });
        count += len;
        return this;
    }

    /**
     * @since 1.8
     */
    AbstractStringBuilder append(AbstractStringBuilder asb) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (asb == null)
            return appendNull();
        char[] strChars = asb.toCharArray();
        int len = strChars.length;
        ensureCapacityInternal(count + len);
        value = CollectionIntrinsics.generateCharArray(value.length, index -> {
            if (index < count) return value[index];
            else if (index < count + len) return strChars[index - count];
            else return value[index];
        });
        count += len;
        return this;
    }

    // Documentation in subclasses because of synchro difference
    @Override
    public AbstractStringBuilder append(CharSequence s) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (s == null)
            return appendNull();
        if (s instanceof String)
            return this.append((String) s);
        if (s instanceof AbstractStringBuilder)
            return this.append((AbstractStringBuilder) s);

        return this.append(s, 0, s.length());
    }

    private AbstractStringBuilder appendNull() {
        int c = count;
        ensureCapacityInternal(c + 4);
        final char[] value = this.value;
        value[c++] = 'n';
        value[c++] = 'u';
        value[c++] = 'l';
        value[c++] = 'l';
        count = c;
        return this;
    }

    /**
     * Appends a subsequence of the specified {@code CharSequence} to this
     * sequence.
     * <p>
     * Characters of the argument {@code s}, starting at
     * index {@code start}, are appended, in order, to the contents of
     * this sequence up to the (exclusive) index {@code end}. The length
     * of this sequence is increased by the value of {@code end - start}.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at
     * index <i>k</i> in this character sequence becomes equal to the
     * character at index <i>k</i> in this sequence, if <i>k</i> is less than
     * <i>n</i>; otherwise, it is equal to the character at index
     * <i>k+start-n</i> in the argument {@code s}.
     * <p>
     * If {@code s} is {@code null}, then this method appends
     * characters as if the s parameter was a sequence containing the four
     * characters {@code "null"}.
     *
     * @param s     the sequence to append.
     * @param start the starting index of the subsequence to be appended.
     * @param end   the end index of the subsequence to be appended.
     * @return a reference to this object.
     * @throws IndexOutOfBoundsException if
     *                                   {@code start} is negative, or
     *                                   {@code start} is greater than {@code end} or
     *                                   {@code end} is greater than {@code s.length()}
     */
    @Override
    public AbstractStringBuilder append(CharSequence s, int start, int end) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (s == null)
            s = "null";
        if ((start < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException("start " + start + ", end " + end + ", s.length() " + s.length());
        int len = end - start;
        CharSequence finalS = s;
        value = CollectionIntrinsics.generateCharArray(value.length, index -> {
            if (index < start) return value[index];
            else if (index >= end) return value[index];
            else return finalS.charAt(index);
        });
        count += len;
        return this;
    }

    /**
     * Appends the string representation of the {@code char} array
     * argument to this sequence.
     * <p>
     * The characters of the array argument are appended, in order, to
     * the contents of this sequence. The length of this sequence
     * increases by the length of the argument.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(char[])},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param str the characters to be appended.
     * @return a reference to this object.
     */
    public AbstractStringBuilder append(char[] str) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        int len = str.length;
        ensureCapacityInternal(count + len);
        value = CollectionIntrinsics.generateCharArray(value.length, index -> {
            if (index < count) return value[index];
            else if (index >= (count + len)) return value[index];
            else return str[index];
        });
        count += len;
        return this;
    }

    /**
     * Appends the string representation of a subarray of the
     * {@code char} array argument to this sequence.
     * <p>
     * Characters of the {@code char} array {@code str}, starting at
     * index {@code offset}, are appended, in order, to the contents
     * of this sequence. The length of this sequence increases
     * by the value of {@code len}.
     * <p>
     * The overall effect is exactly as if the arguments were converted
     * to a string by the method {@link String#valueOf(char[], int, int)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param str    the characters to be appended.
     * @param offset the index of the first {@code char} to append.
     * @param len    the number of {@code char}s to append.
     * @return a reference to this object.
     * @throws IndexOutOfBoundsException if {@code offset < 0} or {@code len < 0}
     *                                   or {@code offset+len > str.length}
     */
    public AbstractStringBuilder append(char[] str, int offset, int len) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (len > 0)
            ensureCapacityInternal(count + len);
        value = CollectionIntrinsics.generateCharArray(value.length, index -> {
            if (index < count) return value[index];
            else if (index >= (count + len)) return value[index];
            else return str[index + offset];
        });
        count += len;
        return this;
    }

    /**
     * Appends the string representation of the {@code boolean}
     * argument to the sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(boolean)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param b a {@code boolean}.
     * @return a reference to this object.
     */
    public AbstractStringBuilder append(boolean b) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (b) {
            ensureCapacityInternal(count + 4);
            value[count++] = 't';
            value[count++] = 'r';
            value[count++] = 'u';
            value[count++] = 'e';
        } else {
            ensureCapacityInternal(count + 5);
            value[count++] = 'f';
            value[count++] = 'a';
            value[count++] = 'l';
            value[count++] = 's';
            value[count++] = 'e';
        }
        return this;
    }

    /**
     * Appends the string representation of the {@code char}
     * argument to this sequence.
     * <p>
     * The argument is appended to the contents of this sequence.
     * The length of this sequence increases by {@code 1}.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(char)},
     * and the character in that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param c a {@code char}.
     * @return a reference to this object.
     */
    @Override
    public AbstractStringBuilder append(char c) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        ensureCapacityInternal(count + 1);
        value[count++] = c;
        return this;
    }

    /**
     * Appends the string representation of the {@code int}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(int)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param i an {@code int}.
     * @return a reference to this object.
     */
    public AbstractStringBuilder append(int i) {
        return append(Integer.toString(i));
    }

    /**
     * Appends the string representation of the {@code long}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(long)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param l a {@code long}.
     * @return a reference to this object.
     */
    public AbstractStringBuilder append(long l) {
        return append(Long.toString(l));
    }

    /**
     * Appends the string representation of the {@code float}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(float)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param f a {@code float}.
     * @return a reference to this object.
     */
    public AbstractStringBuilder append(float f) {
        return append(Float.toString(f));
    }

    /**
     * Appends the string representation of the {@code double}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(double)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param d a {@code double}.
     * @return a reference to this object.
     */
    public AbstractStringBuilder append(double d) {
        return append(Double.toString(d));
    }

    /**
     * Removes the characters in a substring of this sequence.
     * The substring begins at the specified {@code start} and extends to
     * the character at index {@code end - 1} or to the end of the
     * sequence if no such character exists. If
     * {@code start} is equal to {@code end}, no changes are made.
     *
     * @param      start  The beginning index, inclusive.
     * @param      end    The ending index, exclusive.
     * @return     This object.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             is negative, greater than {@code length()}, or
     *             greater than {@code end}.
     */
    public AbstractStringBuilder delete(int start, int end) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            end = count;
        if (start > end)
            throw new StringIndexOutOfBoundsException();
        int len = end - start;
        if (len > 0) {
            value = CollectionIntrinsics.generateCharArray(value.length, index -> {
                if (index < start) return value[index];
                else if (index + len < value.length) return value[index + len];
                else return (char) 0;
            });
            count -= len;
        }
        return this;
    }

    /**
     * Code points are not supported, returns an unknown value
     */
    public AbstractStringBuilder appendCodePoint(int codePoint) {
        return UnknownIntrinsics.kexUnknown();
    }

    /**
     * Removes the {@code char} at the specified position in this
     * sequence. This sequence is shortened by one {@code char}.
     *
     * <p>Note: If the character at the given index is a supplementary
     * character, this method does not remove the entire character. If
     * correct handling of supplementary characters is required,
     * determine the number of {@code char}s to remove by calling
     * {@code Character.charCount(thisSequence.codePointAt(index))},
     * where {@code thisSequence} is this sequence.
     *
     * @param       index  Index of {@code char} to remove
     * @return      This object.
     * @throws      StringIndexOutOfBoundsException  if the {@code index}
     *              is negative or greater than or equal to
     *              {@code length()}.
     */
    public AbstractStringBuilder deleteCharAt(int index) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        value = CollectionIntrinsics.generateCharArray(value.length, i -> {
            if (i < index) return value[index];
            else if (i + 1 < value.length) return value[i + 1];
            else return '\u0000';
        });
        count--;
        return this;
    }

    /**
     * Replaces the characters in a substring of this sequence
     * with characters in the specified {@code String}. The substring
     * begins at the specified {@code start} and extends to the character
     * at index {@code end - 1} or to the end of the
     * sequence if no such character exists. First the
     * characters in the substring are removed and then the specified
     * {@code String} is inserted at {@code start}. (This
     * sequence will be lengthened to accommodate the
     * specified String if necessary.)
     *
     * @param      start    The beginning index, inclusive.
     * @param      end      The ending index, exclusive.
     * @param      str   String that will replace previous contents.
     * @return     This object.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             is negative, greater than {@code length()}, or
     *             greater than {@code end}.
     */
    public AbstractStringBuilder replace(int start, int end, String str) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (start > count)
            throw new StringIndexOutOfBoundsException("start > length()");
        if (start > end)
            throw new StringIndexOutOfBoundsException("start > end");

        if (end > count)
            end = count;
        int len = str.length();
        int finalEnd = end;
        int newCount = count + len - (end - start);
        ensureCapacityInternal(newCount);

        value = CollectionIntrinsics.generateCharArray(value.length, index -> {
            if (index < start) return value[index];
            else if (index >= finalEnd) return value[index - len];
            else return (char) 0;
        });
        char[] strChars = str.toCharArray();

        value = CollectionIntrinsics.generateCharArray(value.length, index -> {
            if (index < start) return value[index];
            else if (index >= finalEnd) return value[index];
            else return strChars[index];
        });
        count = newCount;
        return this;
    }

    /**
     * Returns a new {@code String} that contains a subsequence of
     * characters currently contained in this character sequence. The
     * substring begins at the specified index and extends to the end of
     * this sequence.
     *
     * @param      start    The beginning index, inclusive.
     * @return     The new string.
     * @throws     StringIndexOutOfBoundsException  if {@code start} is
     *             less than zero, or greater than the length of this object.
     */
    public String substring(int start) {
        return substring(start, count);
    }

    /**
     * Returns a new character sequence that is a subsequence of this sequence.
     *
     * <p> An invocation of this method of the form
     *
     * <pre>{@code
     * sb.subSequence(begin,&nbsp;end)}</pre>
     *
     * behaves in exactly the same way as the invocation
     *
     * <pre>{@code
     * sb.substring(begin,&nbsp;end)}</pre>
     *
     * This method is provided so that this class can
     * implement the {@link CharSequence} interface.
     *
     * @param      start   the start index, inclusive.
     * @param      end     the end index, exclusive.
     * @return     the specified subsequence.
     *
     * @throws  IndexOutOfBoundsException
     *          if {@code start} or {@code end} are negative,
     *          if {@code end} is greater than {@code length()},
     *          or if {@code start} is greater than {@code end}
     */
    @Override
    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }

    /**
     * Returns a new {@code String} that contains a subsequence of
     * characters currently contained in this sequence. The
     * substring begins at the specified {@code start} and
     * extends to the character at index {@code end - 1}.
     *
     * @param      start    The beginning index, inclusive.
     * @param      end      The ending index, exclusive.
     * @return     The new string.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             or {@code end} are negative or greater than
     *             {@code length()}, or {@code start} is
     *             greater than {@code end}.
     */
    public String substring(int start, int end) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            throw new StringIndexOutOfBoundsException(end);
        if (start > end)
            throw new StringIndexOutOfBoundsException(end - start);
        return new String(value, start, end - start);
    }

    /**
     * Inserts the string representation of a subarray of the {@code str}
     * array argument into this sequence. The subarray begins at the
     * specified {@code offset} and extends {@code len} {@code char}s.
     * The characters of the subarray are inserted into this sequence at
     * the position indicated by {@code index}. The length of this
     * sequence increases by {@code len} {@code char}s.
     *
     * @param      index    position at which to insert subarray.
     * @param      str       A {@code char} array.
     * @param      offset   the index of the first {@code char} in subarray to
     *             be inserted.
     * @param      len      the number of {@code char}s in the subarray to
     *             be inserted.
     * @return     This object
     * @throws     StringIndexOutOfBoundsException  if {@code index}
     *             is negative or greater than {@code length()}, or
     *             {@code offset} or {@code len} are negative, or
     *             {@code (offset+len)} is greater than
     *             {@code str.length}.
     */
    public AbstractStringBuilder insert(int index, char[] str, int offset, int len) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        if ((index < 0) || (index > length()))
            throw new StringIndexOutOfBoundsException(index);
        if ((offset < 0) || (len < 0) || (offset > str.length - len))
            throw new StringIndexOutOfBoundsException("offset " + offset + ", len " + len + ", str.length " + str.length);
        ensureCapacityInternal(count + len);
        value = CollectionIntrinsics.generateCharArray(value.length, i -> {
            if (i < index) return value[i];
            else if (i >= index + len) return value[i - len];
            else return '\u0000';
        });

        value = CollectionIntrinsics.generateCharArray(value.length, i -> {
            if (i < index) return value[i];
            else if (i >= index + len) return value[i];
            else return str[i + offset];
        });
        count += len;
        return this;
    }

    /**
     * Inserts the string representation of the {@code Object}
     * argument into this character sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(Object)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      obj      an {@code Object}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, Object obj) {
        return insert(offset, ObjectIntrinsics.any2String(obj));
    }

    /**
     * Inserts the string into this character sequence.
     * <p>
     * The characters of the {@code String} argument are inserted, in
     * order, into this sequence at the indicated offset, moving up any
     * characters originally above that position and increasing the length
     * of this sequence by the length of the argument. If
     * {@code str} is {@code null}, then the four characters
     * {@code "null"} are inserted into this sequence.
     * <p>
     * The character at index <i>k</i> in the new character sequence is
     * equal to:
     * <ul>
     * <li>the character at index <i>k</i> in the old character sequence, if
     * <i>k</i> is less than {@code offset}
     * <li>the character at index <i>k</i>{@code -offset} in the
     * argument {@code str}, if <i>k</i> is not less than
     * {@code offset} but is less than {@code offset+str.length()}
     * <li>the character at index <i>k</i>{@code -str.length()} in the
     * old character sequence, if <i>k</i> is not less than
     * {@code offset+str.length()}
     * </ul><p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      str      a string.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, String str) {
        char[] strArray = str.toCharArray();
        return insert(offset, strArray, 0, strArray.length);
    }

    /**
     * Inserts the string representation of the {@code char} array
     * argument into this sequence.
     * <p>
     * The characters of the array argument are inserted into the
     * contents of this sequence at the position indicated by
     * {@code offset}. The length of this sequence increases by
     * the length of the argument.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(char[])},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      str      a character array.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, char[] str) {
        return insert(offset, str, 0, str.length);
    }

    /**
     * Inserts the specified {@code CharSequence} into this sequence.
     * <p>
     * The characters of the {@code CharSequence} argument are inserted,
     * in order, into this sequence at the indicated offset, moving up
     * any characters originally above that position and increasing the length
     * of this sequence by the length of the argument s.
     * <p>
     * The result of this method is exactly the same as if it were an
     * invocation of this object's
     * {@link #insert(int,CharSequence,int,int) insert}(dstOffset, s, 0, s.length())
     * method.
     *
     * <p>If {@code s} is {@code null}, then the four characters
     * {@code "null"} are inserted into this sequence.
     *
     * @param      dstOffset   the offset.
     * @param      s the sequence to be inserted
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int dstOffset, CharSequence s) {
        if (s == null)
            s = "null";
        if (s instanceof String)
            return this.insert(dstOffset, (String) s);
        return this.insert(dstOffset, s);
    }

    /**
     * Inserts a subsequence of the specified {@code CharSequence} into
     * this sequence.
     * <p>
     * The subsequence of the argument {@code s} specified by
     * {@code start} and {@code end} are inserted,
     * in order, into this sequence at the specified destination offset, moving
     * up any characters originally above that position. The length of this
     * sequence is increased by {@code end - start}.
     * <p>
     * The character at index <i>k</i> in this sequence becomes equal to:
     * <ul>
     * <li>the character at index <i>k</i> in this sequence, if
     * <i>k</i> is less than {@code dstOffset}
     * <li>the character at index <i>k</i>{@code +start-dstOffset} in
     * the argument {@code s}, if <i>k</i> is greater than or equal to
     * {@code dstOffset} but is less than {@code dstOffset+end-start}
     * <li>the character at index <i>k</i>{@code -(end-start)} in this
     * sequence, if <i>k</i> is greater than or equal to
     * {@code dstOffset+end-start}
     * </ul><p>
     * The {@code dstOffset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     * <p>The start argument must be nonnegative, and not greater than
     * {@code end}.
     * <p>The end argument must be greater than or equal to
     * {@code start}, and less than or equal to the length of s.
     *
     * <p>If {@code s} is {@code null}, then this method inserts
     * characters as if the s parameter was a sequence containing the four
     * characters {@code "null"}.
     *
     * @param      dstOffset   the offset in this sequence.
     * @param      s       the sequence to be inserted.
     * @param      start   the starting index of the subsequence to be inserted.
     * @param      end     the end index of the subsequence to be inserted.
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if {@code dstOffset}
     *             is negative or greater than {@code this.length()}, or
     *              {@code start} or {@code end} are negative, or
     *              {@code start} is greater than {@code end} or
     *              {@code end} is greater than {@code s.length()}
     */
    public AbstractStringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
        return insert(dstOffset, s.toString().toCharArray(), start, end);
    }

    /**
     * Inserts the string representation of the {@code boolean}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(boolean)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      b        a {@code boolean}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, boolean b) {
        return insert(offset, String.valueOf(b));
    }

    /**
     * Inserts the string representation of the {@code char}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(char)},
     * and the character in that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      c        a {@code char}.
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, char c) {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        ensureCapacityInternal(count + 1);
        value = CollectionIntrinsics.generateCharArray(value.length, i -> {
            if (i < offset) return value[offset];
            else if (i > offset) return value[i - 1];
            else return '\u0000';
        });
        value[offset] = c;
        count += 1;
        return this;
    }

    /**
     * Inserts the string representation of the second {@code int}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(int)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      i        an {@code int}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, int i) {
        return insert(offset, Integer.valueOf(i));
    }

    /**
     * Inserts the string representation of the {@code long}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(long)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      l        a {@code long}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, long l) {
        return insert(offset, Long.valueOf(l));
    }

    /**
     * Inserts the string representation of the {@code float}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(float)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      f        a {@code float}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, float f) {
        return insert(offset, Float.valueOf(f));
    }

    /**
     * Inserts the string representation of the {@code double}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(double)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      d        a {@code double}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, double d) {
        return insert(offset, Double.valueOf(d));
    }

    /**
     * Not supported, returns an unknown value
     */
    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    /**
     * Not supported, returns an unknown value
     */
    public int indexOf(String str, int fromIndex) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    /**
     * Not supported, returns an unknown value
     */
    public int lastIndexOf(String str) {
        return lastIndexOf(str, count);
    }

    /**
     * Not supported, returns an unknown value
     */
    public int lastIndexOf(String str, int fromIndex) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    /**
     * Causes this character sequence to be replaced by the reverse of
     * the sequence. If there are any surrogate pairs included in the
     * sequence, these are treated as single characters for the
     * reverse operation. Thus, the order of the high-low surrogates
     * is never reversed.
     *
     * Let <i>n</i> be the character length of this character sequence
     * (not the length in {@code char} values) just prior to
     * execution of the {@code reverse} method. Then the
     * character at index <i>k</i> in the new character sequence is
     * equal to the character at index <i>n-k-1</i> in the old
     * character sequence.
     *
     * <p>Note that the reverse operation may result in producing
     * surrogate pairs that were unpaired low-surrogates and
     * high-surrogates before the operation. For example, reversing
     * "\u005CuDC00\u005CuD800" produces "\u005CuD800\u005CuDC00" which is
     * a valid surrogate pair.
     *
     * @return  a reference to this object.
     */
    public AbstractStringBuilder reverse() {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        value = CollectionIntrinsics.generateCharArray(value.length, index -> value[value.length - index - 1]);
        return this;
    }

    /**
     * Returns a string representing the data in this sequence.
     * A new {@code String} object is allocated and initialized to
     * contain the character sequence currently represented by this
     * object. This {@code String} is then returned. Subsequent
     * changes to this sequence do not affect the contents of the
     * {@code String}.
     *
     * @return  a string representation of this sequence of characters.
     */
    @Override
    public String toString() {
        return new String(toCharArray());
    }

    /**
     * Needed by {@code String} for the contentEquals method.
     */
    final char[] getValue() {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        return value;
    }

    /**
     * Converts this string builder to a new character array.
     *
     * @return a newly allocated character array whose length is equal to {@code count}
     * and whose contents are initialized to contain the character sequence represented by this string builder.
     */
    public char[] toCharArray() {
        AssertIntrinsics.kexNotNull(value);
        AssertIntrinsics.kexAssume(count >= 0);
        return CollectionIntrinsics.generateCharArray(count, index -> value[index]);
    }
}
