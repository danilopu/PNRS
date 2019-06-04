package com.example.vremenska;

public class MyNDK {

    static {
        System.loadLibrary("MyJNI");
    }

    public native int conversion (int t, int c);
}
