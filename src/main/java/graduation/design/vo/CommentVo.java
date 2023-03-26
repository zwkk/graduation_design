package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "评论列表")
public class CommentVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("评论标题")
    private String title;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评分")
    private Integer star;
}
