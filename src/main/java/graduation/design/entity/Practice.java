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
 * 章节练习题目表
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("practice")
@ApiModel(value = "Practice对象", description = "章节练习题目表")
public class Practice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("节id")
    @TableField("section_id")
    private Integer sectionId;

    @ApiModelProperty("章id")
    @TableField("chapter_id")
    private Integer chapterId;

    @ApiModelProperty("题目id")
    @TableField("problem_id")
    private Integer problemId;

    @ApiModelProperty("该题分数")
    @TableField("score")
    private String score;

    @ApiModelProperty("学期")
    @TableField("term")
    private String term;


}
