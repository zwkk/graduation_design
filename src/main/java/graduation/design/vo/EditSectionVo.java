package graduation.design.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "作者可编辑节列表")
public class EditSectionVo {

    @ApiModelProperty("节id")
    private Integer sectionId;

    @ApiModelProperty("节标题")
    private String sectionTitle;

    @ApiModelProperty("章id")
    private Integer chapterId;

    @ApiModelProperty("节标题")
    private String chapterTitle;

    @ApiModelProperty("编辑状态")
    private String status;

    @ApiModelProperty("上次编辑时间")
    private LocalDateTime time;

    @ApiModelProperty("当前版本")
    private String version;
}
