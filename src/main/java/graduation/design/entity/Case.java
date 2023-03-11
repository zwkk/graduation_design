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
 * 案例表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case")
@ApiModel(value = "Case对象", description = "案例表")
public class Case implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("学生id")
    @TableField("student_id")
    private Integer studentId;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("评分人数")
    @TableField("num")
    private Integer num;

    @ApiModelProperty("一星人数")
    @TableField("one")
    private Integer one;

    @ApiModelProperty("两星人数")
    @TableField("two")
    private Integer two;

    @ApiModelProperty("三星人数")
    @TableField("three")
    private Integer three;

    @ApiModelProperty("四星人数")
    @TableField("four")
    private Integer four;

    @ApiModelProperty("五星人数")
    @TableField("five")
    private Integer five;


}
