package org.zuel.iemployee.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time: 2021/3/8 14:53
 * author: XinChen1899
 */

@Data
public class Company {

    private int id;

    private String name;

    private String remark;

    private float hardware;

    private float environment;

    private float scheduleArrange;

    private float score;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private boolean deleted;
}
