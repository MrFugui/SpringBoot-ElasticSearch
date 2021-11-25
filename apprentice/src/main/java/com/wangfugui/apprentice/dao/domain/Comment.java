package com.wangfugui.apprentice.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-23
 */
@ApiModel(value = "Comment对象", description = "评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("动态id")
    private Integer dynamicId;

    @ApiModelProperty("评论内容")
    private String commentText;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private Integer createUser;

    @ApiModelProperty("点赞数量")
    private Integer goodNumber;

    @ApiModelProperty("是否删除")
    private Blob deleteFlag;

    @ApiModelProperty("评论数量")
    private Integer talkNumber;

    @ApiModelProperty("评论表父id")
    private Integer commentId;


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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public Integer getGoodNumber() {
        return goodNumber;
    }

    public void setGoodNumber(Integer goodNumber) {
        this.goodNumber = goodNumber;
    }

    public Blob getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Blob deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getTalkNumber() {
        return talkNumber;
    }

    public void setTalkNumber(Integer talkNumber) {
        this.talkNumber = talkNumber;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "Comment{" +
        "id=" + id +
        ", dynamicId=" + dynamicId +
        ", commentText=" + commentText +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", goodNumber=" + goodNumber +
        ", deleteFlag=" + deleteFlag +
        ", talkNumber=" + talkNumber +
        ", commentId=" + commentId +
        "}";
    }
}
