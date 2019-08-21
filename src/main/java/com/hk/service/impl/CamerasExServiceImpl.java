package com.hk.service.impl;

import com.hk.common.dao.BaseDao;
import com.hk.common.service.impl.BaseServiceImpl;
import com.hk.dao.CamerasExDao;
import com.hk.domain.CamerasEx;
import com.hk.service.CamerasExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 胡冉
 * @Description: 监控点CamerasExServiceImpl
 * @date 2018/11/1220:33
 * @copyright {@link www.hndfsj.com}
 */
@Transactional(rollbackFor = Exception.class)
@Service("camerasExService")
public class CamerasExServiceImpl extends BaseServiceImpl<CamerasEx, Long> implements CamerasExService {
    @Autowired
    private CamerasExDao camerasExDao;

    @Override
    public BaseDao<CamerasEx, Long> getDAO() {
        return camerasExDao;
    }

    @Override
    public Iterable<CamerasEx> save(Iterable<CamerasEx> entities) {
        return camerasExDao.save(entities);
    }

    @Override
    public void del(CamerasEx camerasEx) {

    }
}
