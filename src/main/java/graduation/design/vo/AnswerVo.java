package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "作答记录")
public class AnswerVo {

    @ApiModelProperty("题目id")
    private Integer problemId;

    @ApiModelProperty("学生作答结果")
    private String[] answer;
}
