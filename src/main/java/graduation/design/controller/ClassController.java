package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.*;
import graduation.design.entity.Class;
import graduation.design.service.*;
import graduation.design.util.JWTUtils;
import graduation.design.vo.ClassMembers;
import graduation.design.vo.ClassVo;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    ClassService classService;

    @Autowired
    AssistantClassService assistantClassService;

    @Autowired
    TeacherClassService teacherClassService;

    @Autowired
    StudentClassService studentClassService;

    @Autowired
    HomeworkClassService homeworkClassService;

    @Autowired
    UserService userService;

    @Authority({"admin","teacher"})
    @ApiOperation("新增班级,接口权限admin,teacher")
    @PostMapping("/add")
    public Result add(@RequestHeader("Authorization") String token,@RequestBody Class c){
        c.setId(null);
        c.setNum(0);
        if(c.getName()==null || c.getTerm()==null){
            return Result.fail("班级名以及学期不能为空");
        }
        Class c2 = classService.getOne(new QueryWrapper<Class>().eq("name", c.getName()));
        if(c2!=null) return Result.fail("班级名已存在");
        classService.save(c);
        Class c3 = classService.getOne(new QueryWrapper<Class>().eq("name", c.getName()));
        Map<String, Object> map = JWTUtils.checkToken(token);
        if(map==null){
            return Result.fail("用户未登录");
        }
        User user = userService.getById(Integer.valueOf(map.get("userId").toString()));
        List<String> roleList = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
        if(roleList.contains("teacher")){
            TeacherClass teacherClass = new TeacherClass();
            teacherClass.setTeacherId(user.getId()).setClassId(c3.getId());
            teacherClassService.save(teacherClass);
        }
        return Result.success(null);
    }

    @Authority({"admin","teacher"})
    @ApiOperation("删除班级,接口权限admin,teacher")
    @GetMapping("/delete")
    public Result delete(Integer id){
        classService.removeById(id);
        assistantClassService.remove(new QueryWrapper<AssistantClass>().eq("class_id",id));
        teacherClassService.remove(new QueryWrapper<TeacherClass>().eq("class_id",id));
        studentClassService.remove(new QueryWrapper<StudentClass>().eq("class_id",id));
        homeworkClassService.remove(new QueryWrapper<HomeworkClass>().eq("class_id",id));
        return Result.success(null);
    }

    @Authority({"admin","teacher"})
    @ApiOperation("修改班级,接口权限admin,teacher")
    @PostMapping("/modify")
    public Result modify(@RequestBody Class c){
        classService.saveOrUpdate(c);
        return Result.success(null);
    }

    @ApiOperation("查询班级列表,接口权限all")
    @GetMapping("/query")
    public Result query(Integer userId){
        User user = userService.getById(userId);
        List<String> roleList = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
        if(roleList.contains("admin")){
            List<Class> list = classService.list();
            return Result.success(list);
        }else if(roleList.contains("teacher")){
            List<TeacherClass> teacherClasses = teacherClassService.list(new QueryWrapper<TeacherClass>().eq("teacher_id", userId));
            List<Class> list = new ArrayList<>();
            for (TeacherClass teacherClass : teacherClasses) {
                Class class1 = classService.getById(teacherClass.getClassId());
                list.add(class1);
            }
            return Result.success(list);
        }else if(roleList.contains("assistant")){
            List<AssistantClass> assistantClasses = assistantClassService.list(new QueryWrapper<AssistantClass>().eq("assistant_id", userId));
            List<Class> list = new ArrayList<>();
            for (AssistantClass assistantClass : assistantClasses) {
                Class class1 = classService.getById(assistantClass.getClassId());
                list.add(class1);
            }
            return Result.success(list);
        }else if(roleList.contains("student")){
            List<StudentClass> studentClasses = studentClassService.list(new QueryWrapper<StudentClass>().eq("student_id", userId));
            List<Class> list = new ArrayList<>();
            for (StudentClass studentClass : studentClasses) {
                Class class1 = classService.getById(studentClass.getClassId());
                list.add(class1);
            }
            return Result.success(list);
        }
        List<Class> list = new ArrayList<>();
        return Result.success(list);
    }

    @ApiOperation(value = "查询班级详情,接口权限all",response = ClassVo.class)
    @GetMapping("/detail")
    public Result detail(Integer classId){
        ClassVo classVo = new ClassVo();
        Class class1 = classService.getById(classId);
        classVo.setId(class1.getId());
        classVo.setNum(class1.getNum());
        classVo.setName(class1.getName());
        classVo.setTerm(class1.getTerm());
        List<TeacherClass> teacherClasses = teacherClassService.list(new QueryWrapper<TeacherClass>().eq("class_id", classId));
        List<User> teachers = new ArrayList<>();
        for (TeacherClass teacherClass : teacherClasses) {
            Integer teacherId = teacherClass.getTeacherId();
            User user = userService.getById(teacherId);
            user.setPassword(null);
            user.setRoleList(null);
            teachers.add(user);
        }
        classVo.setTeachers(teachers);

        List<AssistantClass> assistantClasses = assistantClassService.list(new QueryWrapper<AssistantClass>().eq("class_id", classId));
        List<User> assistants = new ArrayList<>();
        for (AssistantClass assistantClass : assistantClasses) {
            Integer assistantId = assistantClass.getAssistantId();
            User user = userService.getById(assistantId);
            user.setPassword(null);
            user.setRoleList(null);
            assistants.add(user);
        }
        classVo.setAssistants(assistants);

        List<StudentClass> studentClasses = studentClassService.list(new QueryWrapper<StudentClass>().eq("class_id", classId));
        List<User> students = new ArrayList<>();
        for (StudentClass studentClass : studentClasses) {
            Integer studentId = studentClass.getStudentId();
            User user = userService.getById(studentId);
            user.setPassword(null);
            user.setRoleList(null);
            students.add(user);
        }
        classVo.setStudents(students);
        return Result.success(classVo);
    }

    @Authority({"admin","teacher"})
    @ApiOperation("设置班级教师,接口权限admin,teacher")
    @PostMapping("/teachers")
    public Result teachers(@RequestBody ClassMembers classMembers){
        teacherClassService.remove(new QueryWrapper<TeacherClass>().eq("class_id",classMembers.getClassId()));
        Integer[] ids = classMembers.getIds();
        for (Integer id : ids) {
            User user = userService.getById(id);
            List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
            if(!list.contains("teacher")){
                return Result.fail("用户角色应为教师");
            }
            TeacherClass teacherClass = new TeacherClass();
            teacherClass.setClassId(classMembers.getClassId()).setTeacherId(id);
            teacherClassService.save(teacherClass);
        }
        return Result.success(null);
    }

    @Authority({"admin","teacher"})
    @ApiOperation("设置班级助教,接口权限admin,teacher")
    @PostMapping("/assistants")
    public Result assistants(@RequestBody ClassMembers classMembers){
        assistantClassService.remove(new QueryWrapper<AssistantClass>().eq("class_id",classMembers.getClassId()));
        Integer[] ids = classMembers.getIds();
        for (Integer id : ids) {
            User user = userService.getById(id);
            List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
            if(!list.contains("assistant")){
                return Result.fail("用户角色应为助教");
            }
            AssistantClass assistantClass = new AssistantClass();
            assistantClass.setClassId(classMembers.getClassId()).setAssistantId(id);
            assistantClassService.save(assistantClass);
        }
        return Result.success(null);
    }

    @Authority({"admin","teacher"})
    @ApiOperation("设置班级学生,接口权限admin,teacher")
    @PostMapping("/students")
    public Result students(@RequestBody ClassMembers classMembers){
        studentClassService.remove(new QueryWrapper<StudentClass>().eq("class_id",classMembers.getClassId()));
        Class class1 = classService.getById(classMembers.getClassId());
        class1.setNum(0);
        Integer[] ids = classMembers.getIds();
        for (Integer id : ids) {
            User user = userService.getById(id);
            List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
            if(!list.contains("student")){
                return Result.fail("用户角色应为学生");
            }
            StudentClass studentClass = new StudentClass();
            studentClass.setClassId(classMembers.getClassId()).setStudentId(id);
            studentClassService.save(studentClass);
            class1.setNum(class1.getNum()+1);
        }
        classService.saveOrUpdate(class1);
        return Result.success(null);
    }

}
