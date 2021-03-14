package org.zuel.iemployee.demo.dao;

import org.zuel.iemployee.demo.model.Company;
import org.zuel.iemployee.demo.model.User;

import java.util.List;
import java.util.Map;

/**
 * create time: 2021/3/8 20:47
 * author: XinChen1899
 */

public interface UserDAO {

    boolean insertUser(User user);

    boolean deleteUser(User user);

    boolean updateUser(User user);

    User selectUserById(String id);

    User selectUserByNickname(String nickname);

    List<User> selectUserByCompany(Company company);

    List<User> selectUserWithNicknameLike(String model);
}
