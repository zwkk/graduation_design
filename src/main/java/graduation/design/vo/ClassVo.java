package graduation.design.vo;

import graduation.design.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "班级详情")
public class ClassVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("班级名")
    private String name;

    @ApiModelProperty("学生人数")
    private Integer num;

    @ApiModelProperty("学期")
    private String term;

    @ApiModelProperty("教师")
    private List<User> teachers;

    @ApiModelProperty("助教")
    private List<User> assistants;

    @ApiModelProperty("学生")
    private List<User> students;
}
