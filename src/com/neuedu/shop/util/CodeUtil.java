package com.neuedu.shop.util;

import java.util.UUID;

/**
 * @author 杜晓鹏
 * @create 2018-12-03 21:26
 */
public class CodeUtil {
    //生成唯一的激活码
    public static String generateUniqueCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
