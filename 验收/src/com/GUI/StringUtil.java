package com.GUI;

public class StringUtil {
    //判断字符串是否为空
    //trim()是去掉字符串首尾空格
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

}
