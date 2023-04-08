package graduation.design.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "章节练习作答列表")
public class SubmitPracticeVoList {

    List<SubmitPracticeVo> practices;
}
