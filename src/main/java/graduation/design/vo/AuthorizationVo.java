package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "权限列表")
public class AuthorizationVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("权限列表")
    private String[] authorizations;
}
