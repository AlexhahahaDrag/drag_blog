package com.alex.dragblog.base.enums;

import lombok.Data;

/**
 *description:  状态枚举类
 *author:       alex
 *createDate:   2020/6/26 22:17
 *version:      1.0.0
 */
public enum EStatus {

    //删除的
    DISABLE("DISABLE", 0),
    ENABLE("ENABLE", 1),
    FREEZE("FREEZE", 2),
    STICK("STICK", 3);

    private String code;

    private Integer value;

    EStatus(String code, Integer value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
