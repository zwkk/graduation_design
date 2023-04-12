package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "提问")
public class QuestionVo {

    @ApiModelProperty("主键")
    private Integer id;

    @NotNull
    @ApiModelProperty("学生id")
    private Integer studentId;

    @NotNull
    @ApiModelProperty("标题")
    private String title;

    @NotNull
    @ApiModelProperty("内容")
    private String content;
}
