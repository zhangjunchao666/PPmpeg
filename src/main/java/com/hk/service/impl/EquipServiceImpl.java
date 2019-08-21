package com.hk.service.impl;

/**
 * @author 胡冉
 * @date 2018/11/1910:36
 * @copyright {@link www.hndfsj.com}
 */

import com.hk.common.dao.BaseDao;
import com.hk.common.service.impl.BaseServiceImpl;
import com.hk.dao.EquipDao;
import com.hk.domain.Equip;
import com.hk.service.EquipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("equipService")
public class EquipServiceImpl extends BaseServiceImpl<Equip, String> implements EquipService {
    @Autowired
    private EquipDao equipDao;

    @Override
    public BaseDao<Equip, String> getDAO() {
        return equipDao;
    }

    @Override
    public Iterable<Equip> save(Iterable<Equip> entities) {
        return equipDao.save(entities);
    }

    @Override
    public void del(Equip equip) {
        equipDao.delete(equip);
    }

    @Override
    public Equip findById(String s) {
        return equipDao.findOne(s);
    }

    @Override
    public void del(String s) {
        equipDao.delete(s);
    }
}
