package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "条件查询")
public class SelectVo {

    @ApiModelProperty("教师/助教id")
    private Integer userId;

    @ApiModelProperty("作业id")
    private Integer[] homeworkId;

    @ApiModelProperty("题目id")
    private Integer[] problemId;

    @ApiModelProperty("班级id")
    private Integer[] classId;

    @ApiModelProperty("学生id")
    private Integer[] studentId;
}
