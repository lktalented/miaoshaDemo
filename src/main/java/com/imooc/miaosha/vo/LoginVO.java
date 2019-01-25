package com.imooc.miaosha.vo;

import com.imooc.miaosha.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
/**
 * 登录页面参数对象
 * luokai
 * 2019/1/8 0008 下午 2:59
 */
@Data
public class LoginVO {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
