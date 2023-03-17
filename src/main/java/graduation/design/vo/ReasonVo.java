package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "审核不通过")
public class ReasonVo {

    @ApiModelProperty("审核id")
    private Integer id;

    @ApiModelProperty("拒绝理由")
    private String reason;
}
