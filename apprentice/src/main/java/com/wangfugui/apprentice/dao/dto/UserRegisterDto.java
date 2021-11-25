package com.wangfugui.apprentice.dao.dto;

import com.wangfugui.apprentice.dao.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author masiyi
 */
@Data
@ApiModel("用户注册")
public class UserRegisterDto extends User {

    @ApiModelProperty("验证码值")
    private String verCode;

    @ApiModelProperty("验证码key")
    private String verKey;

    @ApiModelProperty("再次确认密码")
    private String rePassWord;


}