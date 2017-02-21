package com.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.text.TextUtils;

/**
 * @Copyright © 2016 sanbo Inc. All rights reserved.
 * @Description: hh
 * @Version: 1.0
 * @Create: 2016年10月24日 下午10:12:39
 * @Author: sanbo
 */
public class SafeiShell {
    /**
     * 非root执行shell脚本
     *
     * @param shell
     * @return
     */
    public static String shell(String shell) {
        if (TextUtils.isEmpty(shell)) {
            System.err.println("命令是空的,亲~");
            return null;
        }
        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            proc = Runtime.getRuntime().exec(shell);
            in = new BufferedInputStream(proc.getInputStream());
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {

                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    /**
     * root身份执行一句shell.要结果的那种.
     *
     * @param shell
     *            linux shell. 需要adb shell的那种
     * @return
     */
    public static String sudo(String shell) {
        if (TextUtils.isEmpty(shell)) {
            System.err.println("命令是空的,亲~");
            return null;
        }
        Process proc = null;
        BufferedInputStream in = null;
        DataOutputStream out = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            proc = Runtime.getRuntime().exec("su\n");
            out = new DataOutputStream(proc.getOutputStream());
            out.writeBytes(shell);
            out.flush();
            out.writeBytes("exit\n");
            out.flush();
            in = new BufferedInputStream(proc.getInputStream());
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {

                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    /**
     * 批量执行shell
     *
     * @param shells
     * @return
     */
    public static void sudo(String[] shells) {
        if (shells == null || shells.length < 1) {
            System.err.println("命令是空的,亲~");
            return;
        }

        Process proc = null;
        BufferedInputStream in = null;
        DataOutputStream out = null;
        BufferedReader br = null;
        try {
            proc = Runtime.getRuntime().exec("su\n");
            out = new DataOutputStream(proc.getOutputStream());
            for (int i = 0; i < shells.length; i++) {
                out.writeBytes(shells[i]);
                out.flush();
            }
            in = new BufferedInputStream(proc.getInputStream());
            br = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {

                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
