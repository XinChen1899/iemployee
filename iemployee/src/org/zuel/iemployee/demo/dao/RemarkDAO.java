package org.zuel.iemployee.demo.dao;

import org.zuel.iemployee.demo.model.Remark;

import java.util.List;

/**
 * create time: 2021/3/8 20:53
 * author: XinChen1899
 */

public interface RemarkDAO {

    boolean insertRemark(Remark remark);

    boolean deleteRemark(Remark remark);

    boolean updateRemark(Remark remark);

    Remark selectRemarkById(int id);

    List<Remark> selectRemarkByPost(int post);

    List<Remark> selectRemarkByRemarkMaster(String remarkMaster);

    List<Remark> selectRemarkWithTextLike(String model);
}
