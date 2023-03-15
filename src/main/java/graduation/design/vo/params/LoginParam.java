package graduation.design.vo.params;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "登录参数")
public class LoginParam {

    @ApiModelProperty("账号")
    @TableField("account")
    private String account;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;
}
