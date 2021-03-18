package org.zuel.iemployee.demo.service;

import org.zuel.iemployee.demo.constant.RemarkConstant;
import org.zuel.iemployee.demo.dao.RemarkDAO;
import org.zuel.iemployee.demo.dao.impl.RemarkDAOImpl;
import org.zuel.iemployee.demo.model.Post;
import org.zuel.iemployee.demo.model.Remark;
import org.zuel.iemployee.demo.model.ResponseObj;
import org.zuel.iemployee.demo.model.User;
import org.zuel.iemployee.demo.util.ResponseUtil;

import java.util.List;

/**
 * create time: 2021/3/8 20:43
 * author: XinChen1899
 */

public class RemarkService {

    private static RemarkDAO remarkDAO = new RemarkDAOImpl();

    /**
     * 发表评论
     * @param remark
     * @return
     */
    public static ResponseObj publishRemark(Remark remark){
        if(remark == null){
            return ResponseUtil.badArgument();
        }
        if(!remarkDAO.insertRemark(remark)){
            return ResponseUtil.fail(RemarkConstant.REMARK_UNSUCCESSFULLY_CODE, RemarkConstant.REMARK_UNSUCCESSFULLY_ERROR);
        }
        return ResponseUtil.ok("评论成功", remark);
    }

    /**
     * 删除评论
     * @param remark
     * @return
     */
    public static ResponseObj delRemark(Remark remark){
        if(remark == null){
            return ResponseUtil.badArgument();
        }
        if(!remarkDAO.deleteRemark(remark)){
            return ResponseUtil.fail(RemarkConstant.REMARK_DELETE_UNSUCCESSFULLY_CODE, RemarkConstant.REMARK_DELETE_UNSUCCESSFULLY_ERROR);
        }
        return ResponseUtil.ok("删除评论成功", null);
    }

    /**
     * 修改评论
     * @param remark
     * @return
     */
    public static ResponseObj updateRemark(Remark remark){
        if(remark == null){
            return ResponseUtil.badArgument();
        }
        if(!remarkDAO.updateRemark(remark)){
            return ResponseUtil.fail(RemarkConstant.REMARK_UPDATE_UNSUCCESSFULLY_CODE, RemarkConstant.REMARK_UPDATE_UNSUCCESSFULLY_ERROR);
        }
        return ResponseUtil.ok("评论修改成功", remark);
    }

    /**
     * 某帖子的所有评论
     * @param post
     * @param remarkList
     * @return
     */
    public static ResponseObj searchRemarkByPost(Post post, List<Remark> remarkList){
        if(post == null) {
            return ResponseUtil.badArgument();
        }
        remarkList = remarkDAO.selectRemarkByPost(post.getId());
        if(remarkList == null || remarkList.size() == 0){
            return ResponseUtil.fail(RemarkConstant.POST_DOESNOT_REMARKED_CODE, RemarkConstant.POST_DOESNOT_REMARKED);
        }
        return ResponseUtil.ok("操作成功", remarkList);
    }

    /**
     * 某用户的所有评论
     * @param user
     * @param remarkList
     * @return
     */
    public static ResponseObj searchRemarkByUser(User user, List<Remark> remarkList){
        if(user == null || user.getId().equals(null)){
            return ResponseUtil.badArgument();
        }
        remarkList = remarkDAO.selectRemarkByRemarkMaster(user.getId());
        if(remarkList == null || remarkList.size() == 0){
            return ResponseUtil.fail(RemarkConstant.USER_HASNOT_REMARK_CODE, RemarkConstant.USER_HASNOT_REMARK);
        }
        return ResponseUtil.ok("操作成功", remarkList);
    }

    /**
     * 按内容找评论
     * @param text
     * @param remarkList
     * @return
     */
    public static ResponseObj searchRemarkByText(String text, List<Remark> remarkList){
        if(text.equals(null) || text.length() == 0){
            return ResponseUtil.badArgument();
        }
        remarkList = remarkDAO.selectRemarkWithTextLike(text);
        if(remarkList == null || remarkList.size() == 0){
            return ResponseUtil.fail(RemarkConstant.REMARK_NOT_EXIST_CODE, RemarkConstant.REMARK_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", remarkList);
    }
}
