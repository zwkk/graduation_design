package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "作答题目列表")
public class ProblemVo2 {

    @ApiModelProperty("题目id")
    private Integer id;

    @ApiModelProperty("题目分数")
    private String score;

    @ApiModelProperty("题目描述")
    private String content;

    @ApiModelProperty("选择题选项")
    private String[] options;

    @ApiModelProperty("题目类型")
    private String type;

    @ApiModelProperty("填空题填空数")
    private Integer num;
}
