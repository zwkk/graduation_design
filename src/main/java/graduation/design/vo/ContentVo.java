package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "编辑教材")
public class ContentVo {

    @ApiModelProperty("作者id")
    private Integer authorId;

    @ApiModelProperty("节id")
    private Integer sectionId;

    @ApiModelProperty("教材内容")
    private String content;
}
