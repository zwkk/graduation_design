package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "个人信息")
public class InformationVo {

    @NotNull
    @ApiModelProperty("用户id")
    private Integer id;

    @NotNull
    @ApiModelProperty("用户名")
    private String name;
}
