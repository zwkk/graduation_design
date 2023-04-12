package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.*;
import graduation.design.service.*;
import graduation.design.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 提问表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    StudentClassService studentClassService;

    @Autowired
    TeacherClassService teacherClassService;

    @Autowired
    AssistantClassService assistantClassService;

    @Authority("student")
    @ApiOperation("提问,接口权限student")
    @PostMapping("/add")
    public Result add(@RequestBody QuestionVo questionVo){
        questionVo.setId(null);
        Question question = new Question();
        question.setStudentId(questionVo.getStudentId()).setTitle(questionVo.getTitle()).setContent(questionVo.getContent()).setTime(LocalDateTime.now()).setAnswer(0);
        questionService.save(question);
        return Result.success("提问成功");
    }

    @Authority("student")
    @ApiOperation("修改提问,接口权限student")
    @PostMapping("/modify")
    public Result modify(@RequestBody QuestionVo questionVo){
        Question question = questionService.getById(questionVo.getId());
        if(question.getAnswer().equals(1)){
            return Result.fail("已经答复的提问无法修改");
        }
        if(!question.getStudentId().equals(questionVo.getStudentId())){
            return Result.fail("无法修改他人的提问");
        }
        question.setStudentId(questionVo.getStudentId()).setTitle(questionVo.getTitle()).setContent(questionVo.getContent()).setTime(LocalDateTime.now()).setAnswer(0);
        questionService.saveOrUpdate(question);
        return Result.success("修改成功");
    }

    @Authority("student")
    @ApiOperation("删除提问,接口权限student")
    @GetMapping("/delete")
    public Result delete(Integer userId,Integer questionId){
        Question question = questionService.getById(questionId);
        if(question.getAnswer().equals(1)){
            return Result.fail("已经答复的提问无法删除");
        }
        if(!question.getStudentId().equals(userId)){
            return Result.fail("无法删除他人的提问");
        }
        questionService.removeById(questionId);
        return Result.success("删除成功");
    }

    @Authority({"student","teacher","assistant"})
    @ApiOperation("查看自己班级的所有提问,接口权限student,teacher,assistant")
    @GetMapping("/list")
    public Result list(Integer userId){
        User user = userService.getById(userId);
        List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
        List<QuestionVo2> questions = new ArrayList<>();
        if(list.contains("student")){
            List<StudentClass> studentClasses = studentClassService.list(new QueryWrapper<StudentClass>().eq("student_id", userId));
            if(studentClasses.size()==0){
                return Result.success(questions);
            }
            Integer[] classIds = new Integer[studentClasses.size()];
            for (int i = 0; i < studentClasses.size(); i++) {
                classIds[i]=studentClasses.get(i).getClassId();
            }
            List<StudentClass> studentClasses1 = studentClassService.list(new QueryWrapper<StudentClass>().in("class_id", classIds));
            Integer[] students = new Integer[studentClasses1.size()];
            for (int i = 0; i < studentClasses1.size(); i++) {
                students[i]=studentClasses1.get(i).getStudentId();
            }
            List<Integer> students2 = new ArrayList<>();
            for (Integer student : students) {
                if(!students2.contains(student)){
                    students2.add(student);
                }
            }
            Integer[] students3 = new Integer[students2.size()];
            for (int i = 0; i < students2.size(); i++) {
                students3[i]=students2.get(i);
            }
            List<Question> questionList = questionService.list(new QueryWrapper<Question>().in("student_id", students3));
            for (Question question : questionList) {
                QuestionVo2 questionVo2 = new QuestionVo2();
                questionVo2.setQuestionId(question.getId());
                questionVo2.setStudentId(question.getStudentId());
                questionVo2.setName(userService.getById(question.getStudentId()).getName());
                questionVo2.setTitle(question.getTitle());
                questionVo2.setContent(question.getContent());
                questionVo2.setTime(question.getTime());
                questionVo2.setAnswer(question.getAnswer());
                questionVo2.setSolution(question.getSolution());
                questions.add(questionVo2);
            }
        }else if(list.contains("teacher")){
            List<TeacherClass> teacherClasses = teacherClassService.list(new QueryWrapper<TeacherClass>().eq("teacher_id", userId));
            if(teacherClasses.size()==0){
                return Result.success(questions);
            }
            Integer[] classIds = new Integer[teacherClasses.size()];
            for (int i = 0; i < teacherClasses.size(); i++) {
                classIds[i]=teacherClasses.get(i).getClassId();
            }
            List<StudentClass> studentClasses1 = studentClassService.list(new QueryWrapper<StudentClass>().in("class_id", classIds));
            Integer[] students = new Integer[studentClasses1.size()];
            for (int i = 0; i < studentClasses1.size(); i++) {
                students[i]=studentClasses1.get(i).getStudentId();
            }
            List<Integer> students2 = new ArrayList<>();
            for (Integer student : students) {
                if(!students2.contains(student)){
                    students2.add(student);
                }
            }
            Integer[] students3 = new Integer[students2.size()];
            for (int i = 0; i < students2.size(); i++) {
                students3[i]=students2.get(i);
            }
            List<Question> questionList = questionService.list(new QueryWrapper<Question>().in("student_id", students3));
            for (Question question : questionList) {
                QuestionVo2 questionVo2 = new QuestionVo2();
                questionVo2.setQuestionId(question.getId());
                questionVo2.setStudentId(question.getStudentId());
                questionVo2.setName(userService.getById(question.getStudentId()).getName());
                questionVo2.setTitle(question.getTitle());
                questionVo2.setContent(question.getContent());
                questionVo2.setTime(question.getTime());
                questionVo2.setAnswer(question.getAnswer());
                questionVo2.setSolution(question.getSolution());
                questions.add(questionVo2);
            }
        }else if(list.contains("assistant")){
            List<AssistantClass> assistantClasses = assistantClassService.list(new QueryWrapper<AssistantClass>().eq("assistant_id", userId));
            if(assistantClasses.size()==0){
                return Result.success(questions);
            }
            Integer[] classIds = new Integer[assistantClasses.size()];
            for (int i = 0; i < assistantClasses.size(); i++) {
                classIds[i]=assistantClasses.get(i).getClassId();
            }
            List<StudentClass> studentClasses1 = studentClassService.list(new QueryWrapper<StudentClass>().in("class_id", classIds));
            Integer[] students = new Integer[studentClasses1.size()];
            for (int i = 0; i < studentClasses1.size(); i++) {
                students[i]=studentClasses1.get(i).getStudentId();
            }
            List<Integer> students2 = new ArrayList<>();
            for (Integer student : students) {
                if(!students2.contains(student)){
                    students2.add(student);
                }
            }
            Integer[] students3 = new Integer[students2.size()];
            for (int i = 0; i < students2.size(); i++) {
                students3[i]=students2.get(i);
            }
            List<Question> questionList = questionService.list(new QueryWrapper<Question>().in("student_id", students3));
            for (Question question : questionList) {
                QuestionVo2 questionVo2 = new QuestionVo2();
                questionVo2.setQuestionId(question.getId());
                questionVo2.setStudentId(question.getStudentId());
                questionVo2.setName(userService.getById(question.getStudentId()).getName());
                questionVo2.setTitle(question.getTitle());
                questionVo2.setContent(question.getContent());
                questionVo2.setTime(question.getTime());
                questionVo2.setAnswer(question.getAnswer());
                questionVo2.setSolution(question.getSolution());
                questions.add(questionVo2);
            }
        }
        return Result.success(questions);
    }

    @Authority({"teacher","assistant"})
    @ApiOperation("回答提问,接口权限teacher,assistant")
    @PostMapping("/answer")
    public Result answer(@RequestBody SolutionVo solutionVo){
        Question question = questionService.getById(solutionVo.getQuestionId());
        question.setAnswer(1).setSolution(solutionVo.getSolution());
        questionService.saveOrUpdate(question);
        return Result.success("回复成功");
    }

}
