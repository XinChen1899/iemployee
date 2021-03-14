package org.zuel.iemployee.demo.dao.impl;

import org.zuel.iemployee.demo.dao.DBConnection;
import org.zuel.iemployee.demo.dao.FormatFactory;
import org.zuel.iemployee.demo.dao.UserDAO;
import org.zuel.iemployee.demo.model.Company;
import org.zuel.iemployee.demo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * create time: 2021/3/8 20:55
 * author: XinChen1899
 */

public class UserDAOImpl implements UserDAO {

    private Connection conn;

    private PreparedStatement pst = null;

    private ResultSet result = null;

    private String sql;

    public UserDAOImpl(){
        conn = DBConnection.getConnection();
    }

    private void release(ResultSet result, PreparedStatement pst){
        try{ pst.close(); result.close(); }
        catch(SQLException e) {}
    }

    private void release(PreparedStatement pst){
        try{ pst.close(); }
        catch(SQLException e) {}
    }

    @Override
    public boolean insertUser(User user){
        user.setCreateTime(LocalDateTime.now());
        user.setLoginTime(LocalDateTime.now());
        try{
            sql = "INSERT INTO User(id, nickname, company, remark, certification, login_time, create_time, deleted)" +
                    " Values(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getId());
            pst.setString(2, user.getNickname());
            pst.setInt(3, user.getCompany());
            pst.setString(4,user.getRemark());
            pst.setBoolean(5, user.getCertification());
            pst.setDate(6, FormatFactory.localDateTimeToSqlDate(user.getLoginTime()));
            pst.setDate(7, FormatFactory.localDateTimeToSqlDate(user.getCreateTime()));
            pst.setBoolean(8, user.getDeleted());
            int i = pst.executeUpdate();
            System.out.printf("新建用户成功，影响了%d行数据\n", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean deleteUser(User user){
        try{
            sql = "UPDATE User SET delete = 1 WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getId());
            int i = pst.executeUpdate();
            System.out.printf("删除用户成功，影响了%d行数据\n", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean updateUser(User user){
        user.setUpdateTime(LocalDateTime.now());
        try{
            sql = "UPDATE User SET nickname = ?, company = ?, remark = ?, certification = ?," +
                    " password = ?, login_time = ?, update_time = ?, deleted = ?" +
                    "WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getNickname());
            pst.setInt(2, user.getCompany());
            pst.setString(3, user.getRemark());
            pst.setBoolean(4, user.getCertification());
            pst.setString(5, user.getPassword());
            pst.setDate(6, FormatFactory.localDateTimeToSqlDate(user.getLoginTime()));
            pst.setDate(7, FormatFactory.localDateTimeToSqlDate(user.getUpdateTime()));
            pst.setBoolean(8, user.getDeleted());
            pst.setString(9, user.getId());
            int i = pst.executeUpdate();
            System.out.printf("修改用户数据成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            release(pst);
        }
        return true;
    }

    private User selectOneUser(){
        User user = new User();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            user.setId(result.getString(1));
            user.setNickname(result.getString(2));
            user.setCompany(result.getInt(3));
            user.setRemark(result.getString(4));
            user.setCertification(result.getBoolean(5));
            user.setPassword(result.getString(6));
            user.setLoginTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(7)));
            user.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(8)));
            user.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(9)));
            user.setDeleted(result.getBoolean(10));
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return user;
    }

    @Override
    public User selectUserById(String id){
        sql = "SELECT * FROM User WHERE id = \'" + id + "\' AND deleted != 1";
        return selectOneUser();
    }


    @Override
    public User selectUserByNickname(String nickname){
        sql = "SELECT * FROM User WHERE nickname = \'" + nickname +"\' AND deleted != 1";
        return selectOneUser();
    }

    private List<User> selectUserList(){
        List<User> users = new LinkedList<User>();
        User user = new User();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            while(result.next()){
                user.setId(result.getString(1));
                user.setNickname(result.getString(2));
                user.setCompany(result.getInt(3));
                user.setRemark(result.getString(4));
                user.setCertification(result.getBoolean(5));
                user.setPassword(result.getString(6));
                user.setLoginTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(7)));
                user.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(8)));
                user.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(9)));
                user.setDeleted(result.getBoolean(10));
                users.add(user);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return users;
    }

    @Override
    public List<User> selectUserByCompany(Company company){
        sql = "SELECT * FROM User WHERE company = " + company + " AND deleted != 1";
        return selectUserList();
    }

    @Override
    public List<User> selectUserWithNicknameLike(String model){
        sql = "SELECT * FROM User WHERE nickname LIKE + \'" + model + "\' AND deleted != 1";
        return selectUserList();
    }
}
