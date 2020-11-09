package com.pkgs.bullshit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2020-11-09 17:24
 */
@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {

    /**
     * 0:成功
     */
    SUCCESS(0, "成功"),
    /**
     * 1:失败
     */
    FAILURE(1, "失败");

    private final int value;
    private final String label;

}
