package com.hellowy.oasys.server.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器
 *
 * @author wondercar
 * @date 2021/2/14
 */
@Component
public class DateUtil implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        // 年月日
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
