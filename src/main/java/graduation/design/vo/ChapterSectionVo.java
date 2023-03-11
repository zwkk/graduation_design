package graduation.design.vo;

import graduation.design.entity.Section;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "教材内容列表")
public class ChapterSectionVo {

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty("章数")
    private Integer num;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("节列表")
    private List<Section> sectionList;

}
