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
 * 学生练习作答情况表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("student_practice_answer")
@ApiModel(value = "StudentPracticeAnswer对象", description = "学生练习作答情况表")
public class StudentPracticeAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("练习id")
    @TableField("practice_id")
    private Integer practiceId;

    @ApiModelProperty("题目id")
    @TableField("problem_id")
    private Integer problemId;

    @ApiModelProperty("学生id")
    @TableField("student_id")
    private Integer studentId;

    @ApiModelProperty("学生答案")
    @TableField("answer")
    private String answer;

    @ApiModelProperty("得分")
    @TableField("score")
    private String score;

    @ApiModelProperty("是否作答")
    @TableField("if_answer")
    private Integer ifAnswer;


}
