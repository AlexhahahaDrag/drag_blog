package com.alex.dragblog.admin.restapi;

import com.alex.dragblog.base.enums.EMenuType;
import com.alex.dragblog.commons.config.JwtHelper;
import com.alex.dragblog.commons.config.jwt.Audience;
import com.alex.dragblog.commons.entity.Admin;
import com.alex.dragblog.commons.entity.CategoryMenu;
import com.alex.dragblog.commons.entity.Role;
import com.alex.dragblog.commons.feign.PictureFeignClient;
import com.alex.dragblog.utils.*;
import com.alex.dragblog.xo.util.WebUtil;
import com.alex.dragblog.xo.global.MessageConf;
import com.alex.dragblog.xo.global.RedisConf;
import com.alex.dragblog.xo.global.SQLConf;
import com.alex.dragblog.xo.global.SysConf;
import com.alex.dragblog.xo.service.AdminService;
import com.alex.dragblog.xo.service.CategoryMenuService;
import com.alex.dragblog.xo.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    WebUtil webUtil;

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
    private String loginExpireSecond;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        @ApiParam(name = "username", value = "用户名或邮箱或手机号", required = false) @RequestParam(name = "username", required = false) String username,
                        @ApiParam(name = "password", value = "密码", required = false) @RequestParam(name = "password", required = false) String password,
                        @ApiParam(name = "isRememberMe", value = "是否记住账号密码", required = false) @RequestParam(name = "isRememberMe", required = false) Long isRememberMe) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            return ResultUtils.result(SysConf.ERROR, "账号密码不能为空");
        String ip = IpUtils.getIpAddr(request);
        String limitCount = redisUtils.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        if (StringUtils.isNotEmpty(limitCount)) {
            Integer tempLimitCount = Integer.valueOf(limitCount);
            if (tempLimitCount >= 5)
                return ResultUtils.result(SysConf.ERROR, "密码输错次数过多，已被锁定30分钟");
        }
        Boolean isEmail = CheckUtils.checkEmail(username);
        Boolean isMobilePhone = CheckUtils.checkMobilePhone(username);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (isEmail)
            queryWrapper.eq(SQLConf.EMAIL, username);
        else if (isMobilePhone)
            queryWrapper.eq(SQLConf.MOBILE, username);
        else
            queryWrapper.eq(SQLConf.USER_NAME, username);
        Admin admin = adminService.getOne(queryWrapper);
        if (admin == null)
            return ResultUtils.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        //验证密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPassword = encoder.matches(password, admin.getPassword());
        if (!isPassword)
            return ResultUtils.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        //获取角色
        List<String> roleUids = new ArrayList<>();
        roleUids.add(admin.getRoleUid());
        List<Role> roles = (List<Role>) roleService.listByIds(roleUids);
        if (roles == null || roles.isEmpty())
            return ResultUtils.result(SysConf.ERROR, MessageConf.NO_ROLE);
        StringBuilder sb = new StringBuilder();
        for (Role role : roles)
            sb.append(role.getRoleName() + ",");
        String roleName = sb.replace(sb.length() - 1, sb.length(), "").toString();
        long expiration = isRememberMe == 1 ? Long.parseLong(loginExpireSecond) : audience.getExpiresSecond();
        String jwtToken = jwtHelper.createJWT(admin.getUsername(), admin.getId(), roleName, audience.getClientId(), audience.getName(), expiration * 1000, audience.getBase64Security());
        String token = tokenHead + jwtToken;
        Map<String, Object> result = new HashMap<>();
        result.put(SysConf.TOKEN, token);
        //进行登录相关操作
        Integer count = admin.getLoginCount() + 1;
        admin.setLoginCount(count);
        admin.setLastLoginIp(ip);
        admin.setLastLoginTime(new Date());
        admin.updateById();
        //设置token到validCode，用于记录登录用户信息
        admin.setValidCode(token);
        admin.setRole(roles.get(0));
        //添加在线用户到redis中
        adminService.addOnlineAdmin(admin);
        return ResultUtils.result(SysConf.SUCCESS, result);
    }

    @ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
    @GetMapping(value = "/info")
    public String info(HttpServletRequest request,
                       @ApiParam(name = "token", value = "token令牌", required = true) @RequestParam(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        Object id = request.getAttribute(SysConf.ADMIN_ID);
        if (id == null)
            return ResultUtils.result(SysConf.ERROR, "token用户过期");
        Admin admin = adminService.getAdminById((String) id);
        map.put(SysConf.TOKEN, token);
        //获取图片
        // TODO: 2020/7/5
        //获取图片
        if (StringUtils.isNotEmpty(admin.getAvatar())) {
            String pictureList = this.pictureFeignClient.getPicture(admin.getAvatar(), SysConf.FILE_SEGMENTATION);
            admin.setPhotoList(webUtil.getPicture(pictureList));

            List<String> list = webUtil.getPicture(pictureList);

            if (list.size() > 0) {
                map.put(SysConf.AVATAR, list.get(0));
            } else {
                map.put(SysConf.AVATAR, "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            }
        }
        List<String> roleIds = Collections.singletonList(admin.getRoleUid());
        Collection<Role> roles = roleService.listByIds(roleIds);
        map.put(SysConf.ROLES, roles);
        return ResultUtils.result(SysConf.SUCCESS, map);
    }

    @ApiOperation(value = "获取当前用户的菜单", notes = "获取当前用户的菜单", response = String.class)
    @GetMapping(value = "/getMenu")
    public String getMenu(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        Collection<CategoryMenu> categoryMenuList = new ArrayList<>();
        Admin admin = adminService.getById(request.getAttribute(SysConf.ADMIN_UID).toString());

        List<String> roleUid = new ArrayList<>();
        roleUid.add(admin.getRoleUid());
        Collection<Role> roleList = roleService.listByIds(roleUid);

        List<String> categoryMenuUids = new ArrayList<>();

        roleList.forEach(item -> {
            String caetgoryMenuUids = item.getCategoryMenuUids();
            String[] uids = caetgoryMenuUids.replace("[", "").replace("]", "").replace("\"", "").split(",");
            for (int a = 0; a < uids.length; a++) {
                categoryMenuUids.add(uids[a]);
            }

        });
        categoryMenuList = categoryMenuService.listByIds(categoryMenuUids);

        // 从三级级分类中查询出 二级分类
        List<CategoryMenu> buttonList = new ArrayList<>();
        Set<String> secondMenuUidList = new HashSet<>();
        categoryMenuList.forEach(item -> {
            // 查询二级分类
            if (item.getMenuType() == EMenuType.MENU.getValue() && item.getMenuLevel() == SysConf.TWO) {
                secondMenuUidList.add(item.getId());
            }
            // 从三级分类中，得到二级分类
            if (item.getMenuType() == EMenuType.BUTTON.getValue() && StringUtils.isNotEmpty(item.getParentUid())) {
                // 找出二级菜单
                secondMenuUidList.add(item.getParentUid());
                // 找出全部按钮
                buttonList.add(item);
            }
        });

        Collection<CategoryMenu> childCategoryMenuList = new ArrayList<>();
        Collection<CategoryMenu> parentCategoryMenuList = new ArrayList<>();
        List<String> parentCategoryMenuUids = new ArrayList<>();

        if (secondMenuUidList.size() > 0) {
            childCategoryMenuList = categoryMenuService.listByIds(secondMenuUidList);
        }

        childCategoryMenuList.forEach(item -> {
            //选出所有的二级分类
            if (item.getMenuLevel() == SysConf.TWO) {

                if (StringUtils.isNotEmpty(item.getParentUid())) {
                    parentCategoryMenuUids.add(item.getParentUid());
                }
            }
        });

        if (parentCategoryMenuUids.size() > 0) {
            parentCategoryMenuList = categoryMenuService.listByIds(parentCategoryMenuUids);
        }

        List<CategoryMenu> list = new ArrayList<>(parentCategoryMenuList);

        //对parent进行排序
        Collections.sort(list);
        map.put(SysConf.PARENT_LIST, list);
        map.put(SysConf.SON_LIST, childCategoryMenuList);
        map.put(SysConf.BUTTON_LIST, buttonList);
        return ResultUtils.result(SysConf.SUCCESS, map);
    }

    @ApiOperation(value = "推出登录", notes = "退出登录", response = String.class)
    @PostMapping(value = "/logout")
    public String logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getAttribute(SysConf.TOKEN).toString();
        redisUtils.delete(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + token);
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.OPERATION_SUCCESS);
    }

    private Integer setLoginCommit(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        Integer surplusCount = 5;
        String count = redisUtils.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        if (StringUtils.isEmpty(count)) {
            surplusCount--;
            redisUtils.setEx(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip, "1", 30, TimeUnit.MINUTES);
        } else {
            Integer tempCount = Integer.valueOf(count) + 1;
            surplusCount -= tempCount;
            redisUtils.setEx(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip, tempCount + "", 30, TimeUnit.MINUTES);;
        }
        //返回剩余次数
        return surplusCount;
    }
}
