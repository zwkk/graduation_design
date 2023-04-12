package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "用户确认通知列表")
public class UserReceiveListVo {

    @ApiModelProperty("已确认用户列表")
    private List<UserReceiveVo> receiveUsers;

    @ApiModelProperty("未确认用户列表")
    private List<UserReceiveVo> unReceiveUsers;

}
