package com.hk.service.impl;

import com.hk.common.dao.BaseDao;
import com.hk.common.service.impl.BaseServiceImpl;
import com.hk.dao.EncodersDao;
import com.hk.domain.Encoders;
import com.hk.service.EncodersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 胡冉
 * @Description: 设备EncodersServiceImpl
 * @date 2018/11/1220:32
 */@Transactional(rollbackFor = Exception.class)
@Service("encodersService")
public class EncodersServiceImpl extends BaseServiceImpl<Encoders, Long> implements EncodersService {
    @Autowired
    private EncodersDao encodersDao;

    @Override
    public BaseDao<Encoders, Long> getDAO() {
        return encodersDao;
    }

    @Override
    public Iterable<Encoders> save(Iterable<Encoders> entities) {
        return getDAO().save(entities);
    }

    @Override
    public void del(Encoders encoders) {
        encodersDao.delete(encoders);
    }

    @Override
    public Encoders findById(Long s) {
        return null;
    }

    @Override
    public void del(Long s) {

    }
}
