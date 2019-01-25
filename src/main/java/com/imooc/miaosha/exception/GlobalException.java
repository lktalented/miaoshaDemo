package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import lombok.Getter;

/**
 * 全局异常
 * luokai
 * 2019/1/8 0008 下午 4:05
 */
@Getter
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMsg msg;

    public GlobalException(CodeMsg msg) {
        super(msg.toString());
        this.msg = msg;
    }
}
