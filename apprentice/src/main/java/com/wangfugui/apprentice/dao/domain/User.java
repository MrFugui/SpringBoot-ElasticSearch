package com.wangfugui.apprentice.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author masiyi
 */
@Data
@ApiModel("用户")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("头像")
    private String photo;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("注册时间")
    private Date registerDate;

    @ApiModelProperty("联系方式")
    private String contactNo;

    @ApiModelProperty("逻辑删除")
    private Integer delFlag;

    private Date createTime;
    private Date updateTime;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("角色id")
    private Integer roleId;

}