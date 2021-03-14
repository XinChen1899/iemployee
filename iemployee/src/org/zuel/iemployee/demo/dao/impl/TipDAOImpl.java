package org.zuel.iemployee.demo.dao.impl;

import org.zuel.iemployee.demo.dao.DBConnection;
import org.zuel.iemployee.demo.dao.FormatFactory;
import org.zuel.iemployee.demo.dao.TipDAO;
import org.zuel.iemployee.demo.model.Tip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * create time: 2021/3/8 20:58
 * author: XinChen1899
 */

public class TipDAOImpl implements TipDAO {

    private Connection conn;

    private PreparedStatement pst = null;

    private ResultSet result = null;

    private String sql;

    public TipDAOImpl() { conn = DBConnection.getConnection(); }

    private void release(ResultSet result, PreparedStatement pst){
        try{ pst.close(); result.close(); }
        catch(SQLException e) {}
    }

    private void release(PreparedStatement pst){
        try{ pst.close(); }
        catch(SQLException e) {}
    }

    @Override
    public boolean insertTip(Tip tip){
        tip.setCreateTime(LocalDateTime.now());
        try{
            sql = "INSERT INTO Tip(name,zone,post_num,create_time,deleted) VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, tip.getName());
            pst.setInt(2, tip.getZone());
            pst.setInt(3, tip.getPostNum());
            pst.setDate(4, FormatFactory.localDateTimeToSqlDate(tip.getCreateTime()));
            pst.setBoolean(5, tip.getDeleted());
            int i = pst.executeUpdate();
            System.out.printf("插入标签成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean deleteTip(Tip tip){
        try{
            sql = "UPDATE Tip SET deleted = 1 WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, tip.getId());
            int i = pst.executeUpdate();
            System.out.printf("删除标签成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean updateTip(Tip tip){
        try{
            sql = "UPDATE Tip SET name = ?, zone = ?, post_num = ?, update_time = ?, deleted = ?" +
                    " WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, tip.getName());
            pst.setInt(2, tip.getZone());
            pst.setInt(3, tip.getPostNum());
            pst.setDate(4, FormatFactory.localDateTimeToSqlDate(LocalDateTime.now()));
            pst.setBoolean(5, tip.getDeleted());
            pst.setInt(6, tip.getId());
            int i = pst.executeUpdate();
            System.out.printf("修改标签成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    private Tip selectOneTip(){
        Tip tip = new Tip();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            tip.setId(result.getInt(1));
            tip.setName(result.getString(2));
            tip.setZone(result.getInt(3));
            tip.setPostNum(result.getInt(4));
            tip.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(5)));
            tip.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(6)));
            tip.setDeleted(result.getBoolean(7));
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return tip;
    }

    @Override
    public Tip selectTipById(int id){
        sql = "SELECT * FROM Tip WHERE id = " + id;
        return selectOneTip();
    }

    @Override
    public Tip selectTipByName(String name){
        sql = "SELECT * FROM Tip WHERE name = \'" + name + '\'';
        return selectOneTip();
    }

    private List<Tip> selectTipList(){
        List<Tip> tips = new LinkedList<Tip>();
        Tip tip = new Tip();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            while(result.next()){
                tip.setId(result.getInt(1));
                tip.setName(result.getString(2));
                tip.setZone(result.getInt(3));
                tip.setPostNum(result.getInt(4));
                tip.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(5)));
                tip.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(6)));
                tip.setDeleted(result.getBoolean(7));
                tips.add(tip);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return tips;
    }

    @Override
    public List<Tip> selectTipByZone(int zone){
        sql = "SELECT * FROM Tip WHERE zone = " + zone;
        return selectTipList();
    }
}
