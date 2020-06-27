package com.alex.dragblog.base.vo;

import com.alex.dragblog.base.validator.Messages;
import com.alex.dragblog.base.validator.annotion.LongNotNull;
import com.alex.dragblog.base.validator.group.GetList;
import lombok.Data;

/**
 *description:  pageVo 用于分页
 *author:       alex
 *createDate:   2020/6/26 23:22
 *version:      1.0.0
 */
@Data
public class PageInfo<T> {

    //关键字
    private String keyword;

    //当前页
    @LongNotNull(groups = {GetList.class}, message = Messages.PAGE_NOT_NULL)
    private Long currentPage;

    //页大小
    @LongNotNull(groups = {GetList.class}, message = Messages.SIZE_NOT_NULL)
    private Long pageSize;
}
