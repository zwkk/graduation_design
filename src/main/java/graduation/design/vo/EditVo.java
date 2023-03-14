package graduation.design.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import graduation.design.entity.Section;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "所有节可编辑作者列表")
public class EditVo {

    @ApiModelProperty("章id")
    @TableField("id")
    private Integer chapterId;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("节编辑列表")
    private List<SectionEditVo> sectionEditVoList;
}
