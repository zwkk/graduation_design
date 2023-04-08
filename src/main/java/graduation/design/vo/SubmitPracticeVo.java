package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "章节练习作答")
public class SubmitPracticeVo {

    @ApiModelProperty("节id")
    private Integer sectionId;

    @ApiModelProperty("学生id")
    private Integer studentId;

    @ApiModelProperty("题目id")
    private Integer problemId;

    @ApiModelProperty("作答结果")
    private String[] studentAnswer;
}
