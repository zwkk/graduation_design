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
 * 通知学生关联表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("notice_student")
@ApiModel(value = "NoticeStudent对象", description = "通知学生关联表")
public class NoticeStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("通知id")
    @TableField("notice_id")
    private Integer noticeId;

    @ApiModelProperty("学生id")
    @TableField("student_id")
    private Integer studentId;

    @ApiModelProperty("是否收到")
    @TableField("receive")
    private Integer receive;


}
