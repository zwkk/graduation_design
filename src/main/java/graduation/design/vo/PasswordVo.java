package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "修改密码")
public class PasswordVo {

    @NotNull
    @ApiModelProperty("账号")
    private String account;

    @NotNull
    @ApiModelProperty("旧密码")
    private String oldPassword;

    @NotNull
    @ApiModelProperty("新密码")
    private String newPassword;
}
