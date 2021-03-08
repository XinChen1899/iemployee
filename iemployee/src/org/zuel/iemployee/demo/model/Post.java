package org.zuel.iemployee.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time: 2021/3/8 15:02
 * author: XinChen1899
 */

@Data
public class Post {

    private int id;

    private String title;

    private String postMaster;

    private String zone;

    private int company;

    private String text;

    private String img;

    private int visitNum;

    private int tip;

    private int like;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private boolean deleted;
}
