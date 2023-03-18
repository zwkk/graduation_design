package graduation.design.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "审核列表")
public class BookExamineVo {

    @ApiModelProperty("审核主键")
    @TableField("id")
    private Integer id;

    @ApiModelProperty("送审时间")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty("作者id")
    @TableField("author_id")
    private Integer authorId;

    @ApiModelProperty("作者姓名")
    private String name;

    @ApiModelProperty("章标题")
    private String chapterTitle;

    @ApiModelProperty("节标题")
    private String sectionTitle;

    @ApiModelProperty("节id")
    private Integer sectionId;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("审核状态")
    private String status;

}
