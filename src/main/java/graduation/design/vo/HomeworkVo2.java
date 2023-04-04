package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "作业")
public class HomeworkVo2 {

    @ApiModelProperty("作业id")
    private Integer id;

    @ApiModelProperty("作业名称")
    private String name;

    @ApiModelProperty("开始时间,如2020-01-29T14:35:51")
    private LocalDateTime begin;

    @ApiModelProperty("结束时间,如2020-01-29T14:35:51")
    private LocalDateTime end;

    @ApiModelProperty("题目列表")
    private List<ProblemVo2> problems;

    @ApiModelProperty("总分数")
    private Double totalScore;

    @ApiModelProperty("总题数")
    private Integer totalNum;
}
