package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "作者修改上传的教材")
public class ModifyVo {

    @ApiModelProperty("审核id")
    private Integer bookExamineId;

    @ApiModelProperty("文章内容")
    private String content;

}
