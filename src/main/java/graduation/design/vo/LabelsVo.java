package graduation.design.vo;

import graduation.design.entity.Label;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "标签列表")
public class LabelsVo {

    @ApiModelProperty("标签列表")
    private Label[] labels;
}
