package com.alex.dragblog.xo.service.impl;

import com.alex.dragblog.base.enums.EStatus;
import com.alex.dragblog.base.service.impl.SuperServiceImpl;
import com.alex.dragblog.commons.entity.Admin;
import com.alex.dragblog.commons.entity.Role;
import com.alex.dragblog.utils.RedisUtils;
import com.alex.dragblog.utils.ResultUtils;
import com.alex.dragblog.utils.StringUtils;
import com.alex.dragblog.xo.global.MessageConf;
import com.alex.dragblog.xo.global.RedisConf;
import com.alex.dragblog.xo.global.SQLConf;
import com.alex.dragblog.xo.global.SysConf;
import com.alex.dragblog.xo.mapper.RoleMapper;
import com.alex.dragblog.xo.service.AdminService;
import com.alex.dragblog.xo.service.RoleService;
import com.alex.dragblog.xo.vo.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 *description:  角色服务实现类
 *author:       alex
 *createDate:   2020/7/2 22:59
 *version:      1.0.0
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role>implements RoleService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AdminService adminService;

    @Override
    public IPage<Role> getPageList(RoleVo roleVo) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(roleVo.getKeyword().trim()))
            queryWrapper.like(SQLConf.ROLENAME, roleVo.getKeyword().trim());
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE.getValue());
        Page<Role> page = new Page<>(roleVo.getCurrentPage(), roleVo.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public String addRole(RoleVo roleVo) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SQLConf.ROLENAME, roleVo.getRoleName());
        Role role = this.getOne(queryWrapper);
        if (role == null) {
            Role newRole = new Role();
            BeanUtils.copyProperties(roleVo, newRole);
            newRole.insert();
            return ResultUtils.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
        }
        return ResultUtils.result(SysConf.ERROR, MessageConf.INSERT_FAIL);
    }

    @Override
    public String editRole(RoleVo roleVo) {
        String uid = roleVo.getId();
        Role getRole = this.getById(uid);
        if (getRole == null) {
            return ResultUtils.result(SysConf.ERROR, MessageConf.PARAM_INCORRECT);
        }
        getRole.setRoleName(roleVo.getRoleName());
        getRole.setCategoryMenuUids(roleVo.getCategoryMenuUids());
        getRole.setSummary(roleVo.getSummary());
        getRole.setUpdateTime(new Date());
        getRole.updateById();

        // 修改成功后，需要删除redis中所有的admin访问路径
        deleteAdminVisitUrl();

        return ResultUtils.result(SysConf.SUCCESS, MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteRole(RoleVo roleVo) {
        QueryWrapper<Admin> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        blogQueryWrapper.in(SQLConf.ROLEUID, roleVo.getId());
        Integer adminCount = adminService.count(blogQueryWrapper);
        if (adminCount > 0)
            return ResultUtils.result(SysConf.ERROR, MessageConf.ADMIN_UNDER_THIS_ROLE);
        Role role = this.getById(roleVo.getId());
        role.setStatus(EStatus.DISABLE.getValue());
        role.setUpdateTime(new Date());
        role.updateById();
        deleteAdminVisitUrl();
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.DELETE_SUCCESS);
    }

    /**
     * description :删除redis中管理员的访问路径
     * author :     alex
     * @param :
     * @return :
     */

    private void deleteAdminVisitUrl(){
        Set<String> keys = redisUtils.keys(RedisConf.ADMIN_VISIT_MENU + "*");
        redisUtils.delete(keys);
    }
}
