package com.alex.dragblog.xo.service;

import com.alex.dragblog.base.service.SuperService;
import com.alex.dragblog.commons.entity.Role;
import com.alex.dragblog.xo.vo.RoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *description:  角色服务
 *author:       alex
 *createDate:   2020/7/2 22:44
 *version:      1.0.0
 */
public interface RoleService extends SuperService<Role> {

    /**
     * description :获取角色列表
     * author :     alex
     * @param :
     * @return :
     */
    IPage<Role> getPageList(RoleVo roleVo);

    /**
     * description :新增角色
     * author :     alex
     * @param :
     * @return :
     */
    String addRole(RoleVo roleVo);

    /**
     * description :编辑角色
     * author :     alex
     * @param :
     * @return :
     */
    String editRole(RoleVo roleVo);

    /**
     * description :删除角色
     * author :     alex
     * @param :
     * @return :
     */
    String deleteRole(RoleVo roleVo);
}
