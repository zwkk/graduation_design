package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "批改题目列表")
public class ProblemVo4 {

    @ApiModelProperty("题目id")
    private Integer problemId;

    @ApiModelProperty("学生id")
    private Integer studentId;

    @ApiModelProperty("作业id")
    private Integer homeworkId;

    @ApiModelProperty("题目分数")
    private String score;

    @ApiModelProperty("题目描述")
    private String content;

    @ApiModelProperty("题目类型")
    private String type;

    @ApiModelProperty("学生作答答案")
    private String[] studentAnswers;

    @ApiModelProperty("参考答案")
    private String[] answers;
}
