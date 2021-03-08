package org.zuel.iemployee.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time: 2021/3/8 14:25
 * author: XinChen1899
 */

@Data
public class User {

    private String id;

    private String nickname;

    private int company;

    private String remark;

    private boolean certification;

    private String password;

    private LocalDateTime loginTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private boolean deleted;

}
