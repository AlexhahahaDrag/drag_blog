package com.alex.dragblog.base.service.impl;

import com.alex.dragblog.base.SuperMapper;
import com.alex.dragblog.base.service.SuperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 *description:  SuperService实现类
 *author:       alex
 *createDate:   2020/6/26 22:38
 *version:      1.0.0
 */
public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T> {
}
