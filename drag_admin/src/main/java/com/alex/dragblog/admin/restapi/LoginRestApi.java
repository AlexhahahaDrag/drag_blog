package com.alex.dragblog.admin.restapi;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
