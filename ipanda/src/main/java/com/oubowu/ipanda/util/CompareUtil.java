package com.oubowu.ipanda.util;

/**
 * Created by Oubowu on 2017/12/30 23:00.
 */
public class CompareUtil {

    public static boolean equals(Object o1,Object o2){
        if (o1==null){
            return o2==null;
        }
        if (o2==null){
            return false;
        }
        return o1.equals(o2);
    }

}
