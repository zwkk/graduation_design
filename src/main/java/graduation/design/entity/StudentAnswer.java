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
 * 学生答题表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("student_answer")
@ApiModel(value = "StudentAnswer对象", description = "学生答题表")
public class StudentAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生id")
    @TableField("student_id")
    private Integer studentId;

    @ApiModelProperty("作业id")
    @TableField("homework_id")
    private Integer homeworkId;

    @ApiModelProperty("题目id")
    @TableField("problem_id")
    private Integer problemId;

    @ApiModelProperty("学生答案")
    @TableField("answer")
    private String answer;

    @ApiModelProperty("是否批改")
    @TableField("correct")
    private Integer correct;

    @ApiModelProperty("该题得分")
    @TableField("score")
    private String score;


}
