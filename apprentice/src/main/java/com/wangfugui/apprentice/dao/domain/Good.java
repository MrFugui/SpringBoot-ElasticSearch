package com.wangfugui.apprentice.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 点赞表
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-23
 */
@ApiModel(value = "Good对象", description = "点赞表")
public class Good implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("点赞表id")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("动态表id")
    private Integer dynamicId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建用户")
    private Integer createUser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "Good{" +
        "id=" + id +
        ", dynamicId=" + dynamicId +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        "}";
    }
}
