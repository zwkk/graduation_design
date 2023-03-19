package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "内容以及审核意见")
public class ContentReasonVo {

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("审核意见")
    private String reason;
}
