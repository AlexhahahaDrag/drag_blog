package com.alex.dragblog.xo.service.impl;

import com.alex.dragblog.base.service.impl.SuperServiceImpl;
import com.alex.dragblog.commons.entity.Role;
import com.alex.dragblog.xo.service.RoleService;
import org.springframework.stereotype.Service;

/**
 *description:  角色服务实现类
 *author:       alex
 *createDate:   2020/7/2 22:59
 *version:      1.0.0
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role>implements RoleService {
}
