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
 * 班级表
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("class")
@ApiModel(value = "Class对象", description = "班级表")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("班级名")
    @TableField("name")
    private String name;

    @ApiModelProperty("班级人数")
    @TableField("num")
    private Integer num;

    @ApiModelProperty("学期")
    @TableField("term")
    private String term;

    @ApiModelProperty("老师1")
    @TableField("teacher1")
    private Integer teacher1;

    @ApiModelProperty("老师2")
    @TableField("teacher2")
    private Integer teacher2;

    @ApiModelProperty("老师3")
    @TableField("teacher3")
    private Integer teacher3;

    @ApiModelProperty("助教1")
    @TableField("assistant1")
    private Integer assistant1;

    @ApiModelProperty("助教2")
    @TableField("assistant2")
    private Integer assistant2;

    @ApiModelProperty("助教3")
    @TableField("assistant3")
    private Integer assistant3;


}
