package org.zuel.iemployee.demo.service;

import org.zuel.iemployee.demo.constant.ZoneConstant;
import org.zuel.iemployee.demo.dao.ZoneDAO;
import org.zuel.iemployee.demo.dao.impl.ZoneDAOImpl;
import org.zuel.iemployee.demo.model.Post;
import org.zuel.iemployee.demo.model.ResponseObj;
import org.zuel.iemployee.demo.model.Zone;
import org.zuel.iemployee.demo.util.ResponseUtil;

import java.util.List;

/**
 * create time: 2021/3/8 20:43
 * author: XinChen1899
 */

public class ZoneService {

    private static ZoneDAO zoneDAO = new ZoneDAOImpl();

    /**
     * 插入分区
     * @param zone
     * @return
     */
    public static ResponseObj insertZone(Zone zone){
        if(zone == null){
            return ResponseUtil.badArgument();
        }
        if(!zoneDAO.insertZone(zone)){
            return ResponseUtil.fail(ZoneConstant.INSERT_ZONE_UNSUCCESSFULLY_CODE, ZoneConstant.INSERT_ZONE_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("分区插入成功发", zone);
    }

    /**
     * 删除分区
     * @param zone
     * @return
     */
    public static ResponseObj delZone(Zone zone){
        if(zone == null){
            return ResponseUtil.badArgument();
        }
        if(!zoneDAO.deleteZone(zone)){
            return ResponseUtil.fail(ZoneConstant.DELETE_ZONE_UNSUCCESSFULLY_CODE, ZoneConstant.DELETE_ZONE_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("分区删除成功", null);
    }

    /**
     * 修改分区
     * @param zone
     * @return
     */
    public static ResponseObj updateZone(Zone zone){
        if(zone == null){
            return ResponseUtil.badArgument();
        }
        if(!zoneDAO.updateZone(zone)){
            return ResponseUtil.fail(ZoneConstant.UPDATE_ZONE_UNSUCCESSFULLY_CODE, ZoneConstant.UPDATE_ZONE_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("分区修改成功", zone);
    }

    /**
     * 找帖子的分区
     * @param post
     * @param zone
     * @return
     */
    public static ResponseObj searchZoneByPost(Post post, Zone zone){
        if(post == null){
            return ResponseUtil.badArgument();
        }
        zone = zoneDAO.selectZoneById(post.getZone());
        if(zone == null){
            return ResponseUtil.fail(ZoneConstant.ZONE_NOT_EXIST_CODE, ZoneConstant.ZONE_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", zone);
    }

    /**
     * 按名字找分区
     * @param name
     * @param zone
     * @return
     */
    public static ResponseObj searchZoneByName(String name, Zone zone){
        if(name == null || name.length() == 0){
            return ResponseUtil.badArgument();
        }
        zone = zoneDAO.selectZoneByName(name);
        if(zone == null){
            return ResponseUtil.fail(ZoneConstant.ZONE_NOT_EXIST_CODE, ZoneConstant.ZONE_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", zone);
    }

    /**
     * 找到所有的分区
     * @param zoneList
     * @return
     */
    public static ResponseObj searchAllZone(List<Zone> zoneList){
        zoneList = zoneDAO.selectAllZone();
        if(zoneList == null || zoneList.size() == 0){
            return ResponseUtil.fail(ZoneConstant.ZONE_NOT_EXIST_CODE, ZoneConstant.ZONE_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", zoneList);
    }
}
