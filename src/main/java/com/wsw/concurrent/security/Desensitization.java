package com.wsw.concurrent.security;

import com.wsw.concurrent.model.Request;
import com.wsw.concurrent.utils.SecurityUtil;

/**
 * @Author WangSongWen
 * @Date: Created in 14:23 2021/3/15
 * @Description:
 */
public class Desensitization {
    public static void main(String[] args) {
        Request request = new Request();
        request.setName("王松文");
        request.setPhone("12345678909");
        request.setIdCard("432156788909084123");
        request.setImg("img2021");
        SecurityUtil securityUtil = new SecurityUtil();
        String result = securityUtil.toJsonString(request);
        System.out.println(result);
    }
}
