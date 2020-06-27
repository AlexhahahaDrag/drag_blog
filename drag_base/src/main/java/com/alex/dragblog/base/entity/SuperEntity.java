package com.alex.dragblog.base.entity;

import com.alex.dragblog.base.enums.EStatus;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *description:  Entity基类
 *author:       alex
 *createDate:   2020/6/26 22:09
 *version:      1.0.0
 */
@Data
public class SuperEntity <T extends Model> extends Model {

    private static final long serialVersionUID = -4851055162892178225L;

    //唯一uid
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //状态 0：失效 1： 生效
    private int status;

    //创建人
    private String creator;

    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //更新人
    private String updater;

    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //删除人
    private String deleter;

    //删除时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteTime;

    //是否删除
    @TableLogic
    private Integer isDelete;

    public SuperEntity() {
        this.status = EStatus.ENABLE.getValue();
    }
}
