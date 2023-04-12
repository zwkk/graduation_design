package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "通知")
public class NoticeVo {

    @ApiModelProperty("主键")
    private Integer id;

    @NotNull
    @ApiModelProperty("发布者id")
    private Integer userId;

    @NotNull
    @ApiModelProperty("标题")
    private String title;

    @NotNull
    @ApiModelProperty("正文")
    private String content;

    @NotNull
    @ApiModelProperty("接收用户id列表")
    private Integer[] userIds;

}
