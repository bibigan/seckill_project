package com.java.test;

import java.io.*;

public class CreateSignupFile {
    public static void main(String[] args) {
        String path="D:\\bishe\\测试\\zzz.txt";

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path)),
                    "UTF-8"));
            int x=1;
            for (;x<=1000;x++) {
                bw.write(x+","+"11111");
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.err.println("write errors :" + e);
        }
    }
}
