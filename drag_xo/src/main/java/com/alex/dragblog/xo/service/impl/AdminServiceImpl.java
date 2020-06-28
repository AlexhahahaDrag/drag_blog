package com.alex.dragblog.xo.service.impl;

import com.alex.dragblog.base.holder.RequestHolder;
import com.alex.dragblog.base.service.impl.SuperServiceImpl;
import com.alex.dragblog.commons.entity.Admin;
import com.alex.dragblog.commons.feign.PictureFeignClient;
import com.alex.dragblog.utils.RedisUtils;
import com.alex.dragblog.utils.StringUtils;
import com.alex.dragblog.xo.WebUtil;
import com.alex.dragblog.xo.global.SQLConf;
import com.alex.dragblog.xo.global.SysConf;
import com.alex.dragblog.xo.mapper.AdminMapper;
import com.alex.dragblog.xo.service.AdminService;
import com.alex.dragblog.xo.vo.AdminVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Admin getAdminById(String id) {
        return adminMapper.getAdminById(id);
    }

    @Override
    public Admin getAdminByUse(String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SQLConf.USER_NAME, username);
        queryWrapper.last("limit 1");
        //清空密码防止泄露
        Admin admin = this.getOne(queryWrapper);
        admin.setPassword(null);;
        //获取图片
        if (StringUtils.isNotEmpty(admin.getAvatar())) {
            String pictureList = this.pictureFeignClient.getPicture(admin.getAvatar(), ",");
            admin.setPhotoList(webUtil.getPicture(pictureList));
        }
        Admin result = new Admin();
        result.setNickName(admin.getNickName());
        result.setOccupation(admin.getOccupation());
        result.setSummary(admin.getSummary());
        result.setAvatar(admin.getAvatar());
        result.setPhotoList(admin.getPhotoList());
        result.setPersonResume(admin.getPersonResume());
        return result;
    }

    @Override
    public Admin getMe() {
        HttpServletRequest request = RequestHolder.getRequest();
        if (StringUtils.isEmpty((String)request.getAttribute(SysConf.ADMIN_ID)))
            return new Admin();
        Admin admin = this.getById(request.getAttribute(SysConf.ADMIN_ID).toString());
        //清空密码
        admin.setPassword(null);
        //获取图片
        if (StringUtils.isNotEmpty(admin.getAvatar())) {
            String pictureList = this.pictureFeignClient.getPicture(admin.getAvatar(), ",");
            admin.setPhotoList(webUtil.getPicture(pictureList));
        }
        return admin;
    }

    @Override
    public void addOnlineAdmin(AdminVo adminVo) {
            sss
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
