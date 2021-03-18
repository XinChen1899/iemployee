package org.zuel.iemployee.demo.service;

import org.zuel.iemployee.demo.constant.PostConstant;
import org.zuel.iemployee.demo.dao.PostDAO;
import org.zuel.iemployee.demo.dao.impl.PostDAOImpl;
import org.zuel.iemployee.demo.model.*;
import org.zuel.iemployee.demo.util.ResponseUtil;

import java.util.List;

/**
 * create time: 2021/3/8 20:43
 * author: XinChen1899
 */

public class PostService {

    private static PostDAO postDAO = new PostDAOImpl();

    /**
     * 发帖
     * @param post
     * @return
     */
    public static ResponseObj publishPost(Post post){
        if(post == null){
            return ResponseUtil.badArgument();
        }
        if(!postDAO.insertPost(post)){
            return ResponseUtil.fail(PostConstant.POST_UNSUCCESSFULLY_CODE, PostConstant.POST_UNSUCCESSFULLY_ERROR);
        }
        return ResponseUtil.ok("帖子发布成功", null);
    }

    /**
     * 删帖
     * @param post
     * @return
     */
    public static ResponseObj delPost(Post post){
        if(post == null){
            return ResponseUtil.badArgument();
        }
        if(!postDAO.deletePost(post)){
            return ResponseUtil.fail(PostConstant.POST_DELETE_UNSUCCESSFULLY_CODE, PostConstant.POST_DELETE_UNSUCCESSFULLY_ERROR);
        }
        return ResponseUtil.ok("帖子删除成功", null);
    }

    /**
     * 按分区找帖子
     * @param zone
     * @param postList
     * @return
     */
    public static ResponseObj searchPostsByZone(Zone zone, List<Post> postList){
        if(zone == null){
            return ResponseUtil.badArgument();
        }
        postList = postDAO.selectPostByZone(zone.getId());
        if(postList == null){
            return ResponseUtil.ok("操作成功，但似乎没有符合的帖子", null);
        }
        return ResponseUtil.ok("操作成功", postList);
    }

    /**
     * 按用户找帖子
     * @param user
     * @param postList
     * @return
     */
    public static ResponseObj searchPostsByPostMaster(User user, List<Post> postList){
        if(user == null){
            return ResponseUtil.badArgument();
        }
        postList = postDAO.selectPostByPostMaster(user.getId());
        if(postList == null){
            return ResponseUtil.ok("操作成功，但似乎没有符合的帖子", null);
        }
        return ResponseUtil.ok("操作成功", postList);
    }

    /**
     * 按标题找帖子
     * @param model
     * @param postList
     * @return
     */
    public static ResponseObj searchPostsByTitle(String model, List<Post> postList){
        if(model == null || model.equals(' ')){
            return ResponseUtil.badArgument();
        }
        postList = postDAO.selectPostWithTitleLike(model);
        if(postList == null){
            return ResponseUtil.ok("操作成功，但似乎没有符合的帖子", null);
        }
        return ResponseUtil.ok("操作成功", postList);
    }

    /**
     * 查找企业区帖子
     * @param company
     * @param postList
     * @return
     */
    public static ResponseObj searchPostsByCompany(Company company, List<Post> postList){
        if(company == null){
            return ResponseUtil.badArgument();
        }
        postList = postDAO.selectPostByCompany(company.getId());
        if(postList == null){
            return ResponseUtil.ok("操作成功，但似乎没有符合的帖子", null);
        }
        return ResponseUtil.ok("操作成功", postList);
    }

    /**
     * 根据评论找帖子
     * @param remark
     * @param post
     * @return
     */
    public static ResponseObj searchPostByRemark(Remark remark, Post post){
        if(remark == null){
            return ResponseUtil.badArgument();
        }
        post = postDAO.selectPostById(remark.getId());
        if(post == null){
            return ResponseUtil.ok("操作成功，但似乎没有符合的帖子", null);
        }
        return ResponseUtil.ok("操作成功", post);
    }

    /**
     * 按照标签找帖子
     * @param tip
     * @param postList
     * @return
     */
    public static ResponseObj searchPostsByTip(Tip tip, List<Post> postList){
        if(tip == null){
            return ResponseUtil.badArgument();
        }
        postList = postDAO.selectPostByTip(tip.getId());
        if(postList == null){
            return ResponseUtil.ok("操作成功，但似乎没有符合的帖子", null);
        }
        return ResponseUtil.ok("操作成功", postList);
    }

    /**
     * 修改帖子信息
     * @param post
     * @return
     */
    public static ResponseObj updatePost(Post post){
        if(post == null){
            return ResponseUtil.badArgument();
        }
        if(!postDAO.updatePost(post)){
            return ResponseUtil.fail(PostConstant.POST_UPDATE_UNSUCCESSFULLY_CODE, PostConstant.POST_UPDATE_UNSUCCESSFULLY_ERROR);
        }
        return ResponseUtil.ok("修改成功", post);
    }
    
    /**
     * 查找某分区的热门帖子
     * @param hotPost
     * @param postList
     * @return
     */
    public static ResponseObj searchPostByHotPost(int[] hotPost, List<Post> postList){
        if(hotPost == null){
            return ResponseUtil.badArgument();
        }
        Post post;
        for(int i = 0; i < 10; i++){
            post = postDAO.selectPostById(hotPost[i]);
            postList.add(post);
        }
        if(postList == null){
            return ResponseUtil.ok("操作成功，但似乎没有符合的帖子", null);
        }
        return ResponseUtil.ok("操作成功", postList);
    }
}
