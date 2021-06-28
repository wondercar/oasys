package com.hellowy.oasys.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 聊天信息实体类
 *
 * @author wondercar
 * @date 2021/3/28
 */
@Data
@ApiModel(value = "聊天信息实体类", description = "")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {
    @ApiModelProperty(value = "发送方")
    private String from;
    @ApiModelProperty(value = "接收方")
    private String to;
    @ApiModelProperty(value = "内容主体")
    private String content;
    @ApiModelProperty(value = "发送时间")
    private LocalDateTime date;
    @ApiModelProperty(value = "发送方昵称")
    private String fromNickName;
}
