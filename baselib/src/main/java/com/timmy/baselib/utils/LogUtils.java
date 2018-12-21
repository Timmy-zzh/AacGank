package com.timmy.baselib.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Arrays;

/**
 * @author: lex
 * @Email: ldlywt@gmail.com
 * @Time: 2018/1/11 10:56
 * @Description: 对logger的库进行一层包装，方便以后更换其他库
 */
public class LogUtils {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private static final String TAG_DEFAULT = "LogUtils";
    private static boolean isShowThread = false;
    private static String tag = "";
    private static boolean logEnable = true;
    private static final char HORIZONTAL_LINE = '│';

    private static final int MIN_STACK_OFFSET = 3;

    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        log(DEBUG, null, message, args);
    }

    public static void d(@Nullable Object object) {
        log(DEBUG, null, LogUtils.toString(object));
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        e(null, message, args);
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        log(ERROR, throwable, message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        log(WARN, null, message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        log(INFO, null, message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        log(VERBOSE, null, message, args);
    }

    public static void wtf(@NonNull String message, @Nullable Object... args) {
        log(ASSERT, null, message, args);
    }

    @NonNull
    private static String createMessage(@NonNull String message, @Nullable Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

    private synchronized static void log(int priority,
                                         @Nullable Throwable throwable,
                                         @NonNull String msg,
                                         @Nullable Object... args) {
        if (!logEnable) {
            return;
        }
        checkNotNull(msg);
        String tag = TAG_DEFAULT;
        String message = createMessage(msg, args);
        StringBuilder builder = new StringBuilder();

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (isShowThread) {
            builder.append(HORIZONTAL_LINE + " Thread:" + Thread.currentThread().getName());
        }
        int stackOffset = getStackOffset(trace);
        int methodCount = 1;
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            builder.append(" " + HORIZONTAL_LINE + " ")
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))  //类名
                    .append(".")
                    .append(trace[stackIndex].getMethodName())   //方法名
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())   //
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
        }

        builder.append(HORIZONTAL_LINE + " " + message);
        Log.println(priority, tag, builder.toString());
    }

    private static int getStackOffset(@NonNull StackTraceElement[] trace) {
        checkNotNull(trace);

        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LogUtils.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private static String getSimpleClassName(@NonNull String name) {
        checkNotNull(name);

        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }

    @NonNull
    static <T> T checkNotNull(@Nullable final T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }
}
