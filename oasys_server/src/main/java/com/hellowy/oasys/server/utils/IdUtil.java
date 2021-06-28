package com.hellowy.oasys.server.utils;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @author wondercar
 * @date 2021/1/31
 */
public class IdUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
