package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.Label;
import graduation.design.entity.Tool;
import graduation.design.entity.ToolComment;
import graduation.design.entity.ToolLabel;
import graduation.design.service.*;
import graduation.design.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 工具表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    ToolService toolService;

    @Autowired
    ToolLabelService toolLabelService;

    @Autowired
    ToolCommentService toolCommentService;

    @Autowired
    LabelService labelService;

    @Autowired
    UserService userService;

    @Authority("admin")
    @ApiOperation(value = "上传工具,接口权限admin")
    @PostMapping("/add")
    public Result add(@RequestBody ToolVo toolVo){
        Tool tool1 = toolService.getOne(new QueryWrapper<Tool>().eq("name", toolVo.getName()));
        if(tool1!=null) return Result.fail("该工具已存在");
        Tool tool = new Tool();
        tool.setName(toolVo.getName());
        tool.setDescription(toolVo.getDescription());
        tool.setLink(toolVo.getLink());
        tool.setNum(0).setOne(0).setTwo(0).setThree(0).setFour(0).setFive(0);
        toolService.save(tool);
        Tool tool2 = toolService.getOne(new QueryWrapper<Tool>().eq("name", toolVo.getName()));
        Integer[] labelIds = toolVo.getLabelIds();
        for (Integer labelId : labelIds) {
            ToolLabel toolLabel = new ToolLabel();
            toolLabel.setToolId(tool2.getId()).setLabelId(labelId);
            toolLabelService.save(toolLabel);
        }
        return Result.success("上传工具成功");
    }

    @Authority("admin")
    @ApiOperation(value = "删除工具,接口权限admin")
    @GetMapping("/delete")
    public Result delete(Integer toolId){
        toolCommentService.remove(new QueryWrapper<ToolComment>().eq("tool_id",toolId));
        toolLabelService.remove(new QueryWrapper<ToolLabel>().eq("tool_id",toolId));
        toolService.removeById(toolId);
        return Result.success("删除成功");
    }

    @ApiOperation(value = "查看所有工具列表,接口权限all",response = ToolAbstractVo.class)
    @GetMapping("/get")
    public Result get(){
        List<Tool> toolList = toolService.list();
        List<ToolAbstractVo> toolAbstractVoList = new ArrayList<>();
        for (Tool tool : toolList) {
            ToolAbstractVo toolAbstractVo = new ToolAbstractVo();
            toolAbstractVo.setId(tool.getId());
            toolAbstractVo.setName(tool.getName());
            if(tool.getNum()!=0){
                double sum = tool.getOne()+tool.getTwo()*2+tool.getThree()*3+tool.getFour()*4+tool.getFive()*5;
                double score = sum / tool.getNum();
                String result = String.format("%.1f",score);
                toolAbstractVo.setScore(result);
            }else {
                toolAbstractVo.setScore("暂无评分");
            }
            List<ToolLabel> labelNames = toolLabelService.list(new QueryWrapper<ToolLabel>().eq("tool_id", tool.getId()));
            Label[] labels = new Label[labelNames.size()];
            for (int i = 0; i < labelNames.size(); i++) {
                Integer labelId = labelNames.get(i).getLabelId();
                labels[i] = labelService.getById(labelId);
            }
            toolAbstractVo.setLabels(labels);
            toolAbstractVoList.add(toolAbstractVo);
        }
        return Result.success(toolAbstractVoList);
    }

    @ApiOperation(value = "根据工具id查看工具详情,接口权限all",response = ToolDetailVo.class)
    @GetMapping("/detail")
    public Result detail(Integer id){
        ToolDetailVo toolDetailVo = new ToolDetailVo();
        Tool tool = toolService.getById(id);
        toolDetailVo.setId(tool.getId());
        toolDetailVo.setName(tool.getName());
        toolDetailVo.setDescription(tool.getDescription());
        toolDetailVo.setLink(tool.getLink());
        toolDetailVo.setNum(tool.getNum());
        toolDetailVo.setOne(tool.getOne());
        toolDetailVo.setTwo(tool.getTwo());
        toolDetailVo.setThree(tool.getThree());
        toolDetailVo.setFour(tool.getFour());
        toolDetailVo.setFive(tool.getFive());
        if(tool.getNum()!=0){
            double sum = tool.getOne()+tool.getTwo()*2+tool.getThree()*3+tool.getFour()*4+tool.getFive()*5;
            double score = sum / tool.getNum();
            String result = String.format("%.1f",score);
            toolDetailVo.setScore(result);
        }else {
            toolDetailVo.setScore("暂无评分");
        }
        ToolComment comment = toolCommentService.getOne(new QueryWrapper<ToolComment>().eq("tool_id", id).orderByDesc("id").last("limit 1"));
        CommentVo commentVo = new CommentVo();
        commentVo.setId(comment.getId());
        commentVo.setName(userService.getById(comment.getStudentId()).getName());
        commentVo.setTitle(comment.getTitle());
        commentVo.setContent(comment.getContent());
        commentVo.setStar(comment.getStar());
        toolDetailVo.setComment(commentVo);
        List<ToolLabel> toolLabels = toolLabelService.list(new QueryWrapper<ToolLabel>().eq("tool_id", id));
        if(toolLabels.size()==0) {
            toolDetailVo.setList(null);
            return Result.success(toolDetailVo);
        }
        List<ToolAbstractVo> toolAbstractVoList = new ArrayList<>();
        List<ToolAbstractVo> toolList = (List<ToolAbstractVo>) get().getData();
        for (ToolLabel toolLabel : toolLabels) {
            Integer labelId = toolLabel.getLabelId();
            for (ToolAbstractVo abstractVo : toolList) {
                if(abstractVo.getId().equals(id)) continue;
                for (Label label : abstractVo.getLabels()) {
                    if(labelId.equals(label.getId())) {
                        if(!toolAbstractVoList.contains(abstractVo)) {
                            toolAbstractVoList.add(abstractVo);
                        }
                    }
                }
            }
        }
        toolDetailVo.setList(toolAbstractVoList);
        return Result.success(toolDetailVo);
    }

    @ApiOperation(value = "根据工具id查看所有评论,接口权限all",response = CommentVo.class)
    @GetMapping("/comments")
    public Result comments(Integer id){
        List<CommentVo> list = new ArrayList<>();
        List<ToolComment> comments = toolCommentService.list(new QueryWrapper<ToolComment>().eq("tool_id", id));
        for (ToolComment comment : comments) {
            CommentVo commentVo = new CommentVo();
            commentVo.setId(comment.getId());
            commentVo.setName(userService.getById(comment.getStudentId()).getName());
            commentVo.setTitle(comment.getTitle());
            commentVo.setContent(comment.getContent());
            commentVo.setStar(comment.getStar());
            list.add(commentVo);
        }
        return Result.success(list);
    }

    @ApiOperation(value = "根据标签id查找相关工具列表,不带标签id则查询所有工具,接口权限all",response = ToolAbstractVo.class)
    @PostMapping("/find")
    public Result detail(@RequestBody IdVo idVo){
        if(idVo.getIds().length==0) return get();
        Integer[] ids = idVo.getIds();
        List<ToolAbstractVo> toolAbstractVoList = new ArrayList<>();
        List<ToolAbstractVo> toolList = (List<ToolAbstractVo>) get().getData();
        for (Integer id : ids) {
            for (ToolAbstractVo abstractVo : toolList) {
                for (Label label : abstractVo.getLabels()) {
                    if(id.equals(label.getId())) {
                        if(!toolAbstractVoList.contains(abstractVo)) {
                            toolAbstractVoList.add(abstractVo);
                        }
                    }
                }
            }
        }
        return Result.success(toolAbstractVoList);
    }

}
