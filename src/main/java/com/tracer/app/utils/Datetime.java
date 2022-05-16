package com.tracer.app.utils;

public class Datetime {
    public static Long plus(Long time, Long plus){
        return time + plus * 24 * 60 * 60;
    }
}
