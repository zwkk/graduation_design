package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "班级成员")
public class ClassMembers {

    @ApiModelProperty("班级id")
    private Integer classId;

    @ApiModelProperty("教师/助教/学生id数组")
    private Integer[] ids;
}
