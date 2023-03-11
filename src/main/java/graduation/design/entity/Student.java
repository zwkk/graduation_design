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
 * 学生表
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("student")
@ApiModel(value = "Student对象", description = "学生表")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("账号")
    @TableField("account")
    private String account;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("最后登录时间")
    @TableField("last_login")
    private LocalDateTime lastLogin;

    @ApiModelProperty("性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("班级id")
    @TableField("class_id")
    private Integer classId;


}
