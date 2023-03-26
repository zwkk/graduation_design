package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.Tool;
import graduation.design.entity.ToolComment;
import graduation.design.entity.ToolLabel;
import graduation.design.service.ToolCommentService;
import graduation.design.service.ToolService;
import graduation.design.vo.Result;
import graduation.design.vo.ToolVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 工具评论表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/toolComment")
public class ToolCommentController {

    @Autowired
    ToolCommentService toolCommentService;

    @Autowired
    ToolService toolService;

    @ApiOperation(value = "发布工具评论,接口权限all")
    @PostMapping("/add")
    public Result add(@RequestBody ToolComment toolComment){
        toolComment.setId(null);
        toolCommentService.save(toolComment);
        Tool tool = toolService.getById(toolComment.getToolId());
        tool.setNum(tool.getNum()+1);
        if(toolComment.getStar().equals(1)){
            tool.setOne(tool.getOne()+1);
        }else if(toolComment.getStar().equals(2)){
            tool.setTwo(tool.getTwo()+1);
        }else if(toolComment.getStar().equals(3)){
            tool.setThree(tool.getThree()+1);
        }else if(toolComment.getStar().equals(4)){
            tool.setFour(tool.getFour()+1);
        }else if(toolComment.getStar().equals(5)){
            tool.setFive(tool.getFive()+1);
        }
        toolService.saveOrUpdate(tool);
        return Result.success("评论成功");
    }
}
