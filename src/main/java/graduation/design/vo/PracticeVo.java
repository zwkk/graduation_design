package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "章节练习")
public class PracticeVo {

    @ApiModelProperty("题目id")
    private Integer problemId;

    @ApiModelProperty("题目描述")
    private String content;

    @ApiModelProperty("已作答返回正确答案")
    private String[] answer;

    @ApiModelProperty("学生作答答案")
    private String[] studentAnswer;

    @ApiModelProperty("作答是否正确")
    private Integer correct;

    @ApiModelProperty("题目类型,(选择/判断/填空/简答/其它)")
    private String type;

    @ApiModelProperty("难度,(高/中/低)")
    private String difficulty;

    @ApiModelProperty("选择题选项")
    private String[] options;

    @ApiModelProperty("填空题填空数")
    private Integer num;
}
