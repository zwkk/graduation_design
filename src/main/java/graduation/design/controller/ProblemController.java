package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.Problem;
import graduation.design.entity.ProblemSection;
import graduation.design.service.HomeworkProblemService;
import graduation.design.service.ProblemSectionService;
import graduation.design.service.ProblemService;
import graduation.design.vo.ProblemVo;
import graduation.design.vo.ConditionVo;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 题目表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemSectionService problemSectionService;

    @Autowired
    HomeworkProblemService homeworkProblemService;

    @Authority({"admin","teacher","author"})
    @ApiOperation("上传题目到题库,接口权限admin,author,teacher")
    @PostMapping("/add")
    public Result add(@RequestBody ProblemVo problemVo){
        Problem problem = new Problem();
        problem.setContent(problemVo.getContent()).setAnswer(Arrays.toString(problemVo.getAnswer())).setType(problemVo.getType()).setDifficulty(problemVo.getDifficulty()).setDel(0).setOptions(Arrays.toString(problemVo.getOptions()));
        Problem problem1 = problemService.getOne(new QueryWrapper<Problem>().eq("content", problem.getContent()).eq("del",0));
        if(problem1!=null) return Result.fail("该题目已存在");
        problemService.save(problem);
        Problem problem2 = problemService.getOne(new QueryWrapper<Problem>().eq("content", problem.getContent()).eq("del",0));
        Integer[] sectionIds = problemVo.getSectionIds();
        for (Integer sectionId : sectionIds) {
            ProblemSection problemSection = new ProblemSection();
            problemSection.setProblemId(problem2.getId()).setSectionId(sectionId);
            problemSectionService.save(problemSection);
        }
        return Result.success("添加成功");
    }

    @Authority({"admin","teacher","author"})
    @ApiOperation("修改题目,接口权限admin,author,teacher")
    @PostMapping("/modify")
    public Result modify(@RequestBody ProblemVo problemVo){
        Problem problem = problemService.getById(problemVo.getId());
        problem.setContent(problemVo.getContent()).setAnswer(Arrays.toString(problemVo.getAnswer())).setType(problemVo.getType()).setDifficulty(problemVo.getDifficulty()).setDel(0).setOptions(Arrays.toString(problemVo.getOptions()));
        problemService.saveOrUpdate(problem);
        problemSectionService.remove(new QueryWrapper<ProblemSection>().eq("problem_id",problemVo.getId()));
        Integer[] sectionIds = problemVo.getSectionIds();
        for (Integer sectionId : sectionIds) {
            ProblemSection problemSection = new ProblemSection();
            problemSection.setProblemId(problemVo.getId()).setSectionId(sectionId);
            problemSectionService.save(problemSection);
        }
        return Result.success("修改成功");
    }

    @Authority({"admin","teacher","author"})
    @ApiOperation("删除题目(软删除),接口权限admin,author,teacher")
    @GetMapping("/delete")
    public Result delete(Integer problemId){
        Problem problem = problemService.getById(problemId);
        problem.setDel(1);
        problemService.saveOrUpdate(problem);
        problemSectionService.remove(new QueryWrapper<ProblemSection>().eq("problem_id",problemId));
        return Result.success("删除成功");
    }

    @Authority({"admin","teacher","author"})
    @ApiOperation(value = "根据条件获取题库列表,不带条件则获取全部,接口权限admin,author,teacher",response = ProblemVo.class)
    @PostMapping("/list")
    public Result list(@RequestBody ConditionVo conditionVo){
        List<Problem> problems;
        if((conditionVo.getType()==null || conditionVo.getType().length==0)&&(conditionVo.getDifficulty()==null || conditionVo.getDifficulty().length==0)){
            problems = problemService.list(new QueryWrapper<Problem>().eq("del", 0));
        }else if(conditionVo.getType()==null || conditionVo.getType().length==0){
            problems = problemService.list(new QueryWrapper<Problem>().in("difficulty",conditionVo.getDifficulty()).eq("del", 0));
        }else if(conditionVo.getDifficulty()==null || conditionVo.getDifficulty().length==0){
            problems = problemService.list(new QueryWrapper<Problem>().in("type",conditionVo.getType()).eq("del", 0));
        }else{
            problems = problemService.list(new QueryWrapper<Problem>().in("type",conditionVo.getType()).in("difficulty",conditionVo.getDifficulty()).eq("del", 0));
        }
        List<ProblemVo> problemVoList = new ArrayList<>();
        Integer[] sectionIds = conditionVo.getSectionIds();
        if(sectionIds==null || sectionIds.length==0){
            for (Problem problem : problems) {
                ProblemVo problemVo = new ProblemVo();
                problemVo.setContent(problem.getContent());
                problemVo.setId(problem.getId());
                Object[] array = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                String[] answer = new String[array.length];
                for (int i = 0; i < array.length; i++) {
                    answer[i]= (String) array[i];
                }
                problemVo.setAnswer(answer);
                Object[] array1 = Arrays.asList(problem.getOptions().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                String[] options = new String[array1.length];
                for (int i = 0; i < array1.length; i++) {
                    options[i]= (String) array1[i];
                }
                problemVo.setOptions(options);
                problemVo.setNum(answer.length);
                problemVo.setType(problem.getType());
                problemVo.setDifficulty(problem.getDifficulty());
                List<ProblemSection> problemSections = problemSectionService.list(new QueryWrapper<ProblemSection>().eq("problem_id", problem.getId()));
                Integer[] sections = new Integer[problemSections.size()];
                for (int i = 0; i < problemSections.size(); i++) {
                    sections[i]=problemSections.get(i).getSectionId();
                }
                problemVo.setSectionIds(sections);
                problemVoList.add(problemVo);
            }
        }else {
            for (Problem problem : problems) {
                for (Integer sectionId : sectionIds) {
                    if(problemSectionService.getOne(new QueryWrapper<ProblemSection>().eq("problem_id",problem.getId()).eq("section_id",sectionId))!=null){
                        ProblemVo problemVo = new ProblemVo();
                        problemVo.setContent(problem.getContent());
                        problemVo.setId(problem.getId());
                        Object[] array = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                        String[] answer = new String[array.length];
                        for (int i = 0; i < array.length; i++) {
                            answer[i]= (String) array[i];
                        }
                        problemVo.setAnswer(answer);
                        Object[] array1 = Arrays.asList(problem.getOptions().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                        String[] options = new String[array1.length];
                        for (int i = 0; i < array1.length; i++) {
                            options[i]= (String) array1[i];
                        }
                        problemVo.setOptions(options);
                        problemVo.setNum(answer.length);
                        problemVo.setType(problem.getType());
                        problemVo.setDifficulty(problem.getDifficulty());
                        List<ProblemSection> problemSections = problemSectionService.list(new QueryWrapper<ProblemSection>().eq("problem_id", problem.getId()));
                        Integer[] sections = new Integer[problemSections.size()];
                        for (int i = 0; i < problemSections.size(); i++) {
                            sections[i]=problemSections.get(i).getSectionId();
                        }
                        problemVo.setSectionIds(sections);
                        problemVoList.add(problemVo);
                        break;
                    }
                }
            }
        }
        return Result.success(problemVoList);
    }

}
