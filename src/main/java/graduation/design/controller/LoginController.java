package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.*;
import graduation.design.service.*;
import graduation.design.util.JWTUtils;
import graduation.design.vo.Result;
import graduation.design.vo.UserVo;
import graduation.design.vo.params.LoginParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    private static final String slat = "zwk!@#";

    @Autowired
    AdminService adminService;

    @Autowired
    AuthorService authorService;

    @Autowired
    AssistantService assistantService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @ApiOperation(value = "登录,返回token")
    @PostMapping("/login")
    public Result login(@RequestBody LoginParam loginParam){
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail("账号密码不能为空");
        }
        if(account.startsWith("ad")){
            Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("account", account).eq("password", password));
            if(admin==null) return Result.fail("账号或密码错误");
            String token = JWTUtils.createToken(admin.getId(),"admin");
            return Result.success(token);
        }else if(account.startsWith("as")){
            Assistant assistant = assistantService.getOne(new QueryWrapper<Assistant>().eq("account", account).eq("password", password));
            if(assistant==null) return Result.fail("账号或密码错误");
            String token = JWTUtils.createToken(assistant.getId(),"assistant");
            return Result.success(token);
        }else if(account.startsWith("au")){
            Author author = authorService.getOne(new QueryWrapper<Author>().eq("account", account).eq("password", password));
            if(author==null) return Result.fail("账号或密码错误");
            String token = JWTUtils.createToken(author.getId(),"author");
            return Result.success(token);
        }else if(account.startsWith("t")){
            Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("account", account).eq("password", password));
            if(teacher==null) return Result.fail("账号或密码错误");
            String token = JWTUtils.createToken(teacher.getId(),"teacher");
            return Result.success(token);
        }else if(account.startsWith("s")){
            Student student = studentService.getOne(new QueryWrapper<Student>().eq("account", account).eq("password", password));
            if(student==null) return Result.fail("账号或密码错误");
            String token = JWTUtils.createToken(student.getId(),"student");
            return Result.success(token);
        }else {
            return Result.fail("账号不存在");
        }
    }

    @ApiOperation(value = "根据token获取当前登录的用户信息",response = UserVo.class)
    @GetMapping("/getUser")
    public Result getUser(@RequestHeader("Authorization") String token){
        Map<String, Object> map = JWTUtils.checkToken(token);
        if(map==null){
            return Result.fail("用户未登录");
        }
        UserVo user = new UserVo();
        user.setId(Integer.valueOf(map.get("userId").toString()));
        user.setRole(map.get("role").toString());
        if(user.getRole().equals("admin")){
            Admin admin = adminService.getById(user.getId());
            user.setAccount(admin.getAccount());
            user.setName(admin.getName());
            user.setAvatar(admin.getAvatar());
        }else if(user.getRole().equals("assistant")){
            Assistant assistant = assistantService.getById(user.getId());
            user.setAccount(assistant.getAccount());
            user.setName(assistant.getName());
            user.setAvatar(assistant.getAvatar());
        }else if(user.getRole().equals("author")){
            Author author = authorService.getById(user.getId());
            user.setAccount(author.getAccount());
            user.setName(author.getName());
            user.setAvatar(author.getAvatar());
        }else if(user.getRole().equals("teacher")){
            Teacher teacher = teacherService.getById(user.getId());
            user.setAccount(teacher.getAccount());
            user.setName(teacher.getName());
            user.setAvatar(teacher.getAvatar());
        }else if(user.getRole().equals("student")){
            Student student = studentService.getById(user.getId());
            user.setAccount(student.getAccount());
            user.setName(student.getName());
            user.setAvatar(student.getAvatar());
        }
        return Result.success(user);
    }

}
