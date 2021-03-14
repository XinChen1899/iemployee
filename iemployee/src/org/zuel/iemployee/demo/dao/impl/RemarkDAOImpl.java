package org.zuel.iemployee.demo.dao.impl;

import org.zuel.iemployee.demo.dao.DBConnection;
import org.zuel.iemployee.demo.dao.FormatFactory;
import org.zuel.iemployee.demo.dao.RemarkDAO;
import org.zuel.iemployee.demo.model.Remark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * create time: 2021/3/8 20:56
 * author: XinChen1899
 */

public class RemarkDAOImpl implements RemarkDAO {

    private Connection conn;

    private PreparedStatement pst = null;

    private ResultSet result = null;

    private String sql;

    public RemarkDAOImpl() { conn = DBConnection.getConnection(); }

    private void release(ResultSet result, PreparedStatement pst){
        try{ pst.close(); result.close(); }
        catch(SQLException e) {}
    }

    private void release(PreparedStatement pst){
        try{ pst.close(); }
        catch(SQLException e) {}
    }

    @Override
    public boolean insertRemark(Remark remark){
        remark.setCreateTime(LocalDateTime.now());
        try{
            sql = "INSERT INTO Remark(post,remark,remark_master,text,img,like,create_time,deleted) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, remark.getPost());
            pst.setInt(2, remark.getRemark());
            pst.setString(3, remark.getRemarkMaster());
            pst.setString(4, remark.getText());
            pst.setString(5, remark.getImg());
            pst.setInt(6, remark.getLike());
            pst.setDate(7, FormatFactory.localDateTimeToSqlDate(remark.getCreateTime()));
            pst.setBoolean(8, remark.getDeleted());
            int i = pst.executeUpdate();
            System.out.printf("插入评论成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean deleteRemark(Remark remark){
        try{
            sql = "UPDATE Remark SET deleted = 1 WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, remark.getId());
            int i = pst.executeUpdate();
            System.out.printf("删除数据成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean updateRemark(Remark remark){
        try{
            sql = "UPDATE Remark SET post = ?, remark = ?, remark_master = ?," +
                    " text = ?, img = ?, like = ?, update_time = ?, deleted = ?" +
                    " WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, remark.getPost());
            pst.setInt(2, remark.getRemark());
            pst.setString(3, remark.getRemarkMaster());
            pst.setString(4, remark.getText());
            pst.setString(5, remark.getImg());
            pst.setInt(6, remark.getLike());
            pst.setDate(7, FormatFactory.localDateTimeToSqlDate(LocalDateTime.now()));
            pst.setBoolean(8, remark.getDeleted());
            pst.setInt(9, remark.getId());
            int i = pst.executeUpdate();
            System.out.printf("修改评论成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    private Remark selectOneRemark(){
        Remark remark = new Remark();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            remark.setId(result.getInt(1));
            remark.setPost(result.getInt(2));
            remark.setRemark(result.getInt(3));
            remark.setRemarkMaster(result.getString(4));
            remark.setText(result.getString(5));
            remark.setImg(result.getString(6));
            remark.setLike(result.getInt(7));
            remark.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(8)));
            remark.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(9)));
            remark.setDeleted(result.getBoolean(10));
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return remark;
    }

    @Override
    public Remark selectRemarkById(int id){
        sql = "SELECT * FROM Remark WHERE id = " + id;
        return selectOneRemark();
    }

    private List<Remark> selectRemarkList(){
        List<Remark> remarks = new LinkedList<Remark>();
        Remark remark = new Remark();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            while(result.next()){
                remark.setId(result.getInt(1));
                remark.setPost(result.getInt(2));
                remark.setRemark(result.getInt(3));
                remark.setRemarkMaster(result.getString(4));
                remark.setText(result.getString(5));
                remark.setImg(result.getString(6));
                remark.setLike(result.getInt(7));
                remark.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(8)));
                remark.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(9)));
                remark.setDeleted(result.getBoolean(10));
                remarks.add(remark);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return remarks;
    }

    @Override
    public List<Remark> selectRemarkByPost(int post){
        sql = "SELECT * FROM Remark WHERE post = " + post;
        return selectRemarkList();
    }

    @Override
    public List<Remark> selectRemarkByRemarkMaster(String remarkMaster){
        sql = "SELECT * FROM Remark WHERE remark_master = \'" + remarkMaster + '\'';
        return selectRemarkList();
    }

    @Override
    public List<Remark> selectRemarkWithTextLike(String model){
        sql = "SELECT * FROM Remark WHERE text LIKE \'" + model + '\'';
        return selectRemarkList();
    }
}
