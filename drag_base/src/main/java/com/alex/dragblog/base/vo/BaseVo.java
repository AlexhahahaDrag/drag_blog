package com.alex.dragblog.base.vo;

import com.alex.dragblog.base.validator.annotion.IdValid;
import com.alex.dragblog.base.validator.group.Delete;
import com.alex.dragblog.base.validator.group.Update;

/**
 *description:  view object 表现层 基类对象
 *author:       alex
 *createDate:   2020/6/26 22:59
 *version:      1.0.0
 */
public class BaseVo<T> extends PageInfo<T> {

    /**
     * 唯一UID
     */
    @IdValid(groups = {Update.class, Delete.class})
    private String id;

    private Integer status;
}
