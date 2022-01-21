package air.svran.hook.miui.aod;

import android.util.Log;

public class LogUtils {
    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;

    private LogUtils() {
    }

    private static String generateTag(StackTraceElement caller) {
        return logHeaderContent();
    }

    private static String logHeaderContent() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        int stackOffset = getStackOffset(trace) + 1;

        StringBuilder builder = new StringBuilder();

        if (stackOffset >= trace.length) {
            stackOffset = trace.length - 1;
        }
        if (stackOffset == trace.length || stackOffset < 0) return "Svran !!!";
        builder.append("Svran")
                .append(" (")
                .append(trace[stackOffset].getFileName())
                .append(":")
                .append(trace[stackOffset].getLineNumber())
                .append(")");
        return builder.toString();
    }

    static <T> T checkNotNull(final T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    private static int getStackOffset(StackTraceElement[] trace) {
        checkNotNull(trace);

        for (int i = 5; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LogUtils.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    public static void d(String content) {
        if (allowD) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.d(tag, content);
        }
    }

    public static void d(String content, Throwable tr) {
        if (allowD) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.d(tag, content, tr);
        }
    }

    public static void e(String content) {
        if (allowE) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.e(tag, content);
        }
    }

    public static void e(String content, Throwable tr) {
        if (allowE) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.e(tag, content, tr);
        }
    }

    public static void i(String content) {
        if (allowI) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.i(tag, content);
        }
    }

    public static void i(String content, Throwable tr) {
        if (allowI) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.i(tag, content, tr);
        }
    }

    public static void v(String content) {
        if (allowV) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.v(tag, content);
        }
    }

    public static void v(String content, Throwable tr) {
        if (allowV) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.v(tag, content, tr);
        }
    }

    public static void w(String content) {
        if (allowW) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.w(tag, content);
        }
    }

    public static void w(String content, Throwable tr) {
        if (allowW) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.w(tag, content, tr);
        }
    }

    public static void w(Throwable tr) {
        if (allowW) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.w(tag, tr);
        }
    }

    public static void wtf(String content) {
        if (allowWtf) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.wtf(tag, content);
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (allowWtf) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.wtf(tag, content, tr);
        }
    }

    public static void wtf(Throwable tr) {
        if (allowWtf) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String tag = generateTag(caller);
            Log.wtf(tag, tr);
        }
    }
}
