package com.example.UserService.enums;

public enum StatusCodeEnum {

    UD200("UD200")
    ;

    private final String statusCode;

    StatusCodeEnum(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
