package com.hk.service.impl;

import com.hk.common.dao.BaseDao;
import com.hk.common.service.impl.BaseServiceImpl;
import com.hk.dao.MedioInfoDao;
import com.hk.domain.MedioInfo;
import com.hk.service.MedioInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1316:33
 * @copyright {@link www.hndfsj.com}
 */
@Transactional(rollbackFor = Exception.class)
@Service("previewParamService")
public class MedioInfoServiceImpl extends BaseServiceImpl<MedioInfo, Long> implements MedioInfoService {
   @Autowired
   private MedioInfoDao previewParamDao;
    @Override
    public BaseDao<MedioInfo, Long> getDAO() {
        return previewParamDao;
    }

    @Override
    public Iterable<MedioInfo> save(Iterable<MedioInfo> entities) {
        return previewParamDao.save(entities);
    }

    @Override
    public void del(MedioInfo previewParam) {
        previewParamDao.delete(previewParam);
    }
}
