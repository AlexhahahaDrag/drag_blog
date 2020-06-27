package com.alex.dragblog.xo.service;

import com.alex.dragblog.base.service.SuperService;
import com.alex.dragblog.commons.entity.Admin;
import com.alex.dragblog.xo.vo.AdminVo;

/**
 *description:  管理员表 服务类
 *author:       alex
 *createDate:   2020/6/26 22:06
 *version:      1.0.0
 */
public interface AdminService extends SuperService<Admin> {

    /**
     *description:  通过id获取admin
     *author:       alex
     *@param:
     *@return:
     */
    Admin getAdminById(String id);

    /**
     *description:  Web端通过用户名获取一个Admin
     *author:       alex
     *@param:
     *@return:
     */
    Admin getAdminByUse(String username);

    /**
     *description:  获取当前管理员
     *author:       alex
     *@param:
     *@return:
     */
    Admin getMe();

    /**
     *description:  添加在线用户
     *author:       alex
     *@param:
     *@return:
     */
    void addOnlineAdmin(AdminVo adminVo);

    /**
     *description:  编辑当前管理员信息
     *author:       alex
     *@param:
     *@return:
     */
    String editMed(AdminVo adminVo);

    /**
     *description:  修改密码
     *author:       alex
     *@param:
     *@return:
     */
    String changePwd(String oldPwd, String newPwd);
}
