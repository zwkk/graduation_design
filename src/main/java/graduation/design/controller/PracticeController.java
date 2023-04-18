package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.Problem;
import graduation.design.entity.ProblemSection;
import graduation.design.entity.StudentPracticeAnswer;
import graduation.design.service.ProblemSectionService;
import graduation.design.service.ProblemService;
import graduation.design.service.StudentPracticeAnswerService;
import graduation.design.vo.PracticeVo;
import graduation.design.vo.Result;
import graduation.design.vo.SubmitPracticeVo;
import graduation.design.vo.SubmitPracticeVoList;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 章节练习题目表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/practice")
public class PracticeController {

    @Autowired
    StudentPracticeAnswerService studentPracticeAnswerService;

    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemSectionService problemSectionService;

    @ApiOperation(value = "根据节id获取该节对应练习题,接口权限all",response = PracticeVo.class)
    @GetMapping("/get")
    public Result get(Integer sectionId, Integer userId){
        List<ProblemSection> problemSections = problemSectionService.list(new QueryWrapper<ProblemSection>().eq("section_id", sectionId));
        List<PracticeVo> practices = new ArrayList<>();
        for (ProblemSection problemSection : problemSections) {
            if(problemService.getById(problemSection.getProblemId()).getUses()==null || problemService.getById(problemSection.getProblemId()).getUses().equals("作业")){
                continue;
            }
            PracticeVo practiceVo = new PracticeVo();
            practiceVo.setProblemId(problemSection.getProblemId());
            Problem problem = problemService.getById(problemSection.getProblemId());
            if(!(problem.getType().equals("单选") || problem.getType().equals("多选") || problem.getType().equals("填空") || problem.getType().equals("判断"))){
                continue;
            }
            practiceVo.setContent(problem.getContent());
            practiceVo.setType(problem.getType());
            practiceVo.setDifficulty(problem.getDifficulty());
            Object[] array1 = Arrays.asList(problem.getOptions().replaceAll("[\\[\\]]", "").split(", ")).toArray();
            String[] options = new String[array1.length];
            for (int i = 0; i < array1.length; i++) {
                options[i]= (String) array1[i];
            }
            practiceVo.setOptions(options);
            Object[] array2 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
            practiceVo.setNum(array2.length);
            StudentPracticeAnswer studentPracticeAnswer = studentPracticeAnswerService.getOne(new QueryWrapper<StudentPracticeAnswer>().eq("section_id", sectionId).eq("student_id", userId).eq("problem_id", problemSection.getProblemId()));
            if(studentPracticeAnswer!=null){
                practiceVo.setCorrect(studentPracticeAnswer.getIfCorrect());
                String[] answer = new String[array2.length];
                for (int i = 0; i < array2.length; i++) {
                    answer[i]= (String) array2[i];
                }
                practiceVo.setAnswer(answer);
                Object[] array3 = Arrays.asList(studentPracticeAnswer.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                String[] studentAnswer = new String[array3.length];
                for (int i = 0; i < array3.length; i++) {
                    studentAnswer[i]= (String) array3[i];
                }
                practiceVo.setStudentAnswer(studentAnswer);
            }
            practices.add(practiceVo);
        }
        return Result.success(practices);
    }

    @ApiOperation("提交练习题,接口权限all")
    @PostMapping("/submit")
    public Result submit(@RequestBody SubmitPracticeVoList list){
        List<SubmitPracticeVo> submitPracticeVos = list.getPractices();
        for (SubmitPracticeVo submitPracticeVo : submitPracticeVos) {
            StudentPracticeAnswer studentPracticeAnswer = studentPracticeAnswerService.getOne(new QueryWrapper<StudentPracticeAnswer>().eq("section_id", submitPracticeVo.getSectionId()).eq("problem_id", submitPracticeVo.getProblemId()).eq("student_id", submitPracticeVo.getStudentId()));
            if(studentPracticeAnswer==null){
                studentPracticeAnswer = new StudentPracticeAnswer();
                studentPracticeAnswer.setSectionId(submitPracticeVo.getSectionId()).setProblemId(submitPracticeVo.getProblemId()).setStudentId(submitPracticeVo.getStudentId());
            }
            studentPracticeAnswer.setAnswer(Arrays.toString(submitPracticeVo.getStudentAnswer()));
            Problem problem = problemService.getById(submitPracticeVo.getProblemId());
            Object[] array = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
            String[] answer = new String[array.length];
            for (int i = 0; i < array.length; i++) {
                answer[i]= (String) array[i];
            }
            String[] studentAnswer = submitPracticeVo.getStudentAnswer();
            Arrays.sort(answer);
            Arrays.sort(studentAnswer);
            if(studentAnswer.length!=answer.length){
                studentPracticeAnswer.setIfCorrect(0);
            }else {
                studentPracticeAnswer.setIfCorrect(1);
                for (int i = 0; i < answer.length; i++) {
                    if(!answer[i].equals(studentAnswer[i])){
                        studentPracticeAnswer.setIfCorrect(0);
                    }
                }
            }
            studentPracticeAnswerService.saveOrUpdate(studentPracticeAnswer);
        }
        return Result.success("提交成功");
    }

}
