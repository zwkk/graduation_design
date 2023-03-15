package graduation.design.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import graduation.design.entity.Section;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "章节目录列表")
public class ChapterSectionVo {

    @ApiModelProperty("章id")
    @TableField("id")
    private Integer id;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("节列表")
    private List<Section> sectionList;

}
