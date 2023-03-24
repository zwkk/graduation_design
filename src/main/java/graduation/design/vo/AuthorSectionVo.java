package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "编辑某一节作者列表")
public class AuthorSectionVo {

    @ApiModelProperty("作者id数组")
    private Integer[] authorIds;

    @ApiModelProperty("节id")
    private Integer sectionId;

}
