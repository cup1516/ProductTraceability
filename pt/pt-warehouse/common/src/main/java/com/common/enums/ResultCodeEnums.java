package com.common.enums;

public enum ResultCodeEnums {

    SUCCESS("0000"),
    ERROR("1111"),
    SYSTEM_ERROR("1000"),
    BUSSINESS_ERROR("2001"),
    VERIFY_CODE_ERROR("2002"),
    PARAM_ERROR("2002");

    private String code;

    ResultCodeEnums(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
