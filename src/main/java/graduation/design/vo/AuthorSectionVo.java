package graduation.design.vo;

import graduation.design.entity.Author;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "教材编辑权限列表")
public class AuthorSectionVo {

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty("章数")
    private Integer chapterNum;

    @ApiModelProperty("节数")
    private Integer sectionNum;

    @ApiModelProperty("可编辑作者列表")
    private List<Author> authors;
}
