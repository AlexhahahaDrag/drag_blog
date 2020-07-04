package com.alex.dragblog.admin.restapi;

import com.alex.dragblog.commons.config.JwtHelper;
import com.alex.dragblog.commons.config.jwt.Audience;
import com.alex.dragblog.commons.feign.PictureFeignClient;
import com.alex.dragblog.xo.WebUtil;
import com.alex.dragblog.xo.service.AdminService;
import com.alex.dragblog.xo.service.CategoryMenuService;
import com.alex.dragblog.xo.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *description:  登录管理restApi（为了更好的使用security放行把登录管理放在authRestApi中）
 *author:       alex
 *createDate:   2020/6/26 22:00
 *version:      1.0.0
 */
@RestController
@RequestMapping("/auth")
@Api(value = "登陆管理相关接口", tags = {"登录管理相关接口"})
@Slf4j
public class LoginRestApi {

    @Autowired
    private WebUtil webUtil;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private CategoryMenuService categoryMenuService;

    @Autowired
    private Audience audience;

    @Autowired
    @Value(value = "${tokenHead}")
    private String tokenHead;

    @Autowired
    @Value(value = "isRememberMeExpiresSecond")
    private int loginExpireSecond;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        @ApiParam(name = "username", value = "用户名或邮箱或手机号", required = false) @RequestParam(name = "username", required = false) String username,
                        @ApiParam(name = "password", value = "密码", required = false) @RequestParam(name = "password", required = false) String password,
                        @ApiParam(name = "isRememberMe", value = "是否记住账号密码", required = false) @RequestParam(name = "isRememberMe", required = false) String isRememberMe) {

    }
}
