package graduation.design.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 节表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("section")
@ApiModel(value = "Section对象", description = "节表")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("节数")
    @TableField("num")
    private Integer num;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("版本")
    @TableField("version")
    private String version;

    @ApiModelProperty("所属章")
    @TableField("chapter_num")
    private Integer chapterNum;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;


}
