package com.alex.dragblog.xo.service;

import com.alex.dragblog.base.service.SuperService;
import com.alex.dragblog.commons.entity.SystemConfig;
import com.alex.dragblog.xo.vo.SystemConfigVo;

import java.util.List;

/**
 *description:  系统配置表
 *author:       alex
 *createDate:   2020/6/27 11:25
 *version:      1.0.0
 */
public interface SystemConfigService extends SuperService<SystemConfig> {

    /**
     *description:   获取系统配置
     *author:       alex
     *@param:
     *@return:
     */
    SystemConfig getConfig();

    /**
     *description:  通过key前缀清空Redis缓存
     *author:       alex
     *@param:       keys
     *@return:
     */
    String cleanRedisByKey(List<String> keys);

    /**
     *description:  修改系统配置
     *author:       alex
     *@param:       systemConfigVo
     *@return:
     */
    String editSystemConfig(SystemConfigVo systemConfigVo);
}
