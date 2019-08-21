package com.hk.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author 胡冉
 * @Description: 基类Dao
 * @date 2018/11/1220:16
 * @copyright {@link www.hndfsj.com}
 */
@NoRepositoryBean
public interface BaseDao <T,ID extends Serializable> extends JpaSpecificationExecutor<T>,JpaRepository<T,ID> {
}
