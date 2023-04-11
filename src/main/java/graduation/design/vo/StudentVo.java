package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "学生")
public class StudentVo {

    @ApiModelProperty("学生id")
    private Integer studentId;

    @ApiModelProperty("学生名称")
    private String name;

    @ApiModelProperty("上次在线")
    private LocalDateTime lastLogin;

    @ApiModelProperty("未完成的作业id")
    private Integer homeworkId;

    @ApiModelProperty("未完成的作业名称")
    private String homeworkName;

}
