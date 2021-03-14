package org.zuel.iemployee.demo.dao.impl;

import org.zuel.iemployee.demo.dao.DBConnection;
import org.zuel.iemployee.demo.dao.FormatFactory;
import org.zuel.iemployee.demo.dao.ZoneDAO;
import org.zuel.iemployee.demo.model.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * create time: 2021/3/8 20:58
 * author: XinChen1899
 */

public class ZoneDAOImpl implements ZoneDAO {

    private Connection conn;

    private PreparedStatement pst = null;

    private ResultSet result = null;

    private String sql;

    public ZoneDAOImpl() { conn = DBConnection.getConnection(); }

    private void release(ResultSet result, PreparedStatement pst){
        try{ pst.close(); result.close(); }
        catch(SQLException e) {}
    }

    private void release(PreparedStatement pst){
        try{ pst.close(); }
        catch(SQLException e) {}
    }

    @Override
    public boolean insertZone(Zone zone){
        zone.setCreateTime(LocalDateTime.now());
        try{
            sql = "INSERT INTO Zone(name, remark, post_num, hot_post, create_time, deleted) " +
                    "VALUES(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, zone.getName());
            pst.setString(2, zone.getRemark());
            pst.setInt(3, zone.getPostNum());
            pst.setString(4, zone.getHotPost());
            pst.setDate(5, FormatFactory.localDateTimeToSqlDate(zone.getCreateTime()));
            pst.setBoolean(6, zone.getDeleted());
            int i = pst.executeUpdate();
            System.out.printf("插入分区成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean deleteZone(Zone zone){
        try{
            sql = "UPDATE Zone SET deleted = 1 WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, zone.getId());
            int i = pst.executeUpdate();
            System.out.printf("删除分区成功，影响了%行数据");
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean updateZone(Zone zone){
        try{
            sql = "UPDATE Zone SET name = ?, remark = ?, post_num = ?, hot_post = ?, update_time = ?, deleted = ? " +
                    "WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, zone.getName());
            pst.setString(2, zone.getRemark());
            pst.setInt(3, zone.getPostNum());
            pst.setString(4, zone.getHotPost());
            pst.setDate(5, FormatFactory.localDateTimeToSqlDate(LocalDateTime.now()));
            pst.setBoolean(6, zone.getDeleted());
            int i = pst.executeUpdate();
            System.out.printf("修改分区成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    private Zone selectOneZone(){
        Zone zone = new Zone();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            zone.setId(result.getInt(1));
            zone.setName(result.getString(2));
            zone.setRemark(result.getString(3));
            zone.setPostNum(result.getInt(4));
            zone.setHotPost(result.getString(5));
            zone.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(6)));
            zone.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(7)));
            zone.setDeleted(result.getBoolean(8));
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return zone;
    }

    @Override
    public Zone selectZoneById(int id){
        sql = "SELECT * FROM Zone WHERE id = " + id;
        return selectOneZone();
    }

    @Override
    public Zone selectZoneByName(String name){
        sql = "SELECT * FROM Zone WHERE name = \'" + name +'\'';
        return selectOneZone();
    }
}
