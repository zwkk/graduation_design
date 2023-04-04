package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "作业")
public class HomeworkVo {

    @ApiModelProperty("作业id")
    private Integer id;

    @NotNull
    @ApiModelProperty("作业名称")
    private String name;

    @ApiModelProperty("开始时间")
    private LocalDateTime begin;

    @ApiModelProperty("结束时间")
    private LocalDateTime end;

    @NotNull
    @ApiModelProperty("题目列表")
    private ProblemScore[] problemScores;

    @ApiModelProperty("班级列表")
    private Integer[] classes;

}
