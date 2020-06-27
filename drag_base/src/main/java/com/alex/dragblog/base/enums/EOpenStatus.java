package com.alex.dragblog.base.enums;

/**
 *description:  操作状态枚举类
 *author:       alex
 *createDate:   2020/6/27 17:45
 *version:      1.0.0
 */
public enum EOpenStatus {
    OPEN_STATUS("open", "0"),
    CLOSE_STATUS("close", "1");

    private String code;

    private String value;

    EOpenStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
