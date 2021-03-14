package org.zuel.iemployee.demo.constant;

/**
 * create time: 2021/3/10 21:39
 * author: XinChen1899
 */

public class UserConstant {

    // 用户名已存在
    public static final int USERNAME_EXIST_CODE = 1001;
    public static final String USERNAME_EXIST_ERROR = "用户名已存在";

    // 用户名不存在
    public static final int USERNAME_NOT_EXIST_CODE = 1002;
    public static final String USERNAME_NOT_EXIST_ERROR = "用户名不存在";

    // 账号或密码错误
    public static final int USER_INVALID_CODE = 1003;
    public static final String USER_INVALID_ERROR = "账号或密码错误";

    // 用户未认证
    public static final int USER_NOT_CERTIFICATED_CODE = 1004;
    public static final String USER_NOT_CERTIFICATED_ERROR = "用户未认证";

    // 用户认证失败
    public static final int USER_CERTIFICATED_UNSUCCESSFULLY_CODE = 1005;
    public static final String USER_CERTIFICATED_UNSUCCESSFULLY_ERROR = "用户认证失败";
}
