package com.example;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linyuan on 2017/10/26 0026.
 */


enum Season
{
    SPRING,SUMMER,FALL,WINTER
}



public class MapTest {

    public static void main(String[] args) {
        EnumMap enumMap = new EnumMap(Season.class);
        enumMap.put(Season.SUMMER,"夏日妍妍");
        enumMap.put(Season.SPRING,"春暖花开");

        System.out.println(enumMap);

    }
}
