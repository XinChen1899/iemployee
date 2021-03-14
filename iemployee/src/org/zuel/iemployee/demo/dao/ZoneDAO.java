package org.zuel.iemployee.demo.dao;

import org.zuel.iemployee.demo.model.Zone;

/**
 * create time: 2021/3/8 20:53
 * author: XinChen1899
 */

public interface ZoneDAO {

    boolean insertZone(Zone zone);

    boolean deleteZone(Zone zone);

    boolean updateZone(Zone zone);

    Zone selectZoneById(int id);

    Zone selectZoneByName(String name);
}
