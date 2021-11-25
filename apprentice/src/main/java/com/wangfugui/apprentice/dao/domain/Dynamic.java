package com.wangfugui.apprentice.dao.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 动态表
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-23
 */
@ApiModel(value = "Dynamic对象", description = "动态表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dynamic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("表id")
      private Integer id;

    @ApiModelProperty("动态内容")
    private String dynamicText;

    @ApiModelProperty("图片id")
    private Integer fileId;

    @ApiModelProperty("点赞数量")
    private Integer goodNumber;

    @ApiModelProperty("评论数量")
    private Integer talkNumber;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("是否删除")
    private Boolean deleteFlag;

    @ApiModelProperty("创建人")
    private Integer createUser;

    @ApiModelProperty("地址")
    private String address;

    public Dynamic( String dynamicText,Integer id) {
        this.id = id;
        this.dynamicText = dynamicText;
    }
}
