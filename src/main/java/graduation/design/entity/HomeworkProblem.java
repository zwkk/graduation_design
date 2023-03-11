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
 * 作业题目关联表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("homework_problem")
@ApiModel(value = "HomeworkProblem对象", description = "作业题目关联表")
public class HomeworkProblem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("作业id")
    @TableField("homework_id")
    private Integer homeworkId;

    @ApiModelProperty("题目id")
    @TableField("problem_id")
    private Integer problemId;

    @ApiModelProperty("该题分数")
    @TableField("score")
    private String score;


}
