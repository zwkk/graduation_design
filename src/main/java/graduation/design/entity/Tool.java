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
 * 工具表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tool")
@ApiModel(value = "Tool对象", description = "工具表")
public class Tool implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("链接")
    @TableField("link")
    private String link;

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
