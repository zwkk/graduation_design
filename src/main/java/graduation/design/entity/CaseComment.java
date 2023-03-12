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
 * 案例评论表
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_comment")
@ApiModel(value = "CaseComment对象", description = "案例评论表")
public class CaseComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("案例id")
    @TableField("case_id")
    private Integer caseId;

    @ApiModelProperty("学生id")
    @TableField("student_id")
    private Integer studentId;

    @ApiModelProperty("星级")
    @TableField("star")
    private Integer star;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("评论内容")
    @TableField("content")
    private String content;


}
