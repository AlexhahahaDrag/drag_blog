package com.alex.dragblog.xo.service.impl;

import com.alex.dragblog.base.enums.EMenuType;
import com.alex.dragblog.base.enums.EStatus;
import com.alex.dragblog.base.service.impl.SuperServiceImpl;
import com.alex.dragblog.commons.entity.CategoryMenu;
import com.alex.dragblog.utils.RedisUtils;
import com.alex.dragblog.utils.ResultUtils;
import com.alex.dragblog.utils.StringUtils;
import com.alex.dragblog.xo.global.MessageConf;
import com.alex.dragblog.xo.global.RedisConf;
import com.alex.dragblog.xo.global.SQLConf;
import com.alex.dragblog.xo.global.SysConf;
import com.alex.dragblog.xo.mapper.CategoryMenuMapper;
import com.alex.dragblog.xo.service.CategoryMenuService;
import com.alex.dragblog.xo.vo.CategoryMenuVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 *description:  菜单表服务类
 *author:       alex
 *createDate:   2020/7/4 16:19
 *version:      1.0.0
 */
@Service
public class CategoryMenuServiceImpl extends SuperServiceImpl<CategoryMenuMapper, CategoryMenu> implements CategoryMenuService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> getPageList(CategoryMenuVo categoryMenuVo) {
        Map<String, Object> resultMap = new HashMap<>();
        QueryWrapper<CategoryMenu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(categoryMenuVo.getKeyword().trim()))
            queryWrapper.like(SQLConf.NAME, categoryMenuVo.getKeyword().trim());
        if (categoryMenuVo.getMenuLevel() != 0)
            queryWrapper.eq(SQLConf.MENU_LEVEL, categoryMenuVo.getMenuLevel());
        Page<CategoryMenu> page = new Page<>(categoryMenuVo.getCurrentPage(), categoryMenuVo.getPageSize());
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE.getValue());
        queryWrapper.orderByDesc(SQLConf.SORT);
        IPage<CategoryMenu> pageList = this.page(page, queryWrapper);
        List<CategoryMenu> list = pageList.getRecords();
        List<String> ids = list.stream().filter(categoryMenu -> StringUtils.isNotEmpty(categoryMenu.getParentUid())).map(CategoryMenu::getParentUid).collect(Collectors.toList());
        if (ids.size() > 0) {
            Collection<CategoryMenu> parentList = this.listByIds(ids);
            Map<String, CategoryMenu> map = parentList.stream().collect(Collectors.toMap(CategoryMenu::getId, categoryMenu -> categoryMenu, (oldValue, newValue) -> newValue));
            list.forEach(categoryMenu -> {
                if (StringUtils.isNotEmpty(categoryMenu.getParentUid()))
                    categoryMenu.setParentCategoryMenu(map.get(categoryMenu.getParentUid()));
            });
            resultMap.put(SysConf.DATA, pageList);
        }
        pageList.setRecords(list);
        resultMap.put(SysConf.DATA, pageList);
        return resultMap;
    }

    @Override
    public List<CategoryMenu> getAllList(String keyword) {
        QueryWrapper<CategoryMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SQLConf.MENU_LEVEL, 1);
        if (StringUtils.isNotEmpty(keyword))
            queryWrapper.eq(SQLConf.ID, keyword);
        queryWrapper.orderByDesc(SQLConf.SORT);
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        queryWrapper.eq(SQLConf.MENU_TYPE, EMenuType.MENU.getValue());
        List<CategoryMenu> list = this.list(queryWrapper);
        //获取所有的id去寻找子目录
        List<String> ids = list.stream().map(CategoryMenu::getId).collect(Collectors.toList());
        QueryWrapper<CategoryMenu> childWrapper = new QueryWrapper<>();
        childWrapper.in(SQLConf.PARENT_UID, ids);
        childWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        Collection<CategoryMenu> childList = this.list(childWrapper);
        //获取所有的二级菜单，去寻找他的子按钮
        List<String> secondIds = childList.stream().map(CategoryMenu::getId).collect(Collectors.toList());
        QueryWrapper<CategoryMenu> buttonWrapper = new QueryWrapper<>();
        buttonWrapper.in(SQLConf.PARENT_UID, secondIds);
        buttonWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        Collection<CategoryMenu> buttonList = this.list(buttonWrapper);

        Map<String, List<CategoryMenu>> map = new HashMap<>();
        buttonList.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getParentUid())) {
                if (map.get(item.getParentUid()) == null) {
                    List<CategoryMenu> tempList = new ArrayList<>();
                    tempList.add(item);
                    map.put(item.getParentUid(), tempList);
                } else {
                    List<CategoryMenu> tempList = map.get(item.getParentUid());
                    tempList.add(item);
                    map.put(item.getParentUid(), tempList);
                }
            }
        });

        // 给二级菜单设置三级按钮
        childList.forEach(item -> {
            if (map.get(item.getId()) != null) {
                List<CategoryMenu> tempList = map.get(item.getId());
                Collections.sort(tempList, new Comparator<CategoryMenu>() {

                    /*
                     * int compare(CategoryMenu p1, CategoryMenu p2) 返回一个基本类型的整型，
                     * 返回负数表示：p1 小于p2，
                     * 返回0 表示：p1和p2相等，
                     * 返回正数表示：p1大于p2
                     */
                    @Override
                    public int compare(CategoryMenu o1, CategoryMenu o2) {

                        //按照CategoryMenu的Sort进行降序排列
                        if (o1.getSort() > o2.getSort()) {
                            return -1;
                        }
                        if (o1.getSort().equals(o2.getSort())) {
                            return 0;
                        }
                        return 1;
                    }

                });
                item.setChildCategoryMenu(tempList);
            }
        });


        // 给一级菜单设置二级菜单
        for (CategoryMenu parentItem : list) {

            List<CategoryMenu> tempList = new ArrayList<>();

            for (CategoryMenu item : childList) {

                if (item.getParentUid().equals(parentItem.getId())) {
                    tempList.add(item);
                }
            }
            Collections.sort(tempList);
            parentItem.setChildCategoryMenu(tempList);
        }
        return list;
    }


    @Override
    public List<CategoryMenu> getButtonAllList(String keyword) {
        QueryWrapper<CategoryMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SQLConf.MENU_LEVEL, "2");
        queryWrapper.orderByDesc(SQLConf.SORT);
        if (StringUtils.isNotEmpty(keyword)) {
            queryWrapper.eq(SQLConf.UID, keyword);
        }
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        queryWrapper.eq(SQLConf.MENU_TYPE, EMenuType.MENU);
        List<CategoryMenu> list = this.list(queryWrapper);

        //获取所有的ID，去寻找他的子目录
        List<String> ids = new ArrayList<>();
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getId())) {
                ids.add(item.getId());
            }
        });

        QueryWrapper<CategoryMenu> childWrapper = new QueryWrapper<>();
        childWrapper.in(SQLConf.PARENT_UID, ids);
        childWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        Collection<CategoryMenu> childList = this.list(childWrapper);
        Set<String> secondUidSet = new HashSet<>();
        Map<String, List<CategoryMenu>> map = new HashMap<>();
        childList.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getParentUid())) {

                secondUidSet.add(item.getParentUid());

                if (map.get(item.getParentUid()) == null) {
                    List<CategoryMenu> tempList = new ArrayList<>();
                    tempList.add(item);
                    map.put(item.getParentUid(), tempList);
                } else {
                    List<CategoryMenu> tempList = map.get(item.getParentUid());
                    tempList.add(item);
                    map.put(item.getParentUid(), tempList);
                }
            }
        });

        // 过滤不在Button列表中的二级菜单
        List<CategoryMenu> secondCategoryMenuList = new ArrayList<>();
        for (CategoryMenu secondCategoryMenu : list) {
            for (String uid : secondUidSet) {
                if (secondCategoryMenu.getId().equals(uid)) {
                    secondCategoryMenuList.add(secondCategoryMenu);
                    break;
                }
            }
        }

        // 给二级菜单设置三级按钮
        secondCategoryMenuList.forEach(item -> {
            if (map.get(item.getId()) != null) {
                List<CategoryMenu> tempList = map.get(item.getId());
                Collections.sort(tempList);
                item.setChildCategoryMenu(tempList);
            }
        });
        return list;
    }

    @Override
    public String addCategoryMenu(CategoryMenuVo categoryMenuVo) {
        CategoryMenu categoryMenu = new CategoryMenu();
        BeanUtils.copyProperties(categoryMenuVo, categoryMenu);
        //如果是一级菜单，将父id清空
        categoryMenu.setParentUid("");
        categoryMenu.insert();
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editCategoryMenu(CategoryMenuVo categoryMenuVo) {
        CategoryMenu categoryMenu = this.getById(categoryMenuVo.getId());
        BeanUtils.copyProperties(categoryMenuVo, categoryMenu);
        categoryMenu.updateById();
        //修改后需要清空redis中所有admin的访问路径
        deleteAdminVisitUrl();
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String deleteCategoryMenu(CategoryMenuVo categoryMenuVo) {
        QueryWrapper<CategoryMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        queryWrapper.in(SQLConf.PARENT_UID, categoryMenuVo.getId());
        Integer menuCount = this.count(queryWrapper);
        if (menuCount > 0)
            return ResultUtils.result(SysConf.ERROR, MessageConf.CHILDREN_MENU_UNDER_THIS_MENU);
        CategoryMenu categoryMenu = this.getById(categoryMenuVo.getId());
        categoryMenu.setStatus(EStatus.DISABLE.getValue());
        categoryMenu.setUpdateTime(new Date());
        categoryMenu.updateById();
        // 修改成功后，需要删除redis中所有的admin访问路径
        deleteAdminVisitUrl();
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String stickCategoryMenu(CategoryMenuVo categoryMenuVo) {
        CategoryMenu categoryMenu = this.getById(categoryMenuVo.getId());
        //查找出最大的那一个
        QueryWrapper<CategoryMenu> queryWrapper = new QueryWrapper<>();

        //如果是二级菜单 或者 按钮，就在当前的兄弟中，找出最大的一个
        if (categoryMenu.getMenuLevel() == 2 || categoryMenu.getMenuType() == EMenuType.BUTTON.getValue()) {
            queryWrapper.eq(SQLConf.PARENT_UID, categoryMenu.getParentUid());
        }

        queryWrapper.eq(SQLConf.MENU_LEVEL, categoryMenu.getMenuLevel());

        queryWrapper.orderByDesc(SQLConf.SORT);

        queryWrapper.last("limit 1");

        CategoryMenu maxSort = this.getOne(queryWrapper);

        if (StringUtils.isEmpty(maxSort.getId())) {
            return ResultUtils.result(SysConf.ERROR, MessageConf.OPERATION_FAIL);
        }

        Integer sortCount = maxSort.getSort() + 1;

        categoryMenu.setSort(sortCount);
        categoryMenu.setUpdateTime(new Date());
        categoryMenu.updateById();
        return ResultUtils.result(SysConf.SUCCESS, MessageConf.OPERATION_SUCCESS);
    }

    private void deleteAdminVisitUrl() {
        Set<String> keys = redisUtils.keys(RedisConf.ADMIN_VISIT_MENU + "*");
        redisUtils.delete(keys);
    }
}
