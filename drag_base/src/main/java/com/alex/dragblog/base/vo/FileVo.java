package com.alex.dragblog.base.vo;

import java.util.List;
import java.util.Map;

/**
 *description:  fileVo
 *author:       alex
 *createDate:   2020/6/28 21:53
 *version:      1.0.0
 */
public class FileVo extends BaseVo<FileVo> {

    /**
     * 如果是用户上传，则包含用户uid
     */
    private String userId;

    /**
     * 如果是管理员上传，则包含管理员uid
     */
    private String adminId;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 模块名
     */
    private String sortName;

    /**
     * 图片Url集合
     */
    private List<String> urlList;

    /**
     * 系统配置
     */
    private Map<String, Object> systemConfig;
}
