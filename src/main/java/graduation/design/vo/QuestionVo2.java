package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "提问列表")
public class QuestionVo2 {

    @ApiModelProperty("提问id")
    private Integer questionId;

    @ApiModelProperty("学生id")
    private Integer studentId;

    @ApiModelProperty("学生姓名")
    private String name;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("提问时间")
    private LocalDateTime time;

    @ApiModelProperty("是否答复")
    private Integer answer;

    @ApiModelProperty("答复内容")
    private String solution;
}
