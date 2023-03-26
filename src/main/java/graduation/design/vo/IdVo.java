package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "id数组")
public class IdVo {

    @ApiModelProperty("id数组")
    private Integer[] ids;

}
