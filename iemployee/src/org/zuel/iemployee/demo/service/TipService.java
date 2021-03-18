package org.zuel.iemployee.demo.service;

import org.zuel.iemployee.demo.constant.TipConstant;
import org.zuel.iemployee.demo.dao.TipDAO;
import org.zuel.iemployee.demo.dao.impl.TipDAOImpl;
import org.zuel.iemployee.demo.model.Post;
import org.zuel.iemployee.demo.model.ResponseObj;
import org.zuel.iemployee.demo.model.Tip;
import org.zuel.iemployee.demo.model.Zone;
import org.zuel.iemployee.demo.util.ResponseUtil;

import java.util.List;

/**
 * create time: 2021/3/8 20:43
 * author: XinChen1899
 */

public class TipService {
    
    private static TipDAO tipDAO = new TipDAOImpl();

    /**
     * 插入标签
     * @param tip
     * @return
     */
    public static ResponseObj insertTip(Tip tip){
        if(tip == null){
            return ResponseUtil.badArgument();
        }
        if(tipDAO.insertTip(tip)){
            return ResponseUtil.fail(TipConstant.INSERT_TIP_UNSUCCESSFULLY_CODE, TipConstant.INSERT_TIP_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("标签插入成功", tip);
    }

    /**
     * 删除标签
     * @param tip
     * @return
     */
    public static ResponseObj delTip(Tip tip){
        if(tip == null){
            return ResponseUtil.badArgument();
        }
        if(tipDAO.deleteTip(tip)){
            return ResponseUtil.fail(TipConstant.DELETE_TIP_UNSUCCESSFULLY_CODE, TipConstant.DELETE_TIP_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("标签删除成功", tip);
    }

    /**
     * 修改标签信息
     * @param tip
     * @return
     */
    public static ResponseObj updateTip(Tip tip){
        if(tip == null){
            return ResponseUtil.badArgument();
        }
        if(tipDAO.updateTip(tip)){
            return ResponseUtil.fail(TipConstant.UPDATE_TIP_UNSUCCESSFULLY_CODE, TipConstant.UPDATE_TIP_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("标签秀改成功", tip);
    }

    /**
     * 按名字找标签
     * @param name
     * @param tip
     * @return
     */
    public static ResponseObj searchTipByName(String name, Tip tip){
        if(name == null || name.length() == 0){
            return ResponseUtil.badArgument();
        }
        tip = tipDAO.selectTipByName(name);
        if(tip == null){
            return ResponseUtil.fail(TipConstant.TIP_NOT_EXIST_CODE, TipConstant.TIP_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", tip);
    }

    /**
     * 按帖子找标签
     * @param post
     * @param tip
     * @return
     */
    public static ResponseObj searchTipByPost(Post post, Tip tip){
        if(post == null){
            return ResponseUtil.badArgument();
        }
        tip = tipDAO.selectTipById(post.getTip());
        if(tip == null){
            return ResponseUtil.fail(TipConstant.TIP_NOT_EXIST_CODE, TipConstant.TIP_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", tip);
    }

    /**
     * 找分区下的所有标签
     * @param zone
     * @param tipList
     * @return
     */
    public static ResponseObj searchTipByZone(Zone zone, List<Tip> tipList){
        if(zone == null){
            return ResponseUtil.badArgument();
        }
        tipList = tipDAO.selectTipByZone(zone.getId());
        if(tipList == null || tipList.size() == 0){
            return ResponseUtil.fail(TipConstant.TIP_NOT_EXIST_CODE, TipConstant.TIP_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", tipList);
    }
}
