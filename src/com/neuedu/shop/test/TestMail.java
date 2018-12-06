package com.neuedu.shop.test;

import com.neuedu.shop.entity.User;
import com.neuedu.shop.util.CodeUtil;
import com.neuedu.shop.util.MailUtil;
import com.neuedu.shop.util.VerifyCodeUtils;

/**
 * @author 杜晓鹏
 * @create 2018-12-03 21:31
 */
public class TestMail {

    public static void main(String[] args) {
        String email = "335985836@qq.com";
        String str1 = "";
        //利用正则表达式（可改进）验证邮箱是否符合邮箱的格式
        if(!email.matches("^\\w+@(\\w+\\.)+\\w+$")){
            System.out.println("邮箱格式不对");
        }
//        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
//        System.out.println(verifyCode);
        String verifyCode1 = CodeUtil.generateUniqueCode();

        MailUtil mailUtil = new MailUtil(email, verifyCode1);
        mailUtil.run();
    }
}
