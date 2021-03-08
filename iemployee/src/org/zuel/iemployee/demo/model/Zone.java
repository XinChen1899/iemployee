package org.zuel.iemployee.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time: 2021/3/8 18:06
 * author: XinChen1899
 */

@Data
public class Zone {

    private int id;

    private String name;

    private String remark;

    private int postNum;

    private String hotPost;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private boolean deleted;
}
