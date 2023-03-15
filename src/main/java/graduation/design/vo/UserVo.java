package graduation.design.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户信息")
public class UserVo {

    @ApiModelProperty("用户id")
    @TableField("id")
    private Integer id;

    @ApiModelProperty("用户类型")
    @TableField("role")
    private String role;

    @ApiModelProperty("账号")
    @TableField("account")
    private String account;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

}
