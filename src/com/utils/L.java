package com.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Formatter;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

/**
 * @Copyright © 2015 Sanbo Inc. All rights reserved.
 * @Description: Log统一管理类{log工具类支持全部打印/支持类似C的格式化输出或Java的String.format/支持Error打印/支持键入和不键入TAG}
 * @Version: 1.0
 * @Create: 2015年6月18日 下午4:14:01
 * @Author: sanbo
 */
public class L {

    private L() {
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static String DEFAULT_TAG = "sanbo";
    // 规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;
    private static String SPACE = "    ";

    public static final class MLEVEL {
        public static final int VERBOSE = 0x1;
        public static final int DEBUG = 0x2;
        public static final int INFO = 0x3;
        public static final int WARN = 0x4;
        public static final int ERROR = 0x5;
    }

    public static void v(JSONObject obj) {
        if (obj != null && obj.length() > 0) {
            print(MLEVEL.VERBOSE, obj, null, null);
        }
    }

    public static void d(JSONObject obj) {
        if (obj != null && obj.length() > 0) {
            print(MLEVEL.DEBUG, obj, null, null);
        }
    }

    public static void w(JSONObject obj) {
        if (obj != null && obj.length() > 0) {
            print(MLEVEL.WARN, obj, null, null);
        }
    }

    public static void i(JSONObject obj) {
        if (obj != null && obj.length() > 0) {
            print(MLEVEL.INFO, obj, null, null);
        }
    }

    public static void e(JSONObject obj) {
        if (obj != null && obj.length() > 0) {
            print(MLEVEL.ERROR, obj, null, null);
        }
    }

    public static void d(String format, Object... args) {
        try {
            String result = null;
            if (format.contains("%")) {
                new Formatter(Locale.getDefault()).format(format, args).toString();
            } else {
                if (args != null) {
                    result = (String) args[0];
                }
            }
            if (!TextUtils.isEmpty(result)) {
                print(MLEVEL.DEBUG, result, null);
            }
        } catch (Exception e) {
        }
    }

    public static void i(String format, Object... args) {
        try {
            String result = null;
            if (format.contains("%")) {
                new Formatter(Locale.getDefault()).format(format, args).toString();
            } else {
                if (args != null) {
                    result = (String) args[0];
                }
            }
            if (!TextUtils.isEmpty(result)) {
                print(MLEVEL.INFO, result, null);
            }
        } catch (Exception e) {
        }
    }

    public static void w(String format, Object... args) {
        try {
            String result = null;
            if (format.contains("%")) {
                new Formatter(Locale.getDefault()).format(format, args).toString();
            } else {
                if (args != null) {
                    result = (String) args[0];
                }
            }
            if (!TextUtils.isEmpty(result)) {
                print(MLEVEL.WARN, result, null);
            }
        } catch (Exception e) {
        }
    }

    public static void v(String format, Object... args) {
        try {
            String result = null;
            if (format.contains("%")) {
                new Formatter(Locale.getDefault()).format(format, args).toString();
            } else {
                if (args != null) {
                    result = (String) args[0];
                }
            }
            if (!TextUtils.isEmpty(result)) {
                print(MLEVEL.VERBOSE, result, null);
            }
        } catch (Exception e) {
        }
    }

    public static void e(String format, Object... args) {
        try {
            String result = null;
            if (format.contains("%")) {
                new Formatter(Locale.getDefault()).format(format, args).toString();
            } else {
                if (args != null) {
                    result = (String) args[0];
                }
            }
            if (!TextUtils.isEmpty(result)) {
                print(MLEVEL.ERROR, result, null);
            }
        } catch (Exception e) {
        }
    }

    public static void i(Throwable e) {
        print(MLEVEL.INFO, null, e);
    }

    public static void v(Throwable e) {
        print(MLEVEL.VERBOSE, null, e);
    }

    public static void w(Throwable e) {
        print(MLEVEL.WARN, null, e);
    }

    public static void d(Throwable e) {
        print(MLEVEL.DEBUG, null, e);
    }

    public static void e(Throwable e) {
        print(MLEVEL.ERROR, null, e);
    }

    public static void i(String msg, Throwable e) {
        print(MLEVEL.INFO, msg, e);
    }

    public static void v(String msg, Throwable e) {
        print(MLEVEL.VERBOSE, msg, e);

    }

    public static void w(String msg, Throwable e) {
        print(MLEVEL.WARN, msg, e);
    }

    public static void d(String msg, Throwable e) {
        print(MLEVEL.DEBUG, msg, e);
    }

    public static void e(String msg, Throwable e) {
        print(MLEVEL.ERROR, msg, e);
    }

    public static void v(String msg) {
        print(MLEVEL.VERBOSE, msg, null);
    }

    public static void d(String msg) {
        print(MLEVEL.DEBUG, msg, null);
    }

    public static void i(String msg) {
        print(MLEVEL.INFO, msg, null);
    }

    public static void w(String msg) {
        print(MLEVEL.WARN, msg, null);
    }

    public static void e(String msg) {
        print(MLEVEL.ERROR, msg, null);
    }

    public static void v(String tag, String msg, Throwable e) {
        try {
            JSONObject obj = new JSONObject(msg);
            print(MLEVEL.VERBOSE, obj, null, tag);
        } catch (JSONException e1) {
            print(MLEVEL.VERBOSE, msg, null, tag);
        }
    }

    public static void d(String tag, String msg, Throwable e) {
        try {
            JSONObject obj = new JSONObject(msg);
            print(MLEVEL.DEBUG, obj, null, tag);
        } catch (JSONException e1) {
            print(MLEVEL.DEBUG, msg, null, tag);
        }
    }

    public static void i(String tag, String msg, Throwable e) {
        try {
            JSONObject obj = new JSONObject(msg);
            print(MLEVEL.INFO, obj, null, tag);
        } catch (JSONException e1) {
            print(MLEVEL.INFO, msg, null, tag);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        try {
            JSONObject obj = new JSONObject(msg);
            print(MLEVEL.WARN, obj, null, tag);
        } catch (JSONException e1) {
            print(MLEVEL.WARN, msg, null, tag);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        try {
            JSONObject obj = new JSONObject(msg);
            print(MLEVEL.ERROR, obj, null, tag);
        } catch (JSONException e1) {
            print(MLEVEL.ERROR, msg, null, tag);
        }
    }

    private static void print(int level, String msg, Throwable e) {
        try {
            JSONObject obj = new JSONObject(msg);
            print(level, obj, e, DEFAULT_TAG);
        } catch (JSONException e1) {
            print(level, msg, e, DEFAULT_TAG);
        }

    }

    private static void print(int level, JSONObject obj, Throwable e, String tag) {
        if (isDebug) {
            // String msg = format(convertUnicode(obj.toString()));
            String msg = format(obj);
            // String msg = JsonFormatTool.formatJson(obj.toString());

            print(level, msg, e, tag);
        }
    }

    private static void print(int level, String msg, Throwable e, String tag) {

        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                // 剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    switch (level) {
                    case MLEVEL.DEBUG:
                        Log.d((tag == null ? DEFAULT_TAG : tag) + i, msg.substring(start, end));
                        break;
                    case MLEVEL.INFO:
                        Log.i((tag == null ? DEFAULT_TAG : tag) + i, msg.substring(start, end));
                        break;
                    case MLEVEL.ERROR:
                        Log.e((tag == null ? DEFAULT_TAG : tag) + i, msg.substring(start, end));
                        break;
                    case MLEVEL.VERBOSE:
                        Log.v((tag == null ? DEFAULT_TAG : tag) + i, msg.substring(start, end));
                        break;
                    case MLEVEL.WARN:
                        Log.w((tag == null ? DEFAULT_TAG : tag) + i, msg.substring(start, end));
                        break;

                    default:
                        break;
                    }
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    switch (level) {
                    case MLEVEL.DEBUG:
                        Log.d(tag == null ? DEFAULT_TAG : tag, msg.substring(start, strLength));
                        break;
                    case MLEVEL.INFO:
                        Log.i(tag == null ? DEFAULT_TAG : tag, msg.substring(start, strLength));
                        break;
                    case MLEVEL.ERROR:
                        Log.e(tag == null ? DEFAULT_TAG : tag, msg.substring(start, strLength));
                        break;
                    case MLEVEL.VERBOSE:
                        Log.v(tag == null ? DEFAULT_TAG : tag, msg.substring(start, strLength));
                        break;
                    case MLEVEL.WARN:
                        Log.w(tag == null ? DEFAULT_TAG : tag, msg.substring(start, strLength));
                        break;

                    default:
                        break;
                    }
                    break;
                }
                if (e != null) {
                    StackTraceElement[] stackTrace = e.getStackTrace();
                    for (StackTraceElement s : stackTrace) {
                        switch (level) {
                        case MLEVEL.DEBUG:
                            Log.d(tag == null ? DEFAULT_TAG : tag, "\t\tat\t" + s.toString());
                            break;
                        case MLEVEL.INFO:
                            Log.i(tag == null ? DEFAULT_TAG : tag, "\t\tat\t" + s.toString());
                            break;
                        case MLEVEL.ERROR:
                            Log.e(tag == null ? DEFAULT_TAG : tag, "\t\tat\t" + s.toString());
                            break;
                        case MLEVEL.VERBOSE:
                            Log.v(tag == null ? DEFAULT_TAG : tag, "\t\tat\t" + s.toString());
                            break;
                        case MLEVEL.WARN:
                            Log.w(tag == null ? DEFAULT_TAG : tag, "\t\tat\t" + s.toString());
                            break;

                        default:
                            break;
                        }
                    }

                }
            }
        }
    }

    /**
     * 将error转换成字符串
     */
    @SuppressWarnings("unused")
    private static String getStackTrace(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 格式化输出json
     *
     * @param jsonStr
     * @return
     * @author lizhgb
     * @Date 2015-10-14 下午1:17:35
     */
    public static String format(JSONObject obj) {
        String jsonStr = obj.toString();
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char llast = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            llast = last;
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
            case '{':
            case '[':
                // if (condition) {
                //
                // }
                sb.append(current);
                sb.append('\n');
                indent++;
                addIndentBlank(sb, indent);
                break;
            case '"':
                sb.append(current);
                if (last == ',' && llast == '}') {
                    sb.append('\n');
                    addIndentBlank(sb, indent);
                }
                break;
            case '}':
            case ']':
                sb.append('\n');
                indent--;
                addIndentBlank(sb, indent);
                sb.append(current);
                break;
            case ',':
                sb.append(current);

                switch (last) {
                case '\\':

                    break;
                case ']':
                    // 支持JsonArray
                    sb.append('\n');
                    addIndentBlank(sb, indent);
                    break;
                case '"':
                    // 支持json Value里多个,的
                    if (llast != ':') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;

                default:
                    break;
                }
                break;
            default:
                sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     * @author lizhgb
     * @Date 2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append(SPACE);
        }
    }

}