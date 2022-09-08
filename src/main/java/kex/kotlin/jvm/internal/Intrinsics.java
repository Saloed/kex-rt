package kex.kotlin.jvm.internal;


import kotlin.KotlinNullPointerException;
import kotlin.UninitializedPropertyAccessException;
import org.vorpal.research.kex.intrinsics.ObjectIntrinsics;

public class Intrinsics {
    private Intrinsics() {
    }

    public static String stringPlus(String self, Object other) {
        return self + other;
    }

    public static void checkNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }

    }

    public static void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new NullPointerException();
        }

    }

    public static void throwNpe() {
        throw new KotlinNullPointerException();
    }

    public static void throwNpe(String message) {
        throw new KotlinNullPointerException();
    }

    public static void throwJavaNpe() {
        throw new NullPointerException();
    }

    public static void throwJavaNpe(String message) {
        throw new NullPointerException();
    }

    public static void throwUninitializedProperty(String message) {
        throw new UninitializedPropertyAccessException();
    }

    public static void throwUninitializedPropertyAccessException(String propertyName) {
        throw new UninitializedPropertyAccessException();
    }

    public static void throwAssert() {
        throw new AssertionError();
    }

    public static void throwAssert(String message) {
        throw new AssertionError();
    }

    public static void throwIllegalArgument() {
        throw new IllegalArgumentException();
    }

    public static void throwIllegalArgument(String message) {
        throw new IllegalArgumentException();
    }

    public static void throwIllegalState() {
        throw new IllegalStateException();
    }

    public static void throwIllegalState(String message) {
        throw new IllegalStateException();
    }

    public static void checkExpressionValueIsNotNull(Object value, String expression) {
        if (value == null) {
            throw new IllegalStateException();
        }
    }

    public static void checkNotNullExpressionValue(Object value, String expression) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

    public static void checkReturnedValueIsNotNull(Object value, String className, String methodName) {
        if (value == null) {
            throw new IllegalStateException();
        }
    }

    public static void checkReturnedValueIsNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalStateException();
        }
    }

    public static void checkFieldIsNotNull(Object value, String className, String fieldName) {
        if (value == null) {
            throw new IllegalStateException();
        }
    }

    public static void checkFieldIsNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalStateException();
        }
    }

    public static void checkParameterIsNotNull(Object value, String paramName) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkNotNullParameter(Object value, String paramName) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

    private static void throwParameterIsNullIAE(String paramName) {
        throw new IllegalArgumentException();
    }

    private static void throwParameterIsNullNPE(String paramName) {
        throw new NullPointerException();
    }

    public static int compare(long thisVal, long anotherVal) {
        return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
    }

    public static int compare(int thisVal, int anotherVal) {
        return thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1);
    }

    public static boolean areEqual(Object first, Object second) {
        if (first == null) return second == null;
        return ObjectIntrinsics.equals(first, second);
    }

    public static boolean areEqual(Double first, Double second) {
        // todo: use double bits instead of doubleValue
        return first == null ? second == null : second != null && first.doubleValue() == second.doubleValue();
    }

    public static boolean areEqual(Double first, double second) {
        return first != null && first.doubleValue() == second;
    }

    public static boolean areEqual(double first, Double second) {
        return second != null && first == second.doubleValue();
    }

    public static boolean areEqual(Float first, Float second) {
        // todo: use gloat bits instead of floatValue
        return first == null ? second == null : second != null && first.floatValue() == second.floatValue();
    }

    public static boolean areEqual(Float first, float second) {
        return first != null && first.floatValue() == second;
    }

    public static boolean areEqual(float first, Float second) {
        return second != null && first == second.floatValue();
    }

    public static void throwUndefinedForReified() {
        throw new UnsupportedOperationException();
    }

    public static void throwUndefinedForReified(String message) {
        throw new UnsupportedOperationException();
    }

    public static void reifiedOperationMarker(int id, String typeParameterIdentifier) {
        throw new UnsupportedOperationException();
    }

    public static void reifiedOperationMarker(int id, String typeParameterIdentifier, String message) {
        throw new UnsupportedOperationException();
    }

    public static void needClassReification() {
        throw new UnsupportedOperationException();
    }

    public static void needClassReification(String message) {
        throw new UnsupportedOperationException();
    }

    public static void checkHasClass(String internalName) throws ClassNotFoundException {
        String fqName = internalName.replace('/', '.');

        try {
            Class.forName(fqName);
        } catch (ClassNotFoundException var3) {
            throw new ClassNotFoundException();
        }
    }

    public static void checkHasClass(String internalName, String requiredVersion) throws ClassNotFoundException {
        String fqName = internalName.replace('/', '.');

        try {
            Class.forName(fqName);
        } catch (ClassNotFoundException var4) {
            throw new ClassNotFoundException();
        }
    }

    public static class Kotlin {
        private Kotlin() {
        }
    }
}
