package com.alex.dragblog.xo.service.impl;

import com.alex.dragblog.base.global.BaseSysConf;
import com.alex.dragblog.base.holder.RequestHolder;
import com.alex.dragblog.base.service.impl.SuperServiceImpl;
import com.alex.dragblog.commons.entity.Admin;
import com.alex.dragblog.commons.entity.OnlineAdmin;
import com.alex.dragblog.commons.feign.PictureFeignClient;
import com.alex.dragblog.utils.*;
import com.alex.dragblog.xo.util.WebUtil;
import com.alex.dragblog.xo.global.MessageConf;
import com.alex.dragblog.xo.global.RedisConf;
import com.alex.dragblog.xo.global.SQLConf;
import com.alex.dragblog.xo.global.SysConf;
import com.alex.dragblog.xo.mapper.AdminMapper;
import com.alex.dragblog.xo.service.AdminService;
import com.alex.dragblog.xo.vo.AdminVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    public void addOnlineAdmin(Admin admin) {
        HttpServletRequest request = RequestHolder.getRequest();
        Map<String, String> map = IpUtils.getOsAndBorwserInfo(request);
        String os = map.get(SysConf.OS);
        String browser = map.get(SysConf.BROWSER);
        String ip = IpUtils.getIpAddr(request);
        OnlineAdmin onlineAdmin = new OnlineAdmin();
        onlineAdmin.setTokenId(admin.getValidCode());
        onlineAdmin.setUsername(admin.getUsername());
        onlineAdmin.setBrowser(browser);
        onlineAdmin.setIpAddr(ip);
        onlineAdmin.setOs(os);
        onlineAdmin.setLoginTime(DateUtils.getNowTime());
        onlineAdmin.setRoleName(admin.getRole().getRoleName());
        //从redis中获取ip来源
        String jsonResult = redisUtils.get(SysConf.IP_SOURCE + BaseSysConf.REDIS_SEGMENTATION + ip);
        if (StringUtils.isEmpty(jsonResult)) {
            String address = IpUtils.getAddresses(SysConf.IP + SysConf.EQUAL_TO + ip, SysConf.UTF_8);
            if (StringUtils.isNotEmpty(address)) {
                onlineAdmin.setLoginLocation(address);
                redisUtils.setEx(SysConf.IP_SOURCE + BaseSysConf.REDIS_SEGMENTATION + ip, address, 24, TimeUnit.HOURS);
            }
        } else
            onlineAdmin.setLoginLocation(jsonResult);
        redisUtils.setEx(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + admin.getValidCode(), JsonUtils.objectToJson(onlineAdmin), 30 , TimeUnit.MINUTES);
    }

    @Override
    public String editMed(AdminVo adminVo) {
        HttpServletRequest request = RequestHolder.getRequest();
        Object id = request.getAttribute(SysConf.ADMIN_ID);
        if (id == null || id == "")
            return ResultUtils.result(SysConf.ERROR, MessageConf.OPERATION_FAIL);
        Admin admin = this.getById(id.toString());
        admin.setAvatar(adminVo.getAvatar());
        admin.setNickName(adminVo.getNickName());
        admin.setGender(adminVo.getGender());
        admin.setEmail(adminVo.getEmail());
        admin.setQqNumber(adminVo.getQqNumber());
        admin.setGithub(adminVo.getGithub());
        admin.setGitee(adminVo.getGitee());
        admin.setOccupation(adminVo.getOccupation());
        admin.setSummary(adminVo.getSummary());
        admin.setPersonResume(adminVo.getPersonResume());
        admin.updateById();
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public String changePwd(String oldPwd, String newPwd) {
        HttpServletRequest request = RequestHolder.getRequest();
        Object id = request.getAttribute(SysConf.ADMIN_ID);
        if (id == null || id == "") {
            return ResultUtils.result(SysConf.ERROR, MessageConf.INVALID_TOKEN);
        }
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
            return ResultUtils.result(SysConf.ERROR, MessageConf.PARAM_INCORRECT);
        }
        Admin admin = this.getById(id.toString());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPassword = encoder.matches(oldPwd, admin.getPassword());
        if (isPassword) {
            admin.setPassword(newPwd);
            admin.updateById();
            return ResultUtils.result(SysConf.SUCCESS, MessageConf.OPERATION_SUCCESS);
        }
        return ResultUtils.result(SysConf.ERROR, MessageConf.OPERATION_FAIL);
    }
}
