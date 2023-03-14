package graduation.design.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import graduation.design.entity.Author;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "单个节可编辑作者列表")
public class SectionEditVo {

    @ApiModelProperty("节id")
    @TableField("id")
    private Integer sectionId;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("作者列表")
    @TableField("authors")
    private List<Author> authors;
}
