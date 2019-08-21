package com.hk.repository;

import com.hk.domain.CameraInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 胡冉
 * @ClassName CameraInfoRepository
 * @Description: TODO
 * @Date 2019/5/8 16:10
 * @Version 2.0
 */
public interface CameraInfoRepository extends JpaRepository<CameraInfo, String> {
}
