package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "作答记录")
public class Record {

    @NotNull
    @ApiModelProperty("学生id")
    private Integer studentId;

    @NotNull
    @ApiModelProperty("作业id")
    private Integer homeworkId;

    @ApiModelProperty("学生题目作答列表")
    private AnswerVo[] answers;
}
