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
 * 助教表
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("assistant")
@ApiModel(value = "Assistant对象", description = "助教权限表")
public class Assistant implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("助教id")
    @TableField("assistant_id")
    private Integer assistantId;

    @ApiModelProperty("通知权限")
    @TableField("notice")
    private Integer notice;

    @ApiModelProperty("布置作业权限")
    @TableField("homework")
    private Integer homework;

    @ApiModelProperty("批改作业权限")
    @TableField("correct")
    private Integer correct;

    @ApiModelProperty("回答提问权限")
    @TableField("answer")
    private Integer answer;


}
