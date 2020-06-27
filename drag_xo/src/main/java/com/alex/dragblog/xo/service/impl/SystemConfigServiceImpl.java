package com.alex.dragblog.xo.service.impl;

import com.alex.dragblog.base.enums.EOpenStatus;
import com.alex.dragblog.base.enums.EStatus;
import com.alex.dragblog.base.service.impl.SuperServiceImpl;
import com.alex.dragblog.commons.entity.SystemConfig;
import com.alex.dragblog.utils.RedisUtils;
import com.alex.dragblog.utils.ResultUtils;
import com.alex.dragblog.xo.global.MessageConf;
import com.alex.dragblog.xo.global.RedisConf;
import com.alex.dragblog.xo.global.SQLConf;
import com.alex.dragblog.xo.global.SysConf;
import com.alex.dragblog.xo.mapper.SystemConfigMapper;
import com.alex.dragblog.xo.service.SystemConfigService;
import com.alex.dragblog.xo.vo.SystemConfigVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 *description:  系统服务实现类
 *author:       alex
 *createDate:   2020/6/27 11:32
 *version:      1.0.0
 */
@Service
public class SystemConfigServiceImpl extends SuperServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public SystemConfig getConfig() {
        QueryWrapper<SystemConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE.getValue());
        queryWrapper.eq(SQLConf.ISDELETE, 0);
        queryWrapper.last("LIMIT 1");
        return this.getOne(queryWrapper);;
    }

    @Override
    public String cleanRedisByKey(List<String> key) {
        if (key == null || key.isEmpty())
            return ResultUtils.result(SysConf.ERROR, MessageConf.OPERATION_FAIL);
        key.forEach(item -> {
            //表示清空所有key
            if (RedisConf.ALL.equals(item)) {
                Set<String> keys = redisUtils.keys("*");
                redisUtils.delete(keys);
            } else {
                Set<String> keys = redisUtils.keys(key + "*");
                redisUtils.delete(keys);
            }
        });
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public String editSystemConfig(SystemConfigVo systemConfigVo) {
        if (EOpenStatus.CLOSE_STATUS.getValue().equals(systemConfigVo.getUploadLocal()))
        return null;
    }
}
