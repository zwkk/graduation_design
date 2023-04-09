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
import java.time.LocalDateTime;
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

    @Autowired
    StudentAnswerService studentAnswerService;

    @Autowired
    HomeworkStudentService homeworkStudentService;

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
        homeworkService.saveOrUpdate(homework);
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
        homeworkStudentService.remove(new QueryWrapper<HomeworkStudent>().eq("homework_id",id));
        studentAnswerService.remove(new QueryWrapper<StudentAnswer>().eq("homework_id",id));
        return Result.success("删除成功");
    }

    @Authority({"admin","teacher","assistant","student"})
    @ApiOperation(value = "查询我的班级作业,对于学生,还会返回作答记录以及批改情况,接口权限admin,teacher,assistant,student",response = HomeworkVo3.class)
    @GetMapping("/get")
    public Result get(Integer userId){
        User user = userService.getById(userId);
        List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
        List<HomeworkVo2> homeworks = new ArrayList<>();
        List<HomeworkVo3> studentHomeworks = new ArrayList<>();
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
                HomeworkVo3 homeworkVo3 = new HomeworkVo3();
                homeworkVo3.setId(homework.getId());
                homeworkVo3.setName(homework.getName());
                homeworkVo3.setBegin(homework.getBegin());
                homeworkVo3.setEnd(homework.getEnd());
                if(LocalDateTime.now().isAfter(homework.getEnd())){
                    homeworkVo3.setIfOverdue(1);
                }else {
                    homeworkVo3.setIfOverdue(0);
                }
                homeworkVo3.setTotalNum(0);
                homeworkVo3.setTotalScore(0.0);
                HomeworkStudent homeworkStudent = homeworkStudentService.getOne(new QueryWrapper<HomeworkStudent>().eq("homework_id", homework.getId()).eq("student_id", userId));
                if(homeworkStudent==null){
                    homeworkVo3.setIfAnswer(0);
                    homeworkVo3.setIfCorrect(0);
                    homeworkVo3.setScore(null);
                    homeworkVo3.setTime(null);
                }else {
                    homeworkVo3.setIfAnswer(homeworkStudent.getAnswer());
                    homeworkVo3.setIfCorrect(homeworkStudent.getCorrect());
                    homeworkVo3.setTime(homeworkStudent.getTime());
                    homeworkVo3.setScore(homeworkStudent.getScore());
                }
                List<HomeworkProblem> homeworkProblems = homeworkProblemService.list(new QueryWrapper<HomeworkProblem>().eq("homework_id", homework.getId()));
                List<ProblemVo3> problems = new ArrayList<>();
                for (HomeworkProblem homeworkProblem : homeworkProblems) {
                    ProblemVo3 problemVo3 = new ProblemVo3();
                    problemVo3.setId(homeworkProblem.getProblemId());
                    problemVo3.setScore(homeworkProblem.getScore());
                    homeworkVo3.setTotalScore(homeworkVo3.getTotalScore()+Double.parseDouble(homeworkProblem.getScore()));
                    homeworkVo3.setTotalNum(homeworkVo3.getTotalNum()+1);
                    Problem problem = problemService.getById(homeworkProblem.getProblemId());
                    problemVo3.setContent(problem.getContent());
                    problemVo3.setType(problem.getType());
                    Object[] array1 = Arrays.asList(problem.getOptions().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] options = new String[array1.length];
                    for (int i = 0; i < array1.length; i++) {
                        options[i]= (String) array1[i];
                    }
                    problemVo3.setOptions(options);
                    Object[] array2 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    problemVo3.setNum(array2.length);
                    StudentAnswer studentAnswer = studentAnswerService.getOne(new QueryWrapper<StudentAnswer>().eq("homework_id", homework.getId()).eq("problem_id", homeworkProblem.getProblemId()).eq("student_id", userId));
                    if(studentAnswer==null){
                        problemVo3.setAnswers(null);
                        problemVo3.setStudentScore(null);
                    }else {
                        Object[] array3 = Arrays.asList(studentAnswer.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                        String[] answers = new String[array3.length];
                        for (int i = 0; i < array3.length; i++) {
                            answers[i]= (String) array3[i];
                        }
                        problemVo3.setAnswers(answers);
                        problemVo3.setStudentScore(studentAnswer.getScore());
                    }
                    problems.add(problemVo3);
                }
                homeworkVo3.setProblems(problems);
                studentHomeworks.add(homeworkVo3);
            }
            return Result.success(studentHomeworks);
        }
        return Result.success(homeworks);
    }

    @Authority("student")
    @ApiOperation("保存作答记录但不提交,接口权限student")
    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        studentAnswerService.remove(new QueryWrapper<StudentAnswer>().eq("student_id",record.getStudentId()).eq("homework_id",record.getHomeworkId()));
        AnswerVo[] answers = record.getAnswers();
        for (AnswerVo answer : answers) {
            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setStudentId(record.getStudentId()).setHomeworkId(record.getHomeworkId()).setProblemId(answer.getProblemId()).setAnswer(Arrays.toString(answer.getAnswer())).setCorrect(0);
            studentAnswerService.save(studentAnswer);
        }
        return Result.success("保存成功");
    }

    @Authority("student")
    @ApiOperation("提交作业,接口权限student")
    @PostMapping("/submit")
    public Result submit(@RequestBody Record record){
        Homework homework = homeworkService.getById(record.getHomeworkId());
        if(LocalDateTime.now().isBefore(homework.getBegin())){
            return Result.fail("作业未到开始日期，无法提交");
        }
        if(LocalDateTime.now().isAfter(homework.getEnd())){
            return Result.fail("作业已过截止日期，无法提交");
        }
        studentAnswerService.remove(new QueryWrapper<StudentAnswer>().eq("student_id",record.getStudentId()).eq("homework_id",record.getHomeworkId()));
        AnswerVo[] answers = record.getAnswers();
        for (AnswerVo answer : answers) {
            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setStudentId(record.getStudentId()).setHomeworkId(record.getHomeworkId()).setProblemId(answer.getProblemId()).setAnswer(Arrays.toString(answer.getAnswer()));
            Problem problem = problemService.getById(answer.getProblemId());
            if(problem.getType().equals("选择")||problem.getType().equals("填空")||problem.getType().equals("判断")){
                Object[] array1 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                String[] answer1 = new String[array1.length];
                for (int i = 0; i < array1.length; i++) {
                    answer1[i]= (String) array1[i];
                }
                String[] answer2 = answer.getAnswer();
                int correctNum = 0;
                for (int i = 0; i < answer2.length; i++) {
                    if(answer2[i]!=null && answer2[i].equals(answer1[i])){
                        correctNum++;
                    }
                }
                HomeworkProblem homeworkProblem = homeworkProblemService.getOne(new QueryWrapper<HomeworkProblem>().eq("homework_id", record.getHomeworkId()).eq("problem_id", problem.getId()));
                if(correctNum==answer1.length){
                    studentAnswer.setCorrect(1).setScore(homeworkProblem.getScore());
                }else {
                    double s1 = Double.parseDouble(homeworkProblem.getScore());
                    studentAnswer.setCorrect(1).setScore(String.format("%.1f",s1*correctNum/answer1.length));
                }
            }else {
                studentAnswer.setCorrect(0).setScore(null);
            }
            studentAnswerService.save(studentAnswer);
        }
        HomeworkStudent homeworkStudent = new HomeworkStudent();
        homeworkStudent.setHomeworkId(record.getHomeworkId()).setStudentId(record.getStudentId()).setAnswer(1).setTime(LocalDateTime.now());
        List<StudentAnswer> list = studentAnswerService.list(new QueryWrapper<StudentAnswer>().eq("student_id", record.getStudentId()).eq("homework_id", homework.getId()).eq("correct", 1));
        if(list.size()==homeworkProblemService.list(new QueryWrapper<HomeworkProblem>().eq("homework_id",record.getHomeworkId())).size()){
            homeworkStudent.setCorrect(1);
            Double sum = 0.0;
            for (StudentAnswer studentAnswer : list) {
                sum+=Double.parseDouble(studentAnswer.getScore());
            }
            homeworkStudent.setScore(String.format("%.1f",sum));
        }else {
            homeworkStudent.setCorrect(0).setScore(null);
        }
        homeworkStudentService.save(homeworkStudent);
        return Result.success("提交成功");
    }

    @Authority({"teacher","assistant"})
    @ApiOperation(value = "教师助教查看本班级待批改题目列表,接口权限teacher,assistant",response = ProblemVo4.class)
    @GetMapping("/list")
    public Result list(Integer userId){
        User user = userService.getById(userId);
        List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
        List<ProblemVo4> problems = new ArrayList<>();
        if(list.contains("teacher")){
            List<TeacherClass> teacherClasses = teacherClassService.list(new QueryWrapper<TeacherClass>().eq("teacher_id", userId));
            if(teacherClasses.size()==0){
                return Result.success(problems);
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
                List<StudentAnswer> studentAnswers = studentAnswerService.list(new QueryWrapper<StudentAnswer>().eq("homework_id", homework.getId()).eq("correct", 0));
                for (StudentAnswer studentAnswer : studentAnswers) {
                    ProblemVo4 problemVo4 = new ProblemVo4();
                    problemVo4.setHomeworkId(studentAnswer.getHomeworkId());
                    problemVo4.setProblemId(studentAnswer.getProblemId());
                    problemVo4.setStudentId(studentAnswer.getStudentId());
                    Problem problem = problemService.getById(studentAnswer.getProblemId());
                    problemVo4.setContent(problem.getContent());
                    problemVo4.setType(problem.getType());
                    Object[] array1 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] answers = new String[array1.length];
                    for (int i = 0; i < array1.length; i++) {
                        answers[i]= (String) array1[i];
                    }
                    problemVo4.setAnswers(answers);
                    HomeworkProblem homeworkProblem = homeworkProblemService.getOne(new QueryWrapper<HomeworkProblem>().eq("homework_id", studentAnswer.getHomeworkId()).eq("problem_id", studentAnswer.getProblemId()));
                    problemVo4.setScore(homeworkProblem.getScore());
                    Object[] array2 = Arrays.asList(studentAnswer.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] studentAnswers2 = new String[array2.length];
                    for (int i = 0; i < array2.length; i++) {
                        studentAnswers2[i]= (String) array2[i];
                    }
                    problemVo4.setStudentAnswers(studentAnswers2);
                    problems.add(problemVo4);
                }
            }
        }else if(list.contains("assistant")){
            List<AssistantClass> assistantClasses = assistantClassService.list(new QueryWrapper<AssistantClass>().eq("assistant_id", userId));
            if(assistantClasses.size()==0){
                return Result.success(problems);
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
                List<StudentAnswer> studentAnswers = studentAnswerService.list(new QueryWrapper<StudentAnswer>().eq("homework_id", homework.getId()).eq("correct", 0));
                for (StudentAnswer studentAnswer : studentAnswers) {
                    ProblemVo4 problemVo4 = new ProblemVo4();
                    problemVo4.setHomeworkId(studentAnswer.getHomeworkId());
                    problemVo4.setProblemId(studentAnswer.getProblemId());
                    problemVo4.setStudentId(studentAnswer.getStudentId());
                    Problem problem = problemService.getById(studentAnswer.getProblemId());
                    problemVo4.setContent(problem.getContent());
                    problemVo4.setType(problem.getType());
                    Object[] array1 = Arrays.asList(problem.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] answers = new String[array1.length];
                    for (int i = 0; i < array1.length; i++) {
                        answers[i]= (String) array1[i];
                    }
                    problemVo4.setAnswers(answers);
                    HomeworkProblem homeworkProblem = homeworkProblemService.getOne(new QueryWrapper<HomeworkProblem>().eq("homework_id", studentAnswer.getHomeworkId()).eq("problem_id", studentAnswer.getProblemId()));
                    problemVo4.setScore(homeworkProblem.getScore());
                    Object[] array2 = Arrays.asList(studentAnswer.getAnswer().replaceAll("[\\[\\]]", "").split(", ")).toArray();
                    String[] studentAnswers2 = new String[array2.length];
                    for (int i = 0; i < array2.length; i++) {
                        studentAnswers2[i]= (String) array2[i];
                    }
                    problemVo4.setStudentAnswers(studentAnswers2);
                    problems.add(problemVo4);
                }
            }
        }
        return Result.success(problems);
    }

    @Authority({"teacher","assistant"})
    @ApiOperation("批改作业,接口权限teacher,assistant")
    @PostMapping("/correct")
    public Result correct(@RequestBody CorrectVo correctVo){
        StudentAnswer studentAnswer = studentAnswerService.getOne(new QueryWrapper<StudentAnswer>().eq("student_id", correctVo.getStudentId()).eq("homework_id", correctVo.getHomeworkId()).eq("problem_id", correctVo.getProblemId()));
        studentAnswer.setCorrect(1);
        studentAnswer.setScore(Double.toString(correctVo.getScore()));
        studentAnswerService.saveOrUpdate(studentAnswer);
        HomeworkStudent homeworkStudent = homeworkStudentService.getOne(new QueryWrapper<HomeworkStudent>().eq("student_id", correctVo.getStudentId()).eq("homework_id", correctVo.getHomeworkId()));
        List<StudentAnswer> list = studentAnswerService.list(new QueryWrapper<StudentAnswer>().eq("student_id", correctVo.getStudentId()).eq("homework_id", correctVo.getHomeworkId()).eq("correct", 1));
        if(list.size()==homeworkProblemService.list(new QueryWrapper<HomeworkProblem>().eq("homework_id",correctVo.getHomeworkId())).size()){
            homeworkStudent.setCorrect(1);
            Double sum = 0.0;
            for (StudentAnswer studentAnswer1 : list) {
                sum+=Double.parseDouble(studentAnswer1.getScore());
            }
            homeworkStudent.setScore(String.format("%.1f",sum));
        }else {
            homeworkStudent.setCorrect(0).setScore(null);
        }
        homeworkStudentService.saveOrUpdate(homeworkStudent);
        return Result.success("批改成功");
    }

}
