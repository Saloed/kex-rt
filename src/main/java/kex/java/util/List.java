package kex.java.util;

import org.jetbrains.annotations.NotNull;
import org.vorpal.research.kex.intrinsics.AssertIntrinsics;
import org.vorpal.research.kex.intrinsics.CollectionIntrinsics;

import java.util.Collection;

abstract class List<E> implements java.util.List<E> {
    private Object[] elementData = CollectionIntrinsics.emptyObjectArray();

    private void rangeCheck(int index) {
        AssertIntrinsics.kexNotNull(elementData);
        if (index < 0 || index >= elementData.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int size() {
        AssertIntrinsics.kexNotNull(elementData);
        return elementData.length;
    }

    @Override
    public boolean isEmpty() {
        AssertIntrinsics.kexNotNull(elementData);
        return elementData.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public boolean add(E e) {
        AssertIntrinsics.kexNotNull(elementData);
        int oldLength = elementData.length;
        elementData = CollectionIntrinsics.arrayCopyAndGrow(elementData, 1);
        elementData[oldLength] = e;
        return true;
    }

    @Override
    public E get(int index) {
        AssertIntrinsics.kexNotNull(elementData);
        rangeCheck(index);
        return (E) elementData[index];
    }

    @Override
    public E set(int index, E element) {
        AssertIntrinsics.kexNotNull(elementData);
        rangeCheck(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        AssertIntrinsics.kexNotNull(elementData);
        if (index == elementData.length) {
            add(element);
            return;
        }
        elementData = CollectionIntrinsics.arrayCopyAndShiftRight(elementData, index, 1);
        elementData[index] = element;
    }

    @Override
    public void clear() {
        AssertIntrinsics.kexNotNull(elementData);
        elementData = CollectionIntrinsics.arrayCopyAndTrim(elementData, elementData.length);
    }

    @Override
    public int indexOf(Object o) {
        return indexOfFrom(0, o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return indexOfFromReversed(elementData.length - 1, o);
    }

    private int indexOfFrom(int index, Object o) {
        if (index >= elementData.length) return -1;
        Object element = elementData[index];
        if (o == null) {
            if (element == null) return index;
        } else {
            if (o.equals(element)) return index;
        }
        return indexOfFrom(index + 1, o);
    }

    private int indexOfFromReversed(int index, Object o) {
        if (index <= 0) return -1;
        Object element = elementData[index];
        if (o == null) {
            if (element == null) return index;
        } else {
            if (o.equals(element)) return index;
        }
        return indexOfFrom(index - 1, o);
    }

    @Override
    public E remove(int index) {
        AssertIntrinsics.kexNotNull(elementData);
        rangeCheck(index);

        E oldValue = (E) elementData[index];

        if (index == elementData.length - 1) {
            elementData = CollectionIntrinsics.arrayCopyAndTrim(elementData, 1);
        } else {
            elementData = CollectionIntrinsics.arrayCopyAndShiftLeft(elementData, index, 1);
        }

        return oldValue;
    }

    @Override
    public boolean remove(Object o) {
        AssertIntrinsics.kexNotNull(elementData);
        int removeIndex = indexOf(o);
        if (removeIndex == -1) {
            return false;
        } else {
            remove(removeIndex);
            return true;
        }
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        if (c instanceof java.util.List) {
            return containsAllList((java.util.List) c);
        } else {
            return false;
        }
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        if (c instanceof java.util.List) {
            return addAllList((java.util.List<E>) c);
        } else {
            return false;
        }
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        if (c instanceof java.util.List) {
            return addAllList(index, (java.util.List<E>) c);
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        if (c instanceof java.util.List) {
            return removeAllList((java.util.List) c);
        } else {
            return false;
        }
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        if (c instanceof java.util.List) {
            return retainAllList((java.util.List) c);
        } else {
            return false;
        }
    }


    private boolean containsAllList(java.util.List c) {
        return false;
    }

    private boolean addAllList(java.util.List<E> c) {
        int length = c.size();
        if (length == 0) {
            return false;
        } else {
            addAllHelper(0, length, c);
            return true;
        }
    }

    private void addAllHelper(int i, int length, java.util.List<E> c) {
        if (i == length) return;
        E value = c.get(i);
        add(value);
        addAllHelper(i + 1, length, c);
    }

    private boolean addAllList(int index, java.util.List<E> c) {
        return false;
    }

    private boolean retainAllList(java.util.List c) {
        return false;
    }

    private boolean removeAllList(java.util.List c) {
        return false;
    }

}
