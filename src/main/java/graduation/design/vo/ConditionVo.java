package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "题目列表查询条件")
public class ConditionVo {

    @ApiModelProperty("题目类型,(选择/判断/填空/简答/其它)")
    private String[] type;

    @ApiModelProperty("难度,(高/中/低)")
    private String[] difficulty;

    @ApiModelProperty("相关节id")
    private Integer[] sectionIds;

    @ApiModelProperty("练习/作业")
    private String[] uses;

}
