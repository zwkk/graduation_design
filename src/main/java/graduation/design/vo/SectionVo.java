package graduation.design.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "节详细内容")
public class SectionVo {

    @ApiModelProperty("节数")
    @TableField("num")
    private Integer num;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("版本号")
    @TableField("version")
    private String version;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

}
