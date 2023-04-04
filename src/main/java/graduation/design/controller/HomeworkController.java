package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.*;
import graduation.design.entity.Class;
import graduation.design.service.*;
import graduation.design.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 作业表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    HomeworkService homeworkService;

    @Autowired
    HomeworkClassService homeworkClassService;

    @Autowired
    HomeworkProblemService homeworkProblemService;

    @Autowired
    UserService userService;

    @Autowired
    ProblemService problemService;

    @Autowired
    TeacherClassService teacherClassService;

    @Autowired
    AssistantClassService assistantClassService;

    @Autowired
    StudentClassService studentClassService;

    @Authority({"admin","teacher","assistant"})
    @ApiOperation("布置作业,接口权限admin,teacher,assistant")
    @PostMapping("/add")
    public Result add(@RequestBody HomeworkVo homeworkVo){
        homeworkVo.setId(null);
        Homework homework = new Homework();
        if(homeworkService.getOne(new QueryWrapper<Homework>().eq("name",homeworkVo.getName()))!=null){
            return Result.fail("该作业名称已存在");
        }
        homework.setName(homeworkVo.getName()).setBegin(homeworkVo.getBegin()).setEnd(homeworkVo.getEnd());
        homeworkService.save(homework);
        Homework homework1 = homeworkService.getOne(new QueryWrapper<Homework>().eq("name", homeworkVo.getName()));
        ProblemScore[] problemScores = homeworkVo.getProblemScores();
        if(problemScores.length==0) return Result.fail("题目列表不能为空");
        for (ProblemScore problemScore : problemScores) {
            HomeworkProblem homeworkProblem = new HomeworkProblem();
            homeworkProblem.setHomeworkId(homework1.getId()).setProblemId(problemScore.getId()).setScore(String.format("%.1f",problemScore.getScore()));
            homeworkProblemService.save(homeworkProblem);
        }
        Integer[] classes = homeworkVo.getClasses();
        for (Integer classId : classes) {
            HomeworkClass homeworkClass = new HomeworkClass();
            homeworkClass.setHomeworkId(homework1.getId()).setClassId(classId);
            homeworkClassService.save(homeworkClass);
        }
        return Result.success("上传成功");
    }

    @Authority({"admin","teacher","assistant"})
    @ApiOperation("修改作业,接口权限admin,teacher,assistant")
    @PostMapping("/modify")
    public Result modify(@RequestBody HomeworkVo homeworkVo){
        Homework homework = homeworkService.getById(homeworkVo.getId());
        homework.setName(homeworkVo.getName()).setBegin(homeworkVo.getBegin()).setEnd(homeworkVo.getEnd());
        homeworkService.save(homework);
        ProblemScore[] problemScores = homeworkVo.getProblemScores();
        if(problemScores.length==0) return Result.fail("题目列表不能为空");
        homeworkProblemService.remove(new QueryWrapper<HomeworkProblem>().eq("homework_id",homework.getId()));
        for (ProblemScore problemScore : problemScores) {
            HomeworkProblem homeworkProblem = new HomeworkProblem();
            homeworkProblem.setHomeworkId(homework.getId()).setProblemId(problemScore.getId()).setScore(String.format("%.1f",problemScore.getScore()));
            homeworkProblemService.save(homeworkProblem);
        }
        homeworkClassService.remove(new QueryWrapper<HomeworkClass>().eq("homework_id",homework.getId()));
        Integer[] classes = homeworkVo.getClasses();
        for (Integer classId : classes) {
            HomeworkClass homeworkClass = new HomeworkClass();
            homeworkClass.setHomeworkId(homework.getId()).setClassId(classId);
            homeworkClassService.save(homeworkClass);
        }
        return Result.success("修改成功");
    }

    @Authority({"admin","teacher","assistant"})
    @ApiOperation("删除作业,接口权限admin,teacher,assistant")
    @GetMapping("/delete")
    public Result delete(Integer id){
        homeworkService.removeById(id);
        homeworkProblemService.remove(new QueryWrapper<HomeworkProblem>().eq("homework_id",id));
        homeworkClassService.remove(new QueryWrapper<HomeworkClass>().eq("homework_id",id));
        return Result.success("删除成功");
    }

    @Authority({"admin","teacher","assistant","student"})
    @ApiOperation("查询我的班级作业,接口权限admin,teacher,assistant,student")
    @GetMapping("/get")
    public Result get(Integer userId){
        User user = userService.getById(userId);
        List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
        List<HomeworkVo2> homeworks = new ArrayList<>();
        if(list.contains("admin")){
            List<Homework> homeworkList = homeworkService.list();
            for (Homework homework : homeworkList) {
                HomeworkVo2 homeworkVo2 = new HomeworkVo2();
                homeworkVo2.setId(homework.getId());
                homeworkVo2.setName(homework.getName());
                homeworkVo2.setBegin(homework.getBegin());
                homeworkVo2.setEnd(homework.getEnd());
                homeworkVo2.setTotalNum(0);
                homeworkVo2.setTotalScore(0.0);
                List<HomeworkProblem> homeworkProblems = homeworkProblemService.list(new QueryWrapper<HomeworkProblem>().eq("homework_id", homework.getId()));
                List<ProblemVo2> problems = new ArrayList<>();
                for (HomeworkProblem homeworkProblem : homeworkProblems) {
                    ProblemVo2 problemVo2 = new ProblemVo2();
                    problemVo2.setId(homeworkProblem.getProblemId());
                    problemVo2.setScore(homeworkProblem.getScore());
                    homeworkVo2.setTotalScore(homeworkVo2.getTotalScore()+Double.parseDouble(homeworkProblem.getScore()));
                    homeworkVo2.setTotalNum(homeworkVo2.getTotalNum()+1);
                    Problem problem = problemService.getById(homeworkProblem.getProblemId());
                    problemVo2.setContent(problem.getContent());
                    problemVo2.setType(problem.getType());
                    Object[] array1 = Arrays.asList(problem.getOptions().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] options = new String[array1.length];
                    for (int i = 0; i < array1.length; i++) {
                        options[i]= (String) array1[i];
                    }
                    problemVo2.setOptions(options);
                    Object[] array2 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    problemVo2.setNum(array2.length);
                    problems.add(problemVo2);
                }
                homeworkVo2.setProblems(problems);
                homeworks.add(homeworkVo2);
            }
            return Result.success(homeworks);
        }else if(list.contains("teacher")){
            List<TeacherClass> teacherClasses = teacherClassService.list(new QueryWrapper<TeacherClass>().eq("teacher_id", userId));
            if(teacherClasses.size()==0){
                return Result.success(homeworks);
            }
            Integer[] classIds = new Integer[teacherClasses.size()];
            for (int i = 0; i < teacherClasses.size(); i++) {
                classIds[i]=teacherClasses.get(i).getClassId();
            }
            List<HomeworkClass> classes = homeworkClassService.list(new QueryWrapper<HomeworkClass>().in("class_id", classIds));
            Integer[] homeworks2 = new Integer[classes.size()];
            for (int i = 0; i < classes.size(); i++) {
                homeworks2[i]=classes.get(i).getHomeworkId();
            }
            List<Homework> homeworkList2 = homeworkService.list(new QueryWrapper<Homework>().in("id", homeworks2));
            List<Homework> homeworkList = new ArrayList<>();
            for (Homework homework : homeworkList2) {
                if(!homeworkList.contains(homework)){
                    homeworkList.add(homework);
                }
            }
            for (Homework homework : homeworkList) {
                HomeworkVo2 homeworkVo2 = new HomeworkVo2();
                homeworkVo2.setId(homework.getId());
                homeworkVo2.setName(homework.getName());
                homeworkVo2.setBegin(homework.getBegin());
                homeworkVo2.setEnd(homework.getEnd());
                homeworkVo2.setTotalNum(0);
                homeworkVo2.setTotalScore(0.0);
                List<HomeworkProblem> homeworkProblems = homeworkProblemService.list(new QueryWrapper<HomeworkProblem>().eq("homework_id", homework.getId()));
                List<ProblemVo2> problems = new ArrayList<>();
                for (HomeworkProblem homeworkProblem : homeworkProblems) {
                    ProblemVo2 problemVo2 = new ProblemVo2();
                    problemVo2.setId(homeworkProblem.getProblemId());
                    problemVo2.setScore(homeworkProblem.getScore());
                    homeworkVo2.setTotalScore(homeworkVo2.getTotalScore()+Double.parseDouble(homeworkProblem.getScore()));
                    homeworkVo2.setTotalNum(homeworkVo2.getTotalNum()+1);
                    Problem problem = problemService.getById(homeworkProblem.getProblemId());
                    problemVo2.setContent(problem.getContent());
                    problemVo2.setType(problem.getType());
                    Object[] array1 = Arrays.asList(problem.getOptions().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] options = new String[array1.length];
                    for (int i = 0; i < array1.length; i++) {
                        options[i]= (String) array1[i];
                    }
                    problemVo2.setOptions(options);
                    Object[] array2 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    problemVo2.setNum(array2.length);
                    problems.add(problemVo2);
                }
                homeworkVo2.setProblems(problems);
                homeworks.add(homeworkVo2);
            }
            return Result.success(homeworks);
        }else if(list.contains("assistant")){
            List<AssistantClass> assistantClasses = assistantClassService.list(new QueryWrapper<AssistantClass>().eq("assistant_id", userId));
            if(assistantClasses.size()==0){
                return Result.success(homeworks);
            }
            Integer[] classIds = new Integer[assistantClasses.size()];
            for (int i = 0; i < assistantClasses.size(); i++) {
                classIds[i]=assistantClasses.get(i).getClassId();
            }
            List<HomeworkClass> classes = homeworkClassService.list(new QueryWrapper<HomeworkClass>().in("class_id", classIds));
            Integer[] homeworks2 = new Integer[classes.size()];
            for (int i = 0; i < classes.size(); i++) {
                homeworks2[i]=classes.get(i).getHomeworkId();
            }
            List<Homework> homeworkList2 = homeworkService.list(new QueryWrapper<Homework>().in("id", homeworks2));
            List<Homework> homeworkList = new ArrayList<>();
            for (Homework homework : homeworkList2) {
                if(!homeworkList.contains(homework)){
                    homeworkList.add(homework);
                }
            }
            for (Homework homework : homeworkList) {
                HomeworkVo2 homeworkVo2 = new HomeworkVo2();
                homeworkVo2.setId(homework.getId());
                homeworkVo2.setName(homework.getName());
                homeworkVo2.setBegin(homework.getBegin());
                homeworkVo2.setEnd(homework.getEnd());
                homeworkVo2.setTotalNum(0);
                homeworkVo2.setTotalScore(0.0);
                List<HomeworkProblem> homeworkProblems = homeworkProblemService.list(new QueryWrapper<HomeworkProblem>().eq("homework_id", homework.getId()));
                List<ProblemVo2> problems = new ArrayList<>();
                for (HomeworkProblem homeworkProblem : homeworkProblems) {
                    ProblemVo2 problemVo2 = new ProblemVo2();
                    problemVo2.setId(homeworkProblem.getProblemId());
                    problemVo2.setScore(homeworkProblem.getScore());
                    homeworkVo2.setTotalScore(homeworkVo2.getTotalScore()+Double.parseDouble(homeworkProblem.getScore()));
                    homeworkVo2.setTotalNum(homeworkVo2.getTotalNum()+1);
                    Problem problem = problemService.getById(homeworkProblem.getProblemId());
                    problemVo2.setContent(problem.getContent());
                    problemVo2.setType(problem.getType());
                    Object[] array1 = Arrays.asList(problem.getOptions().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] options = new String[array1.length];
                    for (int i = 0; i < array1.length; i++) {
                        options[i]= (String) array1[i];
                    }
                    problemVo2.setOptions(options);
                    Object[] array2 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    problemVo2.setNum(array2.length);
                    problems.add(problemVo2);
                }
                homeworkVo2.setProblems(problems);
                homeworks.add(homeworkVo2);
            }
            return Result.success(homeworks);
        }else if(list.contains("student")){
            List<StudentClass> studentClasses = studentClassService.list(new QueryWrapper<StudentClass>().eq("student_id", userId));
            if(studentClasses.size()==0){
                return Result.success(homeworks);
            }
            Integer[] classIds = new Integer[studentClasses.size()];
            for (int i = 0; i < studentClasses.size(); i++) {
                classIds[i]=studentClasses.get(i).getClassId();
            }
            List<HomeworkClass> classes = homeworkClassService.list(new QueryWrapper<HomeworkClass>().in("class_id", classIds));
            Integer[] homeworks2 = new Integer[classes.size()];
            for (int i = 0; i < classes.size(); i++) {
                homeworks2[i]=classes.get(i).getHomeworkId();
            }
            List<Homework> homeworkList2 = homeworkService.list(new QueryWrapper<Homework>().in("id", homeworks2));
            List<Homework> homeworkList = new ArrayList<>();
            for (Homework homework : homeworkList2) {
                if(!homeworkList.contains(homework)){
                    homeworkList.add(homework);
                }
            }
            for (Homework homework : homeworkList) {
                HomeworkVo2 homeworkVo2 = new HomeworkVo2();
                homeworkVo2.setId(homework.getId());
                homeworkVo2.setName(homework.getName());
                homeworkVo2.setBegin(homework.getBegin());
                homeworkVo2.setEnd(homework.getEnd());
                homeworkVo2.setTotalNum(0);
                homeworkVo2.setTotalScore(0.0);
                List<HomeworkProblem> homeworkProblems = homeworkProblemService.list(new QueryWrapper<HomeworkProblem>().eq("homework_id", homework.getId()));
                List<ProblemVo2> problems = new ArrayList<>();
                for (HomeworkProblem homeworkProblem : homeworkProblems) {
                    ProblemVo2 problemVo2 = new ProblemVo2();
                    problemVo2.setId(homeworkProblem.getProblemId());
                    problemVo2.setScore(homeworkProblem.getScore());
                    homeworkVo2.setTotalScore(homeworkVo2.getTotalScore()+Double.parseDouble(homeworkProblem.getScore()));
                    homeworkVo2.setTotalNum(homeworkVo2.getTotalNum()+1);
                    Problem problem = problemService.getById(homeworkProblem.getProblemId());
                    problemVo2.setContent(problem.getContent());
                    problemVo2.setType(problem.getType());
                    Object[] array1 = Arrays.asList(problem.getOptions().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] options = new String[array1.length];
                    for (int i = 0; i < array1.length; i++) {
                        options[i]= (String) array1[i];
                    }
                    problemVo2.setOptions(options);
                    Object[] array2 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    problemVo2.setNum(array2.length);
                    problems.add(problemVo2);
                }
                homeworkVo2.setProblems(problems);
                homeworks.add(homeworkVo2);
            }
            return Result.success(homeworks);
        }
        return Result.success(homeworks);
    }

}
