package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "作业(学生)")
public class HomeworkVo3 {

    @ApiModelProperty("作业id")
    private Integer id;

    @ApiModelProperty("作业名称")
    private String name;

    @ApiModelProperty("开始时间,如2020-01-29T14:35:51")
    private LocalDateTime begin;

    @ApiModelProperty("结束时间,如2020-01-29T14:35:51")
    private LocalDateTime end;

    @ApiModelProperty("题目列表")
    private List<ProblemVo3> problems;

    @ApiModelProperty("总分数")
    private Double totalScore;

    @ApiModelProperty("总题数")
    private Integer totalNum;

    @ApiModelProperty("得分")
    private String score;

    @ApiModelProperty("完成时间,如2020-01-29T14:35:51")
    private LocalDateTime time;

    @ApiModelProperty("是否已提交")
    private Integer ifAnswer;

    @ApiModelProperty("是否批改")
    private Integer ifCorrect;

    @ApiModelProperty("是否过期")
    private Integer ifOverdue;

}
