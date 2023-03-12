package graduation.design.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 教材审核表
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("book_examine")
@ApiModel(value = "BookExamine对象", description = "教材审核表")
public class BookExamine implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("送审时间")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty("作者id")
    @TableField("author_id")
    private Integer authorId;

    @ApiModelProperty("节id")
    @TableField("section_id")
    private Integer sectionId;

    @ApiModelProperty("章id")
    @TableField("chapter_id")
    private Integer chapterId;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("版本")
    @TableField("version")
    private String version;

    @ApiModelProperty("状态")
    @TableField("status")
    private String status;


}
