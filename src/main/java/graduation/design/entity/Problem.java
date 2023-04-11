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
 * 题目表
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("problem")
@ApiModel(value = "Problem对象", description = "题目表")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("题目描述")
    @TableField("content")
    private String content;

    @ApiModelProperty("答案")
    @TableField("answer")
    private String answer;

    @ApiModelProperty("题目类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("难度")
    @TableField("difficulty")
    private String difficulty;

    @ApiModelProperty("是否删除")
    @TableField("del")
    private Integer del;

    @ApiModelProperty("选择题选项")
    @TableField("options")
    private String options;

    @ApiModelProperty("用途")
    @TableField("uses")
    private String uses;
}
