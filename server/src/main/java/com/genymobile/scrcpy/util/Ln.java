package com.genymobile.scrcpy.util;

import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Log both to Android logger (so that logs are visible in "adb logcat") and standard output/error (so that they are visible in the terminal
 * directly).
 */
public final class Ln {

    private static final String TAG = "scrcpy";
    private static final String PREFIX = "[server] ";

    private static final PrintStream CONSOLE_OUT = new PrintStream(new FileOutputStream(FileDescriptor.out));
    private static final PrintStream CONSOLE_ERR = new PrintStream(new FileOutputStream(FileDescriptor.err));

    public enum Level {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

    private static Level threshold = Level.INFO;

    private Ln() {
        // not instantiable
    }

    public static void disableSystemStreams() {
        PrintStream nullStream = new PrintStream(new NullOutputStream());
        System.setOut(nullStream);
        System.setErr(nullStream);
    }

    /**
     * Initialize the log level.
     * <p>
     * Must be called before starting any new thread.
     *
     * @param level the log level
     */
    public static void initLogLevel(Level level) {
        threshold = level;
    }

    public static boolean isEnabled(Level level) {
        return level.ordinal() >= threshold.ordinal();
    }

    public static void v(String message) {
        if (isEnabled(Level.VERBOSE)) {
            Log.v(TAG, extendMsg(message));
            CONSOLE_OUT.print(PREFIX + "VERBOSE: " + message + '\n');
        }
    }

    public static void d(String message) {
        if (isEnabled(Level.DEBUG)) {
            Log.d(TAG, extendMsg(message));
            CONSOLE_OUT.print(PREFIX + "DEBUG: " + message + '\n');
        }
    }

    public static void i(String message) {
        if (isEnabled(Level.INFO)) {
            Log.i(TAG, extendMsg(message));
            CONSOLE_OUT.print(PREFIX + "INFO: " + message + '\n');
        }
    }

    public static void w(String message, Throwable throwable) {
        if (isEnabled(Level.WARN)) {
            Log.w(TAG, message, throwable);
            CONSOLE_ERR.print(PREFIX + "WARN: " + message + '\n');
            if (throwable != null) {
                throwable.printStackTrace(CONSOLE_ERR);
            }
        }
    }

    public static void w(String message) {
        w(extendMsg(message), null);
    }

    public static void e(String message, Throwable throwable) {
        if (isEnabled(Level.ERROR)) {
            Log.e(TAG, message, throwable);
            CONSOLE_ERR.print(PREFIX + "ERROR: " + message + '\n');
            if (throwable != null) {
                throwable.printStackTrace(CONSOLE_ERR);
            }
        }
    }

    public static void e(String message) {
        e(extendMsg(message), null);
    }

    private static String extendMsg(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 5) {
            // Fallback to a default message if the stack trace is too short
            return String.format("[Unknown.Unknown] %s", msg);
        }
        StackTraceElement element = stackTrace[4]; // 3 is the index for the caller method
        String className = getSimpleClassName(element.getClassName());
        String methodName = element.getMethodName();
        return String.format("[%s.%s] %s", className, methodName, msg);
    }

    private static String getSimpleClassName(String fullClassName) {
        if (fullClassName == null || fullClassName.isEmpty()) {
            return "Unknown";
        }
        String[] parts = fullClassName.split("\\.");
        if (parts.length == 0) {
            return "Unknown";
        }
        return parts[parts.length - 1];
    }

    static class NullOutputStream extends OutputStream {
        @Override
        public void write(byte[] b) {
            // ignore
        }

        @Override
        public void write(byte[] b, int off, int len) {
            // ignore
        }

        @Override
        public void write(int b) {
            // ignore
        }
    }
}
