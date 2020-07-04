package com.alex.dragblog.base.enums;

/**
 *description:  菜单枚举类
 *author:       alex
 *createDate:   2020/7/4 16:56
 *version:      1.0.0
 */
public enum EMenuType {
    MENU("MENU", 0),
    BUTTON("BUTTON", 1);

    private String code;

    private int value;

    EMenuType(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

