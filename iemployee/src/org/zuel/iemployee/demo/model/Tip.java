package org.zuel.iemployee.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time: 2021/3/8 18:05
 * author: XinChen1899
 */

@Data
public class Tip {

    private int id;

    private String name;

    private int zone;

    private int postNum;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private boolean deleted;
}
