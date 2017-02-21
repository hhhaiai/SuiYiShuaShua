package com.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.text.TextUtils;

/**
 * @Copyright © 2015 Sanbo Inc. All rights reserved.
 * @Description: 跟App相关的辅助类
 * @Create: 2015年6月29日 下午6:34:53
 * @Author: sanbo
 */
public class AppUtils {

    private AppUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 根据 packageName 打开对应的app
     * 
     * @param context
     * @param packageName
     *            对应app Manifest package
     */
    public static void launchAppByPackageName(Context context, String packageName) {
        try {
            Object obj = new Intent("android.intent.action.MAIN", null);
            ((Intent) obj).addCategory("android.intent.category.LAUNCHER");
            ((Intent) obj).setPackage(packageName);
            obj = (ResolveInfo) context.getPackageManager().queryIntentActivities((Intent) obj, 0).iterator().next();
            if (obj != null) {
                packageName = ((ResolveInfo) obj).activityInfo.packageName;
                obj = ((ResolveInfo) obj).activityInfo.name;
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.addFlags(268435456);
                intent.setComponent(new ComponentName(packageName, (String) obj));
                context.startActivity(intent);
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动应用
     *
     * @param context
     * @param packageName
     */
    public static void launchApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    /**
     * 启动应用
     *
     * @param context
     * @param packageName
     * @param launchActivity
     */
    public static void launchApp(Context context, String packageName, String launchActivity) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, launchActivity));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取栈顶activity名字
     *
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context) {
        return ((ActivityManager) context.getSystemService(android.content.Context.ACTIVITY_SERVICE)).getRunningTasks(1)
                .get(0).topActivity.getClassName();
    }

    /**
     * 获取栈顶activity名字
     *
     * @param context
     * @return
     */
    public static String getTopActivityNameBPlan(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager = (ActivityManager) (context
                .getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            // 包ming
            // String packageName = f.getPackageName();
            // 完整类名
            topActivityClassName = f.getClassName();
            // 单独类名
            // String temp[] = topActivityClassName.split("\\.");
            // String topActivityName = temp[temp.length - 1];
            // System.out.println("topActivityName=" + topActivityName);
        }
        return topActivityClassName;
    }

    /**
     * 检查activity是否为栈顶activity
     * 
     * @param context
     * @param packageName
     * @param activityName
     * @return
     */
    public static boolean isTopActivity(Context context, String activityName) {

        try {
            String topActivity = getTopActivityName(context);
            if (!TextUtils.isEmpty(activityName) && topActivity.equalsIgnoreCase(activityName)) {
                L.d("isTopActivity  true");
                return true;
            }
        } catch (Throwable e) {
            L.e(e);
        }
        L.d("isTopActivity  false");
        return false;
    }

    /**
     * 检查activity是否为栈顶activity
     * 
     * @param context
     * @param packageName
     * @param activityName
     * @return
     */
    public static boolean isTopActivity(Context context, String packageName, String activityName) {

        try {
            String topActivity = getTopActivityName(context);
            String pack = context.getPackageName();
            if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(activityName)
                    && topActivity.equalsIgnoreCase(activityName) && pack.equalsIgnoreCase(packageName)) {
                return true;
            }
        } catch (Throwable e) {
            L.e(e);
        }
        return false;
    }

    /**
     * 获取系统中是否安装某些应用
     * 
     * @param context
     *            内容上下文
     * @param packageName
     *            判断是否安装的包名
     * @return
     */
    public static boolean isInstallApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (pi.applicationInfo.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取应用程序名称
     */

    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断设备是否为平板
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}