package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.Label;
import graduation.design.entity.Tool;
import graduation.design.entity.ToolLabel;
import graduation.design.service.LabelService;
import graduation.design.vo.LabelsVo;
import graduation.design.vo.Result;
import graduation.design.vo.ToolVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    LabelService labelService;

    @ApiOperation(value = "获取标签列表,接口权限all")
    @GetMapping("/list")
    public Result list(){
        List<Label> labels = labelService.list();
        return Result.success(labels);
    }

    @Authority("admin")
    @ApiOperation(value = "编辑标签列表,接口权限admin")
    @PostMapping("/edit")
    public Result edit(@RequestBody LabelsVo labelsVo){
        labelService.removeByIds(labelService.list());
        for (Label label : labelsVo.getLabels()) {
            labelService.save(label);
        }
        return Result.success("编辑成功");
    }

}
