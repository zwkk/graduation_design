package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "用户信息及权限列表")
public class UserAuthorizationVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("权限列表")
    private List<String> authorizations;
}
