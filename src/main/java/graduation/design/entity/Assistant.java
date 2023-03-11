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
 * @since 2023年03月09日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("assistant")
@ApiModel(value = "Assistant对象", description = "助教表")
public class Assistant implements Serializable {

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

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

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
