package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "题目对应分值")
public class ProblemScore {

    @ApiModelProperty("题目id")
    private Integer id;

    @ApiModelProperty("分数")
    private Double score;

}
