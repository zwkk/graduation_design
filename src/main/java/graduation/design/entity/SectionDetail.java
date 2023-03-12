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
 * 教材内容表
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("section_detail")
@ApiModel(value = "SectionDetail对象", description = "教材内容表")
public class SectionDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("节id")
    @TableField("section_id")
    private Integer sectionId;

    @ApiModelProperty("版本号")
    @TableField("version")
    private String version;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("作者id")
    @TableField("author_id")
    private Integer authorId;


}
