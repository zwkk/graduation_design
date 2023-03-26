package graduation.design.vo;

import graduation.design.entity.Label;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "工具列表")
public class ToolAbstractVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("评分")
    private String score;

    @ApiModelProperty("标签")
    private Label[] labels;
}
