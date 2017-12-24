package com.oubowu.ipanda.util;

import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Oubowu on 2017/12/21 22:40.
 */
public class LoggerInfoUtils {

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    @SuppressWarnings("unchecked")
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }

        if (object instanceof Collection) {
            Collection collection = (Collection) object;
            Iterator iterator = collection.iterator();
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
            while (iterator.hasNext()) {
                Object next = iterator.next();
                sb.append(toString(next));
                if (iterator.hasNext()) {
                    sb.append(",\n");
                }
            }
            sb.append("\n]");
            return sb.toString();
        }

        if (object instanceof Map) {
            Map map = (Map) object;
            Set<Map.Entry> set = map.entrySet();
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            Iterator<Map.Entry> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                sb.append(toString(entry.getKey()));
                sb.append("=");
                sb.append(toString(entry.getValue()));
                if (iterator.hasNext()) {
                    sb.append(",\n");
                }
            }
            sb.append("\n}");
            return sb.toString();
        }

        if (object.getClass().isArray()) {
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
                return formatArray((Object[]) object);
            }
        }

        return object.toString();

    }

    public static <T extends Object> String formatArray(T[] obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < obj.length; i++) {
            T t = obj[i];
            sb.append(toString(t));
            if (i < obj.length - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n]");
        return sb.toString();
    }
}

