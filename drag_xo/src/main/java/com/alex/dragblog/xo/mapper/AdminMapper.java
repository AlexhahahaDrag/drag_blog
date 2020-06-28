package com.alex.dragblog.xo.mapper;

import com.alex.dragblog.base.SuperMapper;
import com.alex.dragblog.commons.entity.Admin;
import org.apache.ibatis.annotations.Param;

/**
 *description:  管理员表mapper接口
 *author:       alex
 *createDate:   2020/6/26 22:44
 *version:      1.0.0
 */
public interface AdminMapper extends SuperMapper<Admin> {

    /**
     *description:  通过id获取管理员
     *author:       alex
     *@param:
     *@return:
     */
    Admin getAdminById(@Param("id") String id);
}
