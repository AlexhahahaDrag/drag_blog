package com.alex.dragblog.admin.restapi;

import com.alex.dragblog.admin.annotation.AuthorityVerify;
import com.alex.dragblog.admin.annotation.OperationLogger;
import com.alex.dragblog.base.enums.EStatus;
import com.alex.dragblog.commons.entity.Admin;
import com.alex.dragblog.commons.entity.Role;
import com.alex.dragblog.commons.feign.PictureFeignClient;
import com.alex.dragblog.utils.RedisUtils;
import com.alex.dragblog.utils.ResultUtils;
import com.alex.dragblog.utils.StringUtils;
import com.alex.dragblog.xo.global.MessageConf;
import com.alex.dragblog.xo.global.SQLConf;
import com.alex.dragblog.xo.global.SysConf;
import com.alex.dragblog.xo.service.AdminService;
import com.alex.dragblog.xo.service.RoleService;
import com.alex.dragblog.xo.util.WebUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *description:  管理员管理
 *author:       alex
 *createDate:   2020/7/13 20:57
 *version:      1.0.0
 */
@RestController
@RequestMapping("/admin")
@Api(value = "管理员相关接口", tags = {"管理员相关接口"})
@Slf4j
public class AdminRestApi {

    @Autowired
    private WebUtil webUtil;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @Value(value = "${DEFAULT_PWD}")
    private String DEFAULT_PWD;

    @AuthorityVerify
    @ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public String getList(HttpServletRequest request,
                          @ApiParam(name = "keyword", value = "关键字", required = false) @RequestParam(name = "keyword", required = false) String keyword,
                          @ApiParam(name = "currentPage", value = "当前页数", required = false) @RequestParam(name = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                          @ApiParam(name = "pageSize", value = "每页显示数目", required = false) @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize ) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        String pictureResult = null;
        if (StringUtils.isNotBlank(keyword))
            queryWrapper.like(SQLConf.USER_NAME, keyword).or().like(SQLConf.MOBILE, keyword).or().like(SQLConf.EMAIL, keyword);
        Page<Admin> page = new Page<>(currentPage, pageSize);
        //去除密码
        queryWrapper.select(Admin.class, i -> !i.getProperty().equals(SQLConf.PASS_WORD));
        IPage<Admin> pageList = adminService.page(page, queryWrapper);
        List<Admin> list =pageList.getRecords();
        final StringBuffer fileIds = new StringBuffer();
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getAvatar()))
                fileIds.append(item.getAvatar() + SysConf.FILE_SEGMENTATION);
        });
        Map<String, String> pictureMap = new HashMap<>();
        if (fileIds != null)
            pictureResult = pictureFeignClient.getPicture(fileIds.toString(), SysConf.FILE_SEGMENTATION);
        List<Map<String, Object>> picList = webUtil.getPictureMap(pictureResult);
        picList.forEach(item -> {
            pictureMap.put(item.get(SQLConf.ID).toString(), item.get(SQLConf.URL).toString());
        });
        for (Admin admin : list) {
            Role role = roleService.getById(admin.getRoleUid());
            admin.setRole(role);
            //获取图片
            if (StringUtils.isNotEmpty(admin.getAvatar())) {
                List<String> pictureIdsTemp = StringUtils.changeStringToString(admin.getAvatar(), SysConf.FILE_SEGMENTATION);
                List<String> pictureListTemp = new ArrayList<>();
                pictureIdsTemp.forEach(picture -> {
                    if (pictureMap.get(picture) != null && pictureMap.get(picture) != "")
                        pictureListTemp.add(pictureMap.get(picture));
                });
                admin.setPhotoList(pictureListTemp);
            }
        }
        return ResultUtils.result(SysConf.SUCCESS, pageList);
    }

    @AuthorityVerify
    @OperationLogger(value = "重置用户密码")
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    @PostMapping("/resetPwd")
    public String resetPwd(HttpServletRequest request,
                           @ApiParam(name = "id", value = "管理员id", required = false) @RequestParam(name = "id", required = false) String id) {
        if (StringUtils.isEmpty(id))
            return ResultUtils.result(SysConf.SUCCESS, MessageConf.PARAM_INCORRECT);
        Admin admin = adminService.getAdminById(id);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(DEFAULT_PWD));
        admin.updateById();
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.UPDATE_SUCCESS);
    }

    @AuthorityVerify
    @OperationLogger(value = "注册管理员")
    @ApiOperation(value = "注册管理员", notes = "注册管理员")
    @PostMapping("/add")
    public String add(HttpServletRequest request,
                      @ApiParam(name = "assignBody", value = "管理员注册对象", required = false) @RequestBody Admin assignBody) {
        String mobile = assignBody.getMobile();
        String username = assignBody.getUsername();
        String email = assignBody.getEmail();
        if (StringUtils.isEmpty(username))
            return ResultUtils.result(SysConf.ERROR, MessageConf.PARAM_INCORRECT);
        if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email))
            return ResultUtils.result(SysConf.ERROR, "邮箱和手机号至少有一项不能为空");
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysConf.USER_NAME, username);
        Admin admin = adminService.getOne(queryWrapper);
        if (admin != null)
            return ResultUtils.result(SysConf.ERROR, "管理员账号已存在");
        //设置为未审核状态
        assignBody.setStatus(EStatus.ENABLE.getValue());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        //设置默认密码
        assignBody.setPassword(encoder.encode(DEFAULT_PWD));
        adminService.save(assignBody);
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
    }
}
