package org.zuel.iemployee.demo.dao.impl;

import org.zuel.iemployee.demo.dao.DBConnection;
import org.zuel.iemployee.demo.dao.FormatFactory;
import org.zuel.iemployee.demo.dao.PostDAO;
import org.zuel.iemployee.demo.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * create time: 2021/3/8 20:56
 * author: XinChen1899
 */

public class PostDAOImpl implements PostDAO {

    private Connection conn;

    private PreparedStatement pst = null;

    private ResultSet result = null;

    private String sql;

    public PostDAOImpl(){ conn = DBConnection.getConnection(); }

    private void release(ResultSet result, PreparedStatement pst){
        try{ pst.close(); result.close(); }
        catch(SQLException e) {}
    }

    private void release(PreparedStatement pst){
        try{ pst.close(); }
        catch(SQLException e) {}
    }

    @Override
    public boolean insertPost(Post post){
        try{
            sql = "INSERT INTO Post(title, post_master, zone, company, text, img, visit_num, tip, like, create_time, deleted) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
            pst = conn.prepareStatement(sql);
            pst.setString(1, post.getTitle());
            pst.setString(2, post.getPostMaster());
            pst.setInt(3, post.getZone());
            pst.setInt(4, post.getCompany());
            pst.setString(5, post.getText());
            pst.setString(6, post.getImg());
            pst.setInt(7, post.getVisitNum());
            pst.setInt(8, post.getTip());
            pst.setInt(9, post.getLike());
            pst.setDate(10, FormatFactory.localDateTimeToSqlDate(post.getCreateTime()));
            pst.setBoolean(11, post.getDeleted());
            int i = pst.executeUpdate();
            System.out.printf("插入帖子成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            release(pst);
        }
        return true;
    }

    @Override
    public boolean deletePost(Post post){
        try{
            sql = "UPDATE Post SET deleted = 1 WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, post.getId());
            int i = pst.executeUpdate();
            System.out.printf("删除帖子成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            release(pst);
        }
        return true;
    }

    @Override
    public boolean updatePost(Post post){
        try{
            sql = "UPDATE Post SET title = ?, post_master = ?, zone = ?," +
                    " company = ?, text = ?, img = ?, visit_num = ?, tip = ?, like = ?," +
                    " update_time = ?, deleted = ?" +
                    " WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, post.getTitle());
            pst.setString(2, post.getPostMaster());
            pst.setInt(3, post.getZone());
            pst.setInt(4, post.getCompany());
            pst.setString(5, post.getText());
            pst.setString(6, post.getImg());
            pst.setInt(7, post.getVisitNum());
            pst.setInt(8, post.getTip());
            pst.setInt(9, post.getLike());
            pst.setDate(10, FormatFactory.localDateTimeToSqlDate(LocalDateTime.now()));
            pst.setBoolean(11, post.getDeleted());
            pst.setInt(12, post.getId());
            int i = pst.executeUpdate();
            System.out.printf("修改帖子成功，影响了%d行数据", i);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            release(pst);
        }
        return true;
    }

    private Post selectOnePost(){
        Post post = new Post();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            post.setId(result.getInt(1));
            post.setTitle(result.getString(2));
            post.setPostMaster(result.getString(3));
            post.setZone(result.getInt(4));
            post.setCompany(result.getInt(5));
            post.setText(result.getString(6));
            post.setImg(result.getString(7));
            post.setVisitNum(result.getInt(8));
            post.setTip(result.getInt(9));
            post.setLike(result.getInt(10));
            post.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(11)));
            post.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(12)));
            post.setDeleted(result.getBoolean(13));
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return post;
    }

    @Override
    public Post selectPostById(int id){
        sql = "SELECT * FROM Post WHERE id = " + id;
        return selectOnePost();
    }

    @Override
    public Post selectPostByTitle(String title){
        sql = "SELECT * FROM Post WHERE title = \'" + title + '\'';
        return selectOnePost();
    }

    private List<Post> selectPostList(){
        List<Post> posts = new LinkedList<Post>();
        Post post = new Post();
        try{
            pst = conn.prepareStatement(sql);
            result = pst.executeQuery();
            while(result.next()){
                post.setId(result.getInt(1));
                post.setTitle(result.getString(2));
                post.setPostMaster(result.getString(3));
                post.setZone(result.getInt(4));
                post.setCompany(result.getInt(5));
                post.setText(result.getString(6));
                post.setImg(result.getString(7));
                post.setVisitNum(result.getInt(8));
                post.setTip(result.getInt(9));
                post.setLike(result.getInt(10));
                post.setCreateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(11)));
                post.setUpdateTime(FormatFactory.SqlDateToLocalDateTime(result.getDate(12)));
                post.setDeleted(result.getBoolean(13));
                posts.add(post);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            release(result, pst);
        }
        return posts;
    }

    @Override
    public List<Post> selectPostByZone(int zone){
        sql = "SELECT * FROM Post WHERE zone = " + zone;
        return selectPostList();
    }

    @Override
    public List<Post> selectPostByCompany(int company){
        sql = "SELECT * FROM Post WHERE company = " + company;
        return selectPostList();
    }

    @Override
    public List<Post> selectPostByPostMaster(String postMaster){
        sql = "SELECT * FROM Post WHERE post_master = \'" + postMaster + '\'';
        return selectPostList();
    }

    @Override
    public List<Post> selectPostWithTitleLike(String model){
        sql = "SELECT * FROM Post WHERE title LIKE '\'" + model + '\'';
        return selectPostList();
    }

    @Override
    public List<Post> selectPostwithTextLike(String text){
        sql = "SELECT * FROM Post WHERE text LIKE '\'" + text + '\'';
        return selectPostList();
    }

    @Override
    public List<Post> selectPostByTip(int tip){
        sql = "SELECT * FROM Post WHERE tip = " + tip;
        return selectPostList();
    }
}
