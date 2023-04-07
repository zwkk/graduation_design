package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "批改题目")
public class CorrectVo {

    @NotNull
    @ApiModelProperty("作业id")
    private Integer homeworkId;

    @NotNull
    @ApiModelProperty("学生id")
    private Integer studentId;

    @NotNull
    @ApiModelProperty("题目id")
    private Integer problemId;

    @NotNull
    @ApiModelProperty("得分")
    private Double score;
}
