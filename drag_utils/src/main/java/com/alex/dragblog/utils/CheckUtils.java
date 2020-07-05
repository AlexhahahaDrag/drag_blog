package com.alex.dragblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *description:  校验工具类
 *author:       alex
 *createDate:   2020/7/5 16:14
 *version:      1.0.0
 */
public class CheckUtils {

    /**
     * description :校验邮箱
     * author :     alex
     * @param :
     * @return :
     */
    public static boolean checkEmail(String email) {
        boolean flag;
        try {
            String check =  "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            return false;
        }
        return flag;
    }

    /**
     * description :校验手机号
     * author :     alex
     * @param :
     * @return :
     */
    public static boolean checkMobilePhone(String mobileNumber) {
        boolean flag;
        try {
            String check = "";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            return false;
        }
        return flag;
    }
}
