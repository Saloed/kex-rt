package kex.java.lang;

import kex.java.util.Arrays;
import org.jetbrains.research.kex.AssertIntrinsics;
import org.jetbrains.research.kex.CollectionIntrinsics;
import org.jetbrains.research.kex.UnknownIntrinsics;
import org.jetbrains.research.kex.ObjectIntrinsics;

public abstract class AbstractStringBuilder implements Appendable, CharSequence {
    char[] value;
    int count;

    AbstractStringBuilder() {}

    AbstractStringBuilder(int capacity) {
        int actualCapacity = UnknownIntrinsics.kexUnknownInt();
        value = new char[actualCapacity];

    }

    @Override
    public int length() {
        return count;
    }

    public int capacity() {
        return value.length;
    }

    public void ensureCapacity(int minimumCapacity) {
        AssertIntrinsics.kexAssume(value.length > minimumCapacity);
    }

    private void ensureCapacityInternal(int minimumCapacity) {
        AssertIntrinsics.kexAssume(value.length > minimumCapacity);
    }

    public void trimToSize() {
        // nothing
    }

    public void setLength(int newLength) {
        if (newLength < 0)
            throw new StringIndexOutOfBoundsException(newLength);
        ensureCapacityInternal(newLength);

        if (count < newLength) {
            Arrays.fill(value, count, newLength, '\0');
        }

        count = newLength;
    }

    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        return value[index];
    }

    public int codePointAt(int index) {
        if ((index < 0) || (index >= count)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }

    public int codePointBefore(int index) {
        int i = index - 1;
        if ((i < 0) || (i >= count)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        if (srcBegin < 0)
            throw new StringIndexOutOfBoundsException(srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
            throw new StringIndexOutOfBoundsException(srcEnd);
        if (srcBegin > srcEnd)
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    public void setCharAt(int index, char ch) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        value[index] = ch;
    }

    public AbstractStringBuilder append(Object obj) {
        return append(ObjectIntrinsics.any2String(obj));
    }

    public AbstractStringBuilder append(String str) {
        if (str == null)
            return appendNull();
        int len = str.length();
        ensureCapacityInternal(count + len);
        str.getChars(0, len, value, count);
        count += len;
        return this;
    }

    public AbstractStringBuilder append(StringBuffer sb) {
        if (sb == null)
            return appendNull();
        int len = sb.length();
        ensureCapacityInternal(count + len);
        sb.getChars(0, len, value, count);
        count += len;
        return this;
    }

    AbstractStringBuilder append(AbstractStringBuilder asb) {
        if (asb == null)
            return appendNull();
        int len = asb.length();
        ensureCapacityInternal(count + len);
        asb.getChars(0, len, value, count);
        count += len;
        return this;
    }

    @Override
    public AbstractStringBuilder append(CharSequence s) {
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

    @Override
    public AbstractStringBuilder append(CharSequence s, int start, int end) {
        if (s == null)
            s = "null";
        if ((start < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException("start " + start + ", end " + end + ", s.length() " + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        CharSequence finalS = s;
        CollectionIntrinsics.forEach(start, end, index -> {
            value[count + index] = finalS.charAt(index);
        });
        count += len;
        return this;
    }

    public AbstractStringBuilder append(char[] str) {
        int len = str.length;
        ensureCapacityInternal(count + len);
        CollectionIntrinsics.arrayCopy(str, 0, value, count, len);
        count += len;
        return this;
    }

    public AbstractStringBuilder append(char[] str, int offset, int len) {
        if (len > 0)                // let arraycopy report AIOOBE for len < 0
            ensureCapacityInternal(count + len);
        CollectionIntrinsics.arrayCopy(str, offset, value, count, len);
        count += len;
        return this;
    }

    public AbstractStringBuilder append(boolean b) {
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

    @Override
    public AbstractStringBuilder append(char c) {
        ensureCapacityInternal(count + 1);
        value[count++] = c;
        return this;
    }

    public AbstractStringBuilder append(int i) {
        return append(Integer.toString(i));
    }

    public AbstractStringBuilder append(long l) {
        return append(Long.toString(l));
    }

    public AbstractStringBuilder append(float f) {
        return append(Float.toString(f));
    }

    public AbstractStringBuilder append(double d) {
        return append(Double.toString(d));
    }

    public AbstractStringBuilder delete(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            end = count;
        if (start > end)
            throw new StringIndexOutOfBoundsException();
        int len = end - start;
        if (len > 0) {
            CollectionIntrinsics.arrayCopy(value, start + len, value, start, count - end);
            count -= len;
        }
        return this;
    }

    public AbstractStringBuilder appendCodePoint(int codePoint) {
        return UnknownIntrinsics.kexUnknown();
    }

    public AbstractStringBuilder deleteCharAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        CollectionIntrinsics.arrayCopy(value, index + 1, value, index, count - index - 1);
        count--;
        return this;
    }

    public AbstractStringBuilder replace(int start, int end, String str) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (start > count)
            throw new StringIndexOutOfBoundsException("start > length()");
        if (start > end)
            throw new StringIndexOutOfBoundsException("start > end");

        if (end > count)
            end = count;
        int len = str.length();
        int newCount = count + len - (end - start);
        ensureCapacityInternal(newCount);

        CollectionIntrinsics.arrayCopy(value, end, value, start + len, count - end);
        char[] strChars = str.toCharArray();
        CollectionIntrinsics.arrayCopy(strChars, 0, value, start, strChars.length);
        count = newCount;
        return this;
    }

    public String substring(int start) {
        return substring(start, count);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }

    public String substring(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            throw new StringIndexOutOfBoundsException(end);
        if (start > end)
            throw new StringIndexOutOfBoundsException(end - start);
        return new String(value, start, end - start);
    }

    public AbstractStringBuilder insert(int index, char[] str, int offset, int len) {
        if ((index < 0) || (index > length()))
            throw new StringIndexOutOfBoundsException(index);
        if ((offset < 0) || (len < 0) || (offset > str.length - len))
            throw new StringIndexOutOfBoundsException("offset " + offset + ", len " + len + ", str.length " + str.length);
        ensureCapacityInternal(count + len);
        CollectionIntrinsics.arrayCopy(value, index, value, index + len, count - index);
        CollectionIntrinsics.arrayCopy(str, offset, value, index, len);
        count += len;
        return this;
    }

    public AbstractStringBuilder insert(int offset, Object obj) {
        return insert(offset, ObjectIntrinsics.any2String(obj));
    }

    public AbstractStringBuilder insert(int offset, String str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        if (str == null)
            str = "null";
        int len = str.length();
        ensureCapacityInternal(count + len);
        CollectionIntrinsics.arrayCopy(value, offset, value, offset + len, count - offset);
        char[] strChars = str.toCharArray();
        CollectionIntrinsics.arrayCopy(strChars, 0, value, offset, strChars.length);
        count += len;
        return this;
    }

    public AbstractStringBuilder insert(int offset, char[] str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        int len = str.length;
        ensureCapacityInternal(count + len);
        CollectionIntrinsics.arrayCopy(value, offset, value, offset + len, count - offset);
        CollectionIntrinsics.arrayCopy(str, 0, value, offset, len);
        count += len;
        return this;
    }

    public AbstractStringBuilder insert(int dstOffset, CharSequence s) {
        if (s == null)
            s = "null";
        if (s instanceof String)
            return this.insert(dstOffset, (String)s);
        return this.insert(dstOffset, s, 0, s.length());
    }

    public AbstractStringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
        if (s == null)
            s = "null";
        if ((dstOffset < 0) || (dstOffset > this.length()))
            throw new IndexOutOfBoundsException("dstOffset "+dstOffset);
        if ((start < 0) || (end < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException("start " + start + ", end " + end + ", s.length() " + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        CollectionIntrinsics.arrayCopy(value, dstOffset, value, dstOffset + len,                count - dstOffset);
        CharSequence finalS = s;
        CollectionIntrinsics.forEach(start, end, index -> {
            value[dstOffset + index] = finalS.charAt(index);
        });
        count += len;
        return this;
    }

    public AbstractStringBuilder insert(int offset, boolean b) {
        return insert(offset, String.valueOf(b));
    }

    public AbstractStringBuilder insert(int offset, char c) {
        ensureCapacityInternal(count + 1);
        CollectionIntrinsics.arrayCopy(value, offset, value, offset + 1, count - offset);
        value[offset] = c;
        count += 1;
        return this;
    }

    public AbstractStringBuilder insert(int offset, int i) {
        return insert(offset, Integer.valueOf(i));
    }

    public AbstractStringBuilder insert(int offset, long l) {
        return insert(offset, Long.valueOf(l));
    }

    public AbstractStringBuilder insert(int offset, float f) {
        return insert(offset, Float.valueOf(f));
    }

    public AbstractStringBuilder insert(int offset, double d) {
        return insert(offset, Double.valueOf(d));
    }

    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    public int indexOf(String str, int fromIndex) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, count);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return UnknownIntrinsics.kexUnknownInt();
    }

    public AbstractStringBuilder reverse() {
        int n = count - 1;
        CollectionIntrinsics.forEach(0, (n - 1) / 2, j -> {
            int k = n - j;
            char cj = value[j];
            char ck = value[k];
            value[j] = ck;
            value[k] = cj;
        });
        return this;
    }

    @Override
    public abstract String toString();

    final char[] getValue() {
        return value;
    }
}
