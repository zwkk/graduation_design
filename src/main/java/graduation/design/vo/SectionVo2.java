package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "节列表")
public class SectionVo2 {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("所属章id")
    private Integer chapterId;

    @ApiModelProperty("在所属章中的节顺序,比如1则排在第一位")
    private Integer sectionOrder;

    @ApiModelProperty("是否阅读")
    private Integer read;
}
