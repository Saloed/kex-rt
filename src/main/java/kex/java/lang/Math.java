package kex.java.lang;

import jdk.internal.math.DoubleConsts;
import jdk.internal.math.FloatConsts;

import java.lang.ArithmeticException;

public final class Math {
    public static final double E = 2.7182818284590452354;

    public static final double PI = 3.14159265358979323846;
    private static final double DEGREES_TO_RADIANS = 0.017453292519943295;
    private static final double RADIANS_TO_DEGREES = 57.29577951308232;

    public static double toRadians(double angdeg) {
        return angdeg * DEGREES_TO_RADIANS;
    }

    public static double toDegrees(double angrad) {
        return angrad * RADIANS_TO_DEGREES;
    }

    public static int round(float a) {
        int intBits = Float.floatToRawIntBits(a);
        int biasedExp = (intBits & FloatConsts.EXP_BIT_MASK)
                >> (FloatConsts.SIGNIFICAND_WIDTH - 1);
        int shift = (FloatConsts.SIGNIFICAND_WIDTH - 2
                + FloatConsts.EXP_BIAS) - biasedExp;
        if ((shift & -32) == 0) { // shift >= 0 && shift < 32
            // a is a finite number such that pow(2,-32) <= ulp(a) < 1
            int r = ((intBits & FloatConsts.SIGNIF_BIT_MASK)
                    | (FloatConsts.SIGNIF_BIT_MASK + 1));
            if (intBits < 0) {
                r = -r;
            }
            // In the comments below each Java expression evaluates to the value
            // the corresponding mathematical expression:
            // (r) evaluates to a / ulp(a)
            // (r >> shift) evaluates to floor(a * 2)
            // ((r >> shift) + 1) evaluates to floor((a + 1/2) * 2)
            // (((r >> shift) + 1) >> 1) evaluates to floor(a + 1/2)
            return ((r >> shift) + 1) >> 1;
        } else {
            // a is either
            // - a finite number with abs(a) < exp(2,FloatConsts.SIGNIFICAND_WIDTH-32) < 1/2
            // - a finite number with ulp(a) >= 1 and hence a is a mathematical integer
            // - an infinity or NaN
            return (int) a;
        }
    }

    public static long round(double a) {
        long longBits = Double.doubleToRawLongBits(a);
        long biasedExp = (longBits & DoubleConsts.EXP_BIT_MASK)
                >> (DoubleConsts.SIGNIFICAND_WIDTH - 1);
        long shift = (DoubleConsts.SIGNIFICAND_WIDTH - 2
                + DoubleConsts.EXP_BIAS) - biasedExp;
        if ((shift & -64) == 0) { // shift >= 0 && shift < 64
            // a is a finite number such that pow(2,-64) <= ulp(a) < 1
            long r = ((longBits & DoubleConsts.SIGNIF_BIT_MASK)
                    | (DoubleConsts.SIGNIF_BIT_MASK + 1));
            if (longBits < 0) {
                r = -r;
            }
            // In the comments below each Java expression evaluates to the value
            // the corresponding mathematical expression:
            // (r) evaluates to a / ulp(a)
            // (r >> shift) evaluates to floor(a * 2)
            // ((r >> shift) + 1) evaluates to floor((a + 1/2) * 2)
            // (((r >> shift) + 1) >> 1) evaluates to floor(a + 1/2)
            return ((r >> shift) + 1) >> 1;
        } else {
            // a is either
            // - a finite number with abs(a) < exp(2,DoubleConsts.SIGNIFICAND_WIDTH-64) < 1/2
            // - a finite number with ulp(a) >= 1 and hence a is a mathematical integer
            // - an infinity or NaN
            return (long) a;
        }
    }

    public static int addExact(int x, int y) {
        int r = x + y;
        // HD 2-12 Overflow iff both arguments have the opposite sign of the result
        if (((x ^ r) & (y ^ r)) < 0) {
            throw new ArithmeticException("integer overflow");
        }
        return r;
    }

    public static long addExact(long x, long y) {
        long r = x + y;
        // HD 2-12 Overflow iff both arguments have the opposite sign of the result
        if (((x ^ r) & (y ^ r)) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return r;
    }

    public static int subtractExact(int x, int y) {
        int r = x - y;
        // HD 2-12 Overflow iff the arguments have different signs and
        // the sign of the result is different from the sign of x
        if (((x ^ y) & (x ^ r)) < 0) {
            throw new ArithmeticException("integer overflow");
        }
        return r;
    }

    public static long subtractExact(long x, long y) {
        long r = x - y;
        // HD 2-12 Overflow iff the arguments have different signs and
        // the sign of the result is different from the sign of x
        if (((x ^ y) & (x ^ r)) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return r;
    }

    public static int multiplyExact(int x, int y) {
        long r = (long) x * (long) y;
        if ((int) r != r) {
            throw new ArithmeticException("integer overflow");
        }
        return (int) r;
    }

    public static long multiplyExact(long x, int y) {
        return multiplyExact(x, (long) y);
    }

    public static long multiplyExact(long x, long y) {
        long r = x * y;
        long ax = Math.abs(x);
        long ay = Math.abs(y);
        if (((ax | ay) >>> 31 != 0)) {
            // Some bits greater than 2^31 that might cause overflow
            // Check the result using the divide operator
            // and check for the special case of Long.MIN_VALUE * -1
            if (((y != 0) && (r / y != x)) ||
                    (x == Long.MIN_VALUE && y == -1)) {
                throw new ArithmeticException("long overflow");
            }
        }
        return r;
    }

    public static int incrementExact(int a) {
        if (a == Integer.MAX_VALUE) {
            throw new ArithmeticException("integer overflow");
        }

        return a + 1;
    }

    public static long incrementExact(long a) {
        if (a == Long.MAX_VALUE) {
            throw new ArithmeticException("long overflow");
        }

        return a + 1L;
    }

    public static int decrementExact(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new ArithmeticException("integer overflow");
        }

        return a - 1;
    }

    public static long decrementExact(long a) {
        if (a == Long.MIN_VALUE) {
            throw new ArithmeticException("long overflow");
        }

        return a - 1L;
    }

    public static int negateExact(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new ArithmeticException("integer overflow");
        }

        return -a;
    }

    public static long negateExact(long a) {
        if (a == Long.MIN_VALUE) {
            throw new ArithmeticException("long overflow");
        }

        return -a;
    }

    public static int toIntExact(long value) {
        if ((int) value != value) {
            throw new ArithmeticException("integer overflow");
        }
        return (int) value;
    }

    public static long multiplyFull(int x, int y) {
        return (long) x * (long) y;
    }

    public static long multiplyHigh(long x, long y) {
        if (x < 0 || y < 0) {
            // Use technique from section 8-2 of Henry S. Warren, Jr.,
            // Hacker's Delight (2nd ed.) (Addison Wesley, 2013), 173-174.
            long x1 = x >> 32;
            long x2 = x & 0xFFFFFFFFL;
            long y1 = y >> 32;
            long y2 = y & 0xFFFFFFFFL;
            long z2 = x2 * y2;
            long t = x1 * y2 + (z2 >>> 32);
            long z1 = t & 0xFFFFFFFFL;
            long z0 = t >> 32;
            z1 += x2 * y1;
            return x1 * y1 + z0 + (z1 >> 32);
        } else {
            // Use Karatsuba technique with two base 2^32 digits.
            long x1 = x >>> 32;
            long y1 = y >>> 32;
            long x2 = x & 0xFFFFFFFFL;
            long y2 = y & 0xFFFFFFFFL;
            long A = x1 * y1;
            long B = x2 * y2;
            long C = (x1 + x2) * (y1 + y2);
            long K = C - A - B;
            return (((B >>> 32) + K) >>> 32) + A;
        }
    }

    public static int floorDiv(int x, int y) {
        int r = x / y;
        // if the signs are different and modulo not zero, round down
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }

    public static long floorDiv(long x, int y) {
        return floorDiv(x, (long) y);
    }

    public static long floorDiv(long x, long y) {
        long r = x / y;
        // if the signs are different and modulo not zero, round down
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }

    public static int floorMod(int x, int y) {
        int mod = x % y;
        // if the signs are different and modulo not zero, adjust result
        if ((mod ^ y) < 0 && mod != 0) {
            mod += y;
        }
        return mod;
    }

    public static int floorMod(long x, int y) {
        // Result cannot overflow the range of int.
        return (int) floorMod(x, (long) y);
    }

    public static long floorMod(long x, long y) {
        long mod = x % y;
        // if the signs are different and modulo not zero, adjust result
        if ((x ^ y) < 0 && mod != 0) {
            mod += y;
        }
        return mod;
    }


    public static int abs(int a) {
        return (a < 0) ? -a : a;
    }

    public static int absExact(int a) {
        if (a == Integer.MIN_VALUE)
            throw new ArithmeticException(
                    "Overflow to represent absolute value of Integer.MIN_VALUE");
        else
            return abs(a);
    }

    public static long abs(long a) {
        return (a < 0) ? -a : a;
    }

    public static long absExact(long a) {
        if (a == Long.MIN_VALUE)
            throw new ArithmeticException(
                    "Overflow to represent absolute value of Long.MIN_VALUE");
        else
            return abs(a);
    }

    public static float abs(float a) {
        return (a <= 0.0F) ? 0.0F - a : a;
    }

    public static double abs(double a) {
        return (a <= 0.0D) ? 0.0D - a : a;
    }


    public static int max(int a, int b) {
        return (a >= b) ? a : b;
    }

    public static long max(long a, long b) {
        return (a >= b) ? a : b;
    }

    private static final long negativeZeroFloatBits = Float.floatToRawIntBits(-0.0f);
    private static final long negativeZeroDoubleBits = Double.doubleToRawLongBits(-0.0d);

    public static float max(float a, float b) {
        if (a != a)
            return a;   // a is NaN
        if ((a == 0.0f) &&
                (b == 0.0f) &&
                (Float.floatToRawIntBits(a) == negativeZeroFloatBits)) {
            // Raw conversion ok since NaN can't map to -0.0.
            return b;
        }
        return (a >= b) ? a : b;
    }


    public static double max(double a, double b) {
        if (a != a)
            return a;   // a is NaN
        if ((a == 0.0d) &&
                (b == 0.0d) &&
                (Double.doubleToRawLongBits(a) == negativeZeroDoubleBits)) {
            // Raw conversion ok since NaN can't map to -0.0.
            return b;
        }
        return (a >= b) ? a : b;
    }

    public static int min(int a, int b) {
        return (a <= b) ? a : b;
    }

    public static long min(long a, long b) {
        return (a <= b) ? a : b;
    }


    public static float min(float a, float b) {
        if (a != a)
            return a;   // a is NaN
        if ((a == 0.0f) &&
                (b == 0.0f) &&
                (Float.floatToRawIntBits(b) == negativeZeroFloatBits)) {
            // Raw conversion ok since NaN can't map to -0.0.
            return b;
        }
        return (a <= b) ? a : b;
    }


    public static double min(double a, double b) {
        if (a != a)
            return a;   // a is NaN
        if ((a == 0.0d) &&
                (b == 0.0d) &&
                (Double.doubleToRawLongBits(b) == negativeZeroDoubleBits)) {
            // Raw conversion ok since NaN can't map to -0.0.
            return b;
        }
        return (a <= b) ? a : b;
    }

    public static double ulp(double d) {
        int exp = getExponent(d);

        switch (exp) {
            case Double.MAX_EXPONENT + 1:       // NaN or infinity
                return Math.abs(d);

            case Double.MIN_EXPONENT - 1:       // zero or subnormal
                return Double.MIN_VALUE;

            default:
                assert exp <= Double.MAX_EXPONENT && exp >= Double.MIN_EXPONENT;

                // ulp(x) is usually 2^(SIGNIFICAND_WIDTH-1)*(2^ilogb(x))
                exp = exp - (DoubleConsts.SIGNIFICAND_WIDTH - 1);
                if (exp >= Double.MIN_EXPONENT) {
                    return powerOfTwoD(exp);
                } else {
                    // return a subnormal result; left shift integer
                    // representation of Double.MIN_VALUE appropriate
                    // number of positions
                    return Double.longBitsToDouble(1L <<
                            (exp - (Double.MIN_EXPONENT - (DoubleConsts.SIGNIFICAND_WIDTH - 1))));
                }
        }
    }


    public static float ulp(float f) {
        int exp = getExponent(f);

        switch (exp) {
            case Float.MAX_EXPONENT + 1:        // NaN or infinity
                return Math.abs(f);

            case Float.MIN_EXPONENT - 1:        // zero or subnormal
                return Float.MIN_VALUE;

            default:
                assert exp <= Float.MAX_EXPONENT && exp >= Float.MIN_EXPONENT;

                // ulp(x) is usually 2^(SIGNIFICAND_WIDTH-1)*(2^ilogb(x))
                exp = exp - (FloatConsts.SIGNIFICAND_WIDTH - 1);
                if (exp >= Float.MIN_EXPONENT) {
                    return powerOfTwoF(exp);
                } else {
                    // return a subnormal result; left shift integer
                    // representation of FloatConsts.MIN_VALUE appropriate
                    // number of positions
                    return Float.intBitsToFloat(1 <<
                            (exp - (Float.MIN_EXPONENT - (FloatConsts.SIGNIFICAND_WIDTH - 1))));
                }
        }
    }


    public static double signum(double d) {
        return (d == 0.0 || Double.isNaN(d)) ? d : copySign(1.0, d);
    }


    public static float signum(float f) {
        return (f == 0.0f || Float.isNaN(f)) ? f : copySign(1.0f, f);
    }

    public static double copySign(double magnitude, double sign) {
        return Double.longBitsToDouble((Double.doubleToRawLongBits(sign) &
                (DoubleConsts.SIGN_BIT_MASK)) |
                (Double.doubleToRawLongBits(magnitude) &
                        (DoubleConsts.EXP_BIT_MASK |
                                DoubleConsts.SIGNIF_BIT_MASK)));
    }


    public static float copySign(float magnitude, float sign) {
        return Float.intBitsToFloat((Float.floatToRawIntBits(sign) &
                (FloatConsts.SIGN_BIT_MASK)) |
                (Float.floatToRawIntBits(magnitude) &
                        (FloatConsts.EXP_BIT_MASK |
                                FloatConsts.SIGNIF_BIT_MASK)));
    }


    public static int getExponent(float f) {

        return ((Float.floatToRawIntBits(f) & FloatConsts.EXP_BIT_MASK) >>
                (FloatConsts.SIGNIFICAND_WIDTH - 1)) - FloatConsts.EXP_BIAS;
    }


    public static int getExponent(double d) {

        return (int) (((Double.doubleToRawLongBits(d) & DoubleConsts.EXP_BIT_MASK) >>
                (DoubleConsts.SIGNIFICAND_WIDTH - 1)) - DoubleConsts.EXP_BIAS);
    }


    public static double nextAfter(double start, double direction) {
        // Branch to descending case first as it is more costly than ascending
        // case due to start != 0.0d conditional.
        if (start > direction) { // descending
            if (start != 0.0d) {
                final long transducer = Double.doubleToRawLongBits(start);
                return Double.longBitsToDouble(transducer + ((transducer > 0L) ? -1L : 1L));
            } else { // start == 0.0d && direction < 0.0d
                return -Double.MIN_VALUE;
            }
        } else if (start < direction) { // ascending
            // Add +0.0 to get rid of a -0.0 (+0.0 + -0.0 => +0.0)
            // then bitwise convert start to integer.
            final long transducer = Double.doubleToRawLongBits(start + 0.0d);
            return Double.longBitsToDouble(transducer + ((transducer >= 0L) ? 1L : -1L));
        } else if (start == direction) {
            return direction;
        } else { // isNaN(start) || isNaN(direction)
            return start + direction;
        }
    }


    public static float nextAfter(float start, double direction) {
        // Branch to descending case first as it is more costly than ascending
        // case due to start != 0.0f conditional.
        if (start > direction) { // descending
            if (start != 0.0f) {
                final int transducer = Float.floatToRawIntBits(start);
                return Float.intBitsToFloat(transducer + ((transducer > 0) ? -1 : 1));
            } else { // start == 0.0f && direction < 0.0f
                return -Float.MIN_VALUE;
            }
        } else if (start < direction) { // ascending
            // Add +0.0 to get rid of a -0.0 (+0.0 + -0.0 => +0.0)
            // then bitwise convert start to integer.
            final int transducer = Float.floatToRawIntBits(start + 0.0f);
            return Float.intBitsToFloat(transducer + ((transducer >= 0) ? 1 : -1));
        } else if (start == direction) {
            return (float) direction;
        } else { // isNaN(start) || isNaN(direction)
            return start + (float) direction;
        }
    }


    public static double nextUp(double d) {
        // Use a single conditional and handle the likely cases first.
        if (d < Double.POSITIVE_INFINITY) {
            // Add +0.0 to get rid of a -0.0 (+0.0 + -0.0 => +0.0).
            final long transducer = Double.doubleToRawLongBits(d + 0.0D);
            return Double.longBitsToDouble(transducer + ((transducer >= 0L) ? 1L : -1L));
        } else { // d is NaN or +Infinity
            return d;
        }
    }


    public static float nextUp(float f) {
        // Use a single conditional and handle the likely cases first.
        if (f < Float.POSITIVE_INFINITY) {
            // Add +0.0 to get rid of a -0.0 (+0.0 + -0.0 => +0.0).
            final int transducer = Float.floatToRawIntBits(f + 0.0F);
            return Float.intBitsToFloat(transducer + ((transducer >= 0) ? 1 : -1));
        } else { // f is NaN or +Infinity
            return f;
        }
    }


    public static double nextDown(double d) {
        if (Double.isNaN(d) || d == Double.NEGATIVE_INFINITY)
            return d;
        else {
            if (d == 0.0)
                return -Double.MIN_VALUE;
            else
                return Double.longBitsToDouble(Double.doubleToRawLongBits(d) +
                        ((d > 0.0d) ? -1L : +1L));
        }
    }


    public static float nextDown(float f) {
        if (Float.isNaN(f) || f == Float.NEGATIVE_INFINITY)
            return f;
        else {
            if (f == 0.0f)
                return -Float.MIN_VALUE;
            else
                return Float.intBitsToFloat(Float.floatToRawIntBits(f) +
                        ((f > 0.0f) ? -1 : +1));
        }
    }


    public static double scalb(double d, int scaleFactor) {


        // magnitude of a power of two so large that scaling a finite
        // nonzero value by it would be guaranteed to over or
        // underflow; due to rounding, scaling down takes an
        // additional power of two which is reflected here
        final int MAX_SCALE = Double.MAX_EXPONENT + -Double.MIN_EXPONENT +
                DoubleConsts.SIGNIFICAND_WIDTH + 1;
        int exp_adjust = 0;
        int scale_increment = 0;
        double exp_delta = Double.NaN;

        // Make sure scaling factor is in a reasonable range

        if (scaleFactor < 0) {
            scaleFactor = Math.max(scaleFactor, -MAX_SCALE);
            scale_increment = -512;
            exp_delta = twoToTheDoubleScaleDown;
        } else {
            scaleFactor = Math.min(scaleFactor, MAX_SCALE);
            scale_increment = 512;
            exp_delta = twoToTheDoubleScaleUp;
        }

        // Calculate (scaleFactor % +/-512), 512 = 2^9, using
        // technique from "Hacker's Delight" section 10-2.
        int t = (scaleFactor >> 9 - 1) >>> 32 - 9;
        exp_adjust = ((scaleFactor + t) & (512 - 1)) - t;

        d *= powerOfTwoD(exp_adjust);
        scaleFactor -= exp_adjust;

        while (scaleFactor != 0) {
            d *= exp_delta;
            scaleFactor -= scale_increment;
        }
        return d;
    }


    public static float scalb(float f, int scaleFactor) {
        // magnitude of a power of two so large that scaling a finite
        // nonzero value by it would be guaranteed to over or
        // underflow; due to rounding, scaling down takes an
        // additional power of two which is reflected here
        final int MAX_SCALE = Float.MAX_EXPONENT + -Float.MIN_EXPONENT +
                FloatConsts.SIGNIFICAND_WIDTH + 1;

        // Make sure scaling factor is in a reasonable range
        scaleFactor = Math.max(Math.min(scaleFactor, MAX_SCALE), -MAX_SCALE);


        return (float) ((double) f * powerOfTwoD(scaleFactor));
    }

    // Constants used in scalb
    static double twoToTheDoubleScaleUp = powerOfTwoD(512);
    static double twoToTheDoubleScaleDown = powerOfTwoD(-512);


    static double powerOfTwoD(int n) {
        assert (n >= Double.MIN_EXPONENT && n <= Double.MAX_EXPONENT);
        return Double.longBitsToDouble((((long) n + (long) DoubleConsts.EXP_BIAS) <<
                (DoubleConsts.SIGNIFICAND_WIDTH - 1))
                & DoubleConsts.EXP_BIT_MASK);
    }


    static float powerOfTwoF(int n) {
        assert (n >= Float.MIN_EXPONENT && n <= Float.MAX_EXPONENT);
        return Float.intBitsToFloat(((n + FloatConsts.EXP_BIAS) <<
                (FloatConsts.SIGNIFICAND_WIDTH - 1))
                & FloatConsts.EXP_BIT_MASK);
    }
}
