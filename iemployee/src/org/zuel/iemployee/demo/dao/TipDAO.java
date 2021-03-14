package org.zuel.iemployee.demo.dao;

import org.zuel.iemployee.demo.model.Tip;

import java.util.List;

/**
 * create time: 2021/3/8 20:54
 * author: XinChen1899
 */

public interface TipDAO {

    boolean insertTip(Tip tip);

    boolean deleteTip(Tip tip);

    boolean updateTip(Tip tip);

    Tip selectTipById(int id);

    Tip selectTipByName(String name);

    List<Tip> selectTipByZone(int zone);
}
