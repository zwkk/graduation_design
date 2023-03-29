package graduation.design.vo;

import graduation.design.entity.Label;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "工具详情")
public class ToolDetailVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("链接")
    private String link;

    @ApiModelProperty("评分人数")
    private Integer num;

    @ApiModelProperty("评分")
    private String score;

    @ApiModelProperty("一星人数")
    private Integer one;

    @ApiModelProperty("两星人数")
    private Integer two;

    @ApiModelProperty("三星人数")
    private Integer three;

    @ApiModelProperty("四星人数")
    private Integer four;

    @ApiModelProperty("五星人数")
    private Integer five;

    @ApiModelProperty("最近的一条评论")
    private CommentVo comment;

    @ApiModelProperty("其它相关工具列表")
    private List<ToolAbstractVo> list;

    @ApiModelProperty("标签")
    private Label[] labels;

}
