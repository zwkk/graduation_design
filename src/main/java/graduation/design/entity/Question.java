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
 * 提问表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("question")
@ApiModel(value = "Question对象", description = "提问表")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生id")
    @TableField("student_id")
    private Integer studentId;

    @ApiModelProperty("问题题目")
    @TableField("title")
    private String title;

    @ApiModelProperty("问题内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("提问时间")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty("是否答复")
    @TableField("answer")
    private Integer answer;

    @ApiModelProperty("答复内容")
    @TableField("solution")
    private String solution;


}
