package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "回复")
public class SolutionVo {

    @NotNull
    @ApiModelProperty("提问id")
    private Integer questionId;

    @ApiModelProperty("回复")
    private String solution;
}
