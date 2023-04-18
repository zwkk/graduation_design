package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalTime;


@Data
@ApiModel(description = "学习记录")
public class RecordVo {

    @ApiModelProperty("上次看到节id")
    private Integer sectionId;

    @ApiModelProperty("上次看到节名称")
    private String sectionName;

    @ApiModelProperty("学习时长")
    private LocalTime time;

    @ApiModelProperty("已学习节数")
    private Integer readSection;

    @ApiModelProperty("总节数")
    private Integer allSection;
}
