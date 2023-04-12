package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "用户")
public class UserReceiveVo {

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("确认时间")
    private LocalDateTime receiveTime;
}
