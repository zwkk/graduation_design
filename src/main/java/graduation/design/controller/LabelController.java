package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.Label;
import graduation.design.entity.ToolLabel;
import graduation.design.service.LabelService;
import graduation.design.service.ToolLabelService;
import graduation.design.vo.Result;
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

    @Autowired
    ToolLabelService toolLabelService;

    @ApiOperation(value = "获取标签列表,接口权限all",response = Label.class)
    @GetMapping("/list")
    public Result list(){
        List<Label> labels = labelService.list();
        return Result.success(labels);
    }

    @Authority("admin")
    @ApiOperation(value = "增加标签,接口权限admin")
    @PostMapping("/add")
    public Result add(@RequestBody Label label){
        label.setId(null);
        if(labelService.getOne(new QueryWrapper<Label>().eq("name",label.getName()))!=null) return Result.fail("该标签已存在");
        labelService.save(label);
        return Result.success("添加成功");
    }

    @Authority("admin")
    @ApiOperation(value = "修改标签,接口权限admin")
    @PostMapping("/modify")
    public Result modify(@RequestBody Label label){
        labelService.saveOrUpdate(label);
        return Result.success("修改成功");
    }

    @Authority("admin")
    @ApiOperation(value = "删除标签,接口权限admin")
    @GetMapping("/delete")
    public Result delete(Integer labelId){
        labelService.removeById(labelId);
        toolLabelService.remove(new QueryWrapper<ToolLabel>().eq("label_id",labelId));
        return Result.success("删除成功");
    }

}
