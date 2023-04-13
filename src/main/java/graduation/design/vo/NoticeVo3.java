package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "通知")
public class NoticeVo3 {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("发布者id")
    private Integer userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("正文")
    private String content;

    @ApiModelProperty("通知时间")
    private LocalDateTime time;

    @ApiModelProperty("接收用户id列表")
    private Integer[] userIds;
}
