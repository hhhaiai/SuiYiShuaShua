package com.shuashua;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import c.app.AppUtils;
import c.shell.SafeiShell;
import c.shell.ShellHelper;

public class MainActivity extends Activity {
    public static final String T = "sanbo";
    private static ExecutorService uiPools = Executors.newSingleThreadExecutor();
    protected static final int MSG_REFRESH_UI = 0x999;
    private Context mContext = null;
    private int w = -1;
    private int h = -1;
    private int dpi = -1;
    // 收听曲目时常10秒
    private long dur = 15 * 1000;
    private String mPackageName = "com.netease.cloudmusic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        uiPools.submit(new Runnable() {

            @Override
            public void run() {
                questPermission();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnBegin:
            startService(new Intent(this, WindowService.class));
            StringBuilder sb = new StringBuilder();
            Resources resources = this.getResources();
            DisplayMetrics dmqq = resources.getDisplayMetrics();
            w = dmqq.widthPixels;
            h = dmqq.heightPixels;
            dpi = dmqq.densityDpi;
            sb.append("widthPixels:").append(w).append("\n");
            sb.append("heightPixels:").append(h).append("\n");
            sb.append("densityDpi:").append(dpi).append("\n");
            LOGI(sb.toString());
            uiPools.submit(new Runnable() {

                @Override
                public void run() {
                    if (w == 720 && h == 1280 && dpi == 320) {
                        wangYiMusic2();
                    } else {
                        LOGI("不支持的分辨率");
                    }
                }

            });
            break;

        default:
            break;
        }
    }

    protected void wangYiMusic2() {
        if (AppUtils.isInstallApp(mContext, mPackageName)) {
            LOGI("测试方法2 安装了【网易云音乐】");
            ShellHelper.getShellHelper().stopApp(mPackageName);
            ShellHelper.getShellHelper().launchApp("com.netease.cloudmusic",
                    "com.netease.cloudmusic.activity.PlayerRadioActivity");
            /**
             * 等待进入播放界面
             */
            for (int i = 0; i < 5; i++) {
                // 播放音乐界面 505 1180(下一首歌)
                // com.netease.cloudmusic/.activity.PlayerRadioActivity
                if (AppUtils.isTopActivity(mContext, "com.netease.cloudmusic.activity.PlayerRadioActivity")) {
                    sleep(1000);
                    // ShellHelper.getShellHelper().tapMode(365, 1180);
                    ShellHelper.getShellHelper().tapMode(414, 1075);
                    break;
                } else {
                    LOGI("不是主页,置顶页面是:" + AppUtils.getTopActivityName(mContext));
                    sleep(2000);
                }
            }
            for (int j = 0; j < Integer.MAX_VALUE; j++) {
                if (j == 0) {
                    sleep(dur / 5);
                    ShellHelper.getShellHelper().tapMode(505, 1180);
                }
                if (AppUtils.isTopActivity(mContext, "com.netease.cloudmusic.activity.PlayerRadioActivity")) {
                    sleep(dur / 3);
                    ShellHelper.getShellHelper().tapMode(414, 1075);
                    sleep(dur / 3 * 2);
                    ShellHelper.getShellHelper().tapMode(505, 1180);
                } else {
                    wangYiMusic2();
                    break;
                }
            }
        } else {
            LOGI("测试方法2 没有安装【网易云音乐】");
        }
    }

    @SuppressWarnings("unused")
    private void wangYiMusic1() {
        if (AppUtils.isInstallApp(mContext, mPackageName)) {
            LOGI("测试方法1 安装了【网易云音乐】");
            ShellHelper.getShellHelper().stopApp(mPackageName);
            AppUtils.launchApp(mContext, mPackageName);
            /**
             * 等待到主页面
             */
            for (int i = 0; i < 5; i++) {
                // 广告界面 跳过坐标611 1060 可以等3-5秒过
                // com.netease.cloudmusic/.activity.LoadingActivity
                // 主界面 110 590(私人FM)
                // com.netease.cloudmusic/.activity.MainActivity
                // 播放音乐界面 505 1180(下一首歌)
                // com.netease.cloudmusic/.activity.PlayerRadioActivity
                if (AppUtils.isTopActivity(mContext, "com.netease.cloudmusic.activity.MainActivity")) {
                    sleep(1000);
                    break;
                } else {
                    LOGI("wangYiYun1 不是主页,置顶页面是:" + AppUtils.getTopActivityName(mContext));
                    sleep(2000);
                }
            }
            /**
             * 点击(私人FM)
             */
            ShellHelper.getShellHelper().tapMode(110, 590);
            /**
             * 等待进入播放界面
             */
            for (int i = 0; i < 5; i++) {
                // 播放音乐界面 505 1180(下一首歌)
                // com.netease.cloudmusic/.activity.PlayerRadioActivity
                if (AppUtils.isTopActivity(mContext, "com.netease.cloudmusic.activity.PlayerRadioActivity")) {
                    sleep(1000);
                    break;
                } else {
                    LOGI("不是主页,置顶页面是:" + AppUtils.getTopActivityName(mContext));
                    sleep(2000);
                }
            }
            for (int j = 0; j < Integer.MAX_VALUE; j++) {
                sleep(dur);
                ShellHelper.getShellHelper().tapMode(505, 1180);
            }

        } else {
            LOGI("测试方法1 没有安装【网易云音乐】");
        }
    }

    protected void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected void questPermission() {
        try {
            String[] shells = new String[] { "mount -o remount,rw /system", "mount -o remount, rw /",
                    "mount -o rw,remount -t ext3 /dev/block/mmcblk1p21 /system",
                    "mount -o remount,rw /dev/block/mtdblock2 /system", "chmod 777 /dev/input/*",
                    "chmod 777 /dev/alarm", "chmod 777 /dev/*", "chmod 777 /dev/block/mmcblk0" };
            SafeiShell.sudo(shells);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LOGI(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.i(T, "log string is empty!");
        } else {
            Log.i(T, str);
        }

    }

}
