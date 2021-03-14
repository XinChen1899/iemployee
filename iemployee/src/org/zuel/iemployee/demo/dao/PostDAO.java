package org.zuel.iemployee.demo.dao;

import org.zuel.iemployee.demo.model.Post;

import java.util.List;

/**
 * create time: 2021/3/8 20:53
 * author: XinChen1899
 */

public interface PostDAO {

    boolean insertPost(Post post);

    boolean deletePost(Post post);

    boolean updatePost(Post post);

    Post selectPostById(int id);

    Post selectPostByTitle(String title);

    List<Post> selectPostByZone(int zone);

    List<Post> selectPostByCompany(int company);

    List<Post> selectPostByPostMaster(String postMaster);

    List<Post> selectPostWithTitleLike(String model);

    List<Post> selectPostwithTextLike(String text);

    List<Post> selectPostByTip(int tip);
}
