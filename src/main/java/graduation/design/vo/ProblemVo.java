package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "题目")
public class ProblemVo {

    @ApiModelProperty("id")
    private Integer id;

    @NotNull
    @ApiModelProperty("题目描述")
    private String content;

    @ApiModelProperty("答案")
    private String[] answer;

    @NotNull
    @ApiModelProperty("题目类型,(选择/判断/填空/简答/其它)")
    private String type;

    @NotNull
    @ApiModelProperty("难度,(高/中/低)")
    private String difficulty;

    @ApiModelProperty("相关节id")
    private Integer[] sectionIds;

    @ApiModelProperty("选择题选项")
    private String[] options;

    @ApiModelProperty("填空题填空数,不用传")
    private Integer num;

    @ApiModelProperty("练习/作业,为空的话默认练习及作业")
    private String[] uses;

}
