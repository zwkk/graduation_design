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
 * 作业学生关联表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("homework_student")
@ApiModel(value = "HomeworkStudent对象", description = "作业学生关联表")
public class HomeworkStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("作业id")
    @TableField("homework_id")
    private Integer homeworkId;

    @ApiModelProperty("学生id")
    @TableField("student_id")
    private Integer studentId;

    @ApiModelProperty("是否作答")
    @TableField("answer")
    private Integer answer;

    @ApiModelProperty("是否批改")
    @TableField("correct")
    private Integer correct;

    @ApiModelProperty("完成时间")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty("分数")
    @TableField("score")
    private String score;


}
