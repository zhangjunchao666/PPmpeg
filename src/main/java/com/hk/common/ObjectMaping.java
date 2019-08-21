package com.hk.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 胡冉
 * @ClassName ObjectMaping
 * @Date 2019/5/8 18:36
 * @Version 2.0
 */
public class ObjectMaping {

    /**
     * 维护对象之间的关系
     */
    public static final Map<String, Process> mapVeidoIdAndProcess = new ConcurrentHashMap<>(1);
}
