package org.zuel.iemployee.demo.dao.impl;

import org.zuel.iemployee.demo.dao.CompanyDAO;
import org.zuel.iemployee.demo.dao.DBConnection;
import org.zuel.iemployee.demo.dao.FormatFactory;
import org.zuel.iemployee.demo.model.Company;

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

public class CompanyDAOImpl implements CompanyDAO {

    private Connection conn;

    private PreparedStatement pst = null;

    private ResultSet result = null;

    private String sql;

    public CompanyDAOImpl() { conn = DBConnection.getConnection(); }

    private void release(ResultSet result, PreparedStatement pst){
        try{ pst.close(); result.close(); }
        catch(SQLException e) {}
    }

    private void release(PreparedStatement pst){
        try{ pst.close(); }
        catch(SQLException e) {}
    }

    @Override
    public boolean insertCompany(Company company){
        company.setCreateTime(LocalDateTime.now());
        try{
            sql = "INSERT INTO Company(name, remark, hardware, salary, environment, schedule_arrange, score, create_time, deleted)" +
                    " VALUES(?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, company.getName());
            pst.setString(2, company.getRemark());
            pst.setFloat(3, company.getHardware());
            pst.setFloat(4, company.getSalary());
            pst.setFloat(5, company.getEnvironment());
            pst.setFloat(6, company.getScheduleArrange());
            pst.setFloat(7, company.getScore());
            pst.setDate(8, FormatFactory.localDateTimeToSqlDate(company.getCreateTime()));
            pst.setBoolean(9, company.getDeleted());
            int i = pst.executeUpdate();
            System.out.printf("添加企业成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            release(pst);
        }
        return true;
    }

    @Override
    public boolean deleteCompany(Company company){
        try{
            sql = "UPDATE Company SET deleted = 1 WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, company.getId());
            int i = pst.executeUpdate();
            System.out.printf("删除企业成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            release(pst);
        }
        return true;
    }

    @Override
    public boolean updateCompany(Company company){
        try{
            sql = "UPDATE Company SET name = ?, remark = ?," +
                    " hardware = ?, salary = ?, environment = ?, schedule_arrange = ?, score = ?, " +
                    "update_time = ?, deleted = ? " +
                    "WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, company.getName());
            pst.setString(2, company.getRemark());
            pst.setFloat(3, company.getHardware());
            pst.setFloat(4, company.getSalary());
            pst.setFloat(5, company.getEnvironment());
            pst.setFloat(6, company.getScheduleArrange());
            pst.setFloat(7, company.getScore());
            pst.setDate(8, FormatFactory.localDateTimeToSqlDate(LocalDateTime.now()));
            pst.setBoolean(9, company.getDeleted());
            pst.setInt(10, company.getId());
            int i = pst.executeUpdate();
            System.out.printf("修改企业数据成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            release(pst);
        }
        return true;
    }

    private Company selectOneCompany(){
        Company company = new Company();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            company.setId(result.getInt(1));
            company.setName(result.getString(2));
            company.setRemark(result.getString(3));
            company.setHardware(result.getFloat(4));
            company.setSalary(result.getFloat(5));
            company.setEnvironment(result.getFloat(6));
            company.setScheduleArrange(result.getFloat(7));
            company.setScore(result.getFloat(8));
            company.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(9)));
            company.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(10)));
            company.setDeleted(result.getBoolean(11));
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            release(result, pst);
        }
        return company;
    }

    @Override
    public Company selectCompanyById(int id){
        sql = "SELECT * FROM Company WHERE id = " + id + " AND deleted != 1";
        return selectOneCompany();
    }

    @Override
    public Company selectCompanyByName(String name){
        sql = "SELECT * FROM Company WHERE name = \'" + name + "\' AND deleted != 1";
        return selectOneCompany();
    }

    private List<Company> selectCompanyList(){
        List<Company> companies = new LinkedList<Company>();
        Company company = new Company();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            while(result.next()){
                company.setId(result.getInt(1));
                company.setName(result.getString(2));
                company.setRemark(result.getString(3));
                company.setHardware(result.getFloat(4));
                company.setSalary(result.getFloat(5));
                company.setEnvironment(result.getFloat(6));
                company.setScheduleArrange(result.getFloat(7));
                company.setScore(result.getFloat(8));
                company.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(9)));
                company.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(10)));
                company.setDeleted(result.getBoolean(11));
                companies.add(company);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return companies;
    }

    @Override
    public List<Company> selectCompanyWithNameLike(String model){
        sql = "SELECT * FROM Company WHERE name LIKE \'" + model + "\' AND deleted != 1";
        return selectCompanyList();
    }
}
