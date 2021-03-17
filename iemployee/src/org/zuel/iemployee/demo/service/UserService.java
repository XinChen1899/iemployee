package org.zuel.iemployee.demo.service;

import org.zuel.iemployee.demo.constant.UserConstant;
import org.zuel.iemployee.demo.dao.UserDAO;
import org.zuel.iemployee.demo.dao.impl.UserDAOImpl;
import org.zuel.iemployee.demo.model.Company;
import org.zuel.iemployee.demo.model.Post;
import org.zuel.iemployee.demo.model.ResponseObj;
import org.zuel.iemployee.demo.model.User;
import org.zuel.iemployee.demo.util.ResponseUtil;

import java.util.List;

/**
 * create time: 2021/3/8 20:42
 * author: XinChen1899
 */

public class UserService {

    private static UserDAO userDao = new UserDAOImpl();

    /**
     * 登录
     * @param user
     * @return
     */
    public static ResponseObj login(User user){
        if(user.getId() == null){
            return ResponseUtil.badArgument();
        }
        User model = userDao.selectUserById(user.getId());
        if(model == null){
            return ResponseUtil.fail(UserConstant.USERNAME_NOT_EXIST_CODE, UserConstant.USERNAME_NOT_EXIST_ERROR);
        }
        if(!model.getId().equals(user.getId()) && !model.getPassword().equals(user.getPassword())){
            return ResponseUtil.fail(UserConstant.USER_INVALID_CODE, UserConstant.USER_INVALID_ERROR);
        }
        user = model;
        return ResponseUtil.ok("登陆成功",model);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public static ResponseObj regist(User user){
        if(user == null){
            return ResponseUtil.badArgument();
        }
        if(!userDao.insertUser(user)){
            return ResponseUtil.fail(UserConstant.USERNAME_EXIST_CODE, UserConstant.USERNAME_EXIST_ERROR);
        }
        return ResponseUtil.ok("注册成功", user);
    }

    /**
     * 注销
     * @param user
     * @return
     */
    public static ResponseObj close(User user){
        if(user == null || user.getId() == null){
            return ResponseUtil.badArgument();
        }
        if(!userDao.deleteUser(user)){
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok("删除成功", user);
    }

    /**
     * 查找楼主
     * @param post
     * @param user
     * @return
     */
    public static ResponseObj searchPostMaster(Post post, User user){
        if(post == null || post.getPostMaster() == null){
            return ResponseUtil.badArgument();
        }
        String postMasterId = post.getPostMaster();
        user = userDao.selectUserById(postMasterId);
        if(user == null || user.getId() == null){
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok("操作成功", user);
    }

    /**
     * 查找某公司所有用户
     * @param company
     * @param userList
     * @return
     */
    public static ResponseObj searchByCompany(Company company, List<User> userList){
        if(company == null){
            return ResponseUtil.badArgument();
        }
        userList = userDao.selectUserByCompany(company);
        return ResponseUtil.ok("操作成功", userList);
    }

    /**
     * 按昵称查找用户
     * @param model
     * @param userList
     * @return
     */
    public static ResponseObj searchByNickname(String model, List<User> userList){
        if(model == null){
            return ResponseUtil.badArgument();
        }
        userList = userDao.selectUserWithNicknameLike(model);
        return ResponseUtil.ok("操作成功", userList);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public static ResponseObj updateUserInfo(User user){
        if(user == null || user.getId() == null){
            return ResponseUtil.badArgument();
        }
        if(!userDao.updateUser(user)){
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok("操作成功", user);
    }
}
