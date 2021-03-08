package org.zuel.iemployee.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time: 2021/3/8 18:02
 * author: XinChen1899
 */

@Data
public class Remark {

    private int id;

    private int post;

    private int remark;

    private String remarkMaster;

    private String text;

    private String img;

    private int like;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private boolean deleted;
}
