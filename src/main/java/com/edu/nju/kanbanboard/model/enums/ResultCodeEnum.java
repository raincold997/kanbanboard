package com.edu.nju.kanbanboard.model.enums;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 雨冷
 * @date 2019/3/31
 */
public enum ResultCodeEnum {
    SUCCESS(1),

    FAIL(0);

    Integer code;

    ResultCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
