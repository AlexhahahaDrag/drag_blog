package com.alex.dragblog.xo.service.impl;

import com.alex.dragblog.base.service.impl.SuperServiceImpl;
import com.alex.dragblog.commons.entity.Admin;
import com.alex.dragblog.xo.WebUtil;
import com.alex.dragblog.xo.mapper.AdminMapper;
import com.alex.dragblog.xo.service.AdminService;
import com.alex.dragblog.xo.vo.AdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *description:  管理员表服务实现类
 *author:       alex
 *createDate:   2020/6/26 22:49
 *version:      1.0.0
 */
@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private WebUtil webUtil;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @Override
    public Admin getAdminById(String id) {
        return null;
    }

    @Override
    public Admin getAdminByUse(String username) {
        return null;
    }

    @Override
    public Admin getMe() {
        return null;
    }

    @Override
    public void addOnlineAdmin(AdminVo adminVo) {

    }

    @Override
    public String editMed(AdminVo adminVo) {
        return null;
    }

    @Override
    public String changePwd(String oldPwd, String newPwd) {
        return null;
    }
}
