package com.co.planeador.service.util;

public class Utilities {

    private Utilities(){}

    public static boolean isNotNullOrEmptyString(String input){
        return null != input && !input.isBlank();
    }

}
