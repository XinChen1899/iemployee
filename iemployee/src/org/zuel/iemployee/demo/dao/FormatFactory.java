package org.zuel.iemployee.demo.dao;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * create time: 2021/3/13 9:04
 * author: XinChen1899
 */

public class FormatFactory {

    public static Date localDateTimeToSqlDate(LocalDateTime localDateTime){
        return new Date(localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli());
    }

    public static LocalDateTime SqlDateToLocalDateTime(Date date){
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
