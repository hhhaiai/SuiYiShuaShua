package com.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * @Copyright © 2015 sanbo Inc. All rights reserved.
 * @Title: ShellHelper.java
 * @Description: 代码执行shell命令，安装卸载应用
 * @Version: 1.0
 * @Create: 2015年6月28日 下午3:33:03
 * @Author: sanbo
 */
public class ShellHelper {
    public static volatile ShellHelper mShellHelper = null;
    private String cmdInstall = "pm install -r ";
    private String cmdUninstall = "pm uninstall -k ";
    private String cmdLaunch = "am start";
    private String cmdStopApp = "am force-stop ";
    @SuppressWarnings("unused")
    private String cmdMainNote = " -a android.intent.action.MAIN -c android.intent.category.LAUNCHER";
    private String cmdInputSwipe = "input swipe ";
    private String cmdInputTap = "input tap ";
    private String cmdInputText = "input text ";
    private String cmdInputKeyevent = "input keyevent ";
    private String cmdLaunchService = "am startservice";
    private String cmdSendBroadCast = "am broadcast -a ";
    private String cmdClearPackageData = "pm clear";

    String cmd = null;

    private ShellHelper() {
    }

    public static ShellHelper getShellHelper() {
        if (mShellHelper == null) {
            synchronized (ShellHelper.class) {
                if (mShellHelper == null) {
                    mShellHelper = new ShellHelper();
                }
            }
        }
        return mShellHelper;
    }

    /**
     * 发送系统广播
     *
     * @param actionName
     *            广播action
     */
    public boolean sendBoradCast(String actionName) {
        cmd = cmdSendBroadCast + " " + actionName;
        return excuteCMDCommnd(cmd);
    }

    /**
     * 清除应用缓存
     *
     * @param packageName
     * @return
     */
    public boolean clearPackageData(String packageName) {
        cmd = cmdClearPackageData + " " + packageName;
        return excuteCMDCommnd(cmd);
    }

    /**
     * 启动service
     *
     * @param packageName
     *            service完整包名
     * @param serviceName
     *            service完整的类名
     */
    public boolean launchService(String packageName, String serviceName) {
        cmd = cmdLaunchService + " -n " + packageName + "/" + serviceName;
        return excuteCMDCommnd(cmd);
    }

    /**
     * 启动service
     *
     * @param actionName
     *            service的action
     */
    public boolean launchService(String actionName) {
        cmd = cmdLaunchService + " -a " + actionName;
        return excuteCMDCommnd(cmd);
    }

    /**
     * 安装软件
     *
     * @param finalPath
     *            apk完整名字,如/sdcard/safei/abc.apk
     * @return
     */
    public boolean installPackage(String finalPath) {
        cmd = cmdInstall + finalPath.trim();
        return excuteCMDCommnd(cmd);
    }

    /**
     * 卸载软件
     *
     * @param packageName
     *            包名,如本应用包名就是com.ss,读取自AndroidManifest.xml
     * @return
     */
    public boolean unInstallPackage(String packageName) {
        cmd = cmdUninstall + packageName.trim();
        return excuteCMDCommnd(cmd);
    }

    /**
     * 启动应用
     *
     * @param packageName
     *            包名
     * @param mainClassName
     *            主类名
     * @return
     */
    public boolean launchApp(String packageName, String mainClassName) {
        cmd = cmdLaunch + " -n " + packageName + "/" + mainClassName + " -f 0X00020000";// +cmdMainNote;
        return excuteCMDCommnd(cmd);
    }

    public boolean launchApp(Context context, String packageName, String mainClassName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, mainClassName);
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

    /**
     * 停止正在运行的app
     *
     * @param packageName
     *            包名
     * @return
     */
    public boolean stopApp(String packageName) {
        cmd = cmdStopApp + " " + packageName;
        return excuteCMDCommnd(cmd);
    }

    /**
     * 滑动事件
     *
     * @param beginX
     *            起始点的X坐标
     * @param beginY
     *            起始点的Y坐标
     * @param endX
     *            目标点的X坐标
     * @param endY
     *            目标点的Y坐标
     * @return
     */
    public boolean swipeMode(int beginX, int beginY, int endX, int endY) {
        cmd = cmdInputSwipe + " " + beginX + " " + beginY + " " + endX + " " + endY;
        return excuteCMDCommnd(cmd);
    }

    /**
     * 点击事件
     *
     * @param x
     *            点击点的X坐标
     * @param y
     *            点击点的Y坐标
     * @return
     */
    public boolean tapMode(double x, double y) {
        cmd = cmdInputTap + " " + x + " " + y;
        return excuteCMDCommnd(cmd);
    }

    /**
     * <pre>
     * 键入字符串
     *      eg:调用 inputKey("android"),在对话框等可以输入字符的地方自动键入android
     * </pre>
     *
     * @param text
     * @return
     */
    public boolean inputKey(String text) {
        cmd = cmdInputText + " " + text;
        return excuteCMDCommnd(cmd);
    }

    /**
     * 触发android提供的KeyEvent
     *
     * @param keycode
     *            KeyEvent
     * @return
     */
    public boolean keyeventMode(String keycode) {
        cmd = cmdInputKeyevent + " " + keycode;
        return excuteCMDCommnd(cmd);
    }

    public boolean excuteCMDCommnd(String cmd) {
        // try {
        // ProcessInstance.getInstance().getProcessData().os.writeBytes(cmd + ";
        // \n");
        // ProcessInstance.getInstance().getProcessData().os.flush();
        // } catch (Throwable e) {
        // e.printStackTrace();
        // }
        // try {
        // Thread.sleep(300);
        // } catch (Throwable e) {
        // e.printStackTrace();
        // }
        Shell.sudo(cmd);
        return false;
    }
}
