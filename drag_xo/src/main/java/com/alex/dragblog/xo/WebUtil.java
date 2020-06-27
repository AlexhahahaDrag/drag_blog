package com.alex.dragblog.xo;

import com.alex.dragblog.xo.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *description:  web有关的工具类
 *author:       alex
 *createDate:   2020/6/27 11:24
 *version:      1.0.0
 */
@Slf4j
@Component
public class WebUtil {

    @Autowired
    private SystemConfigService systemConfigService;
}
