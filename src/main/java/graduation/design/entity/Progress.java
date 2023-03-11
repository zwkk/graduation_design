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
 * 教材阅读进度表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("progress")
@ApiModel(value = "Progress对象", description = "教材阅读进度表")
public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("节id")
    @TableField("section_id")
    private Integer sectionId;

    @ApiModelProperty("学生id")
    @TableField("student_id")
    private Integer studentId;

    @ApiModelProperty("是否阅读")
    @TableField("read")
    private Integer read;


}
