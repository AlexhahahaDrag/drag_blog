package com.alex.dragblog.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 *description:  mapper父类 注意这个类不要让mybatis-plus扫描到
 *author:       alex
 *createDate:   2020/6/26 22:41
 *version:      1.0.0
 */
public interface SuperMapper<T> extends BaseMapper<T> {
}
