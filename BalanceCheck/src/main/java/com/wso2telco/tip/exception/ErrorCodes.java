package com.wso2telco.tip.exception;

/**
 * Created by yasith on 1/17/17.
 */
public enum ErrorCodes {

    INTERNAL_SERVER_ERROR ("Internal Server Error", 500),
    JSON_PROCESSING_ERROR ("Json Processing Error", 501),
    INPUT_ERROR ("Input Error", 502),
    URL_FORMAT_ERROR ("URL format error",503),
    IO_EXCEPTION ("IO exception",504);

    private String code;
    private int key;

    ErrorCodes(String code, int key) {
        this.key = key;
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public int getKey() {
        return key;
    }
}
