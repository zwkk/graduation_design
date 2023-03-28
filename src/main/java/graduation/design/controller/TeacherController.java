package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.User;
import graduation.design.service.UserService;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 教师表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    UserService userService;

    @Authority({"admin","teacher"})
    @ApiOperation("查询教师列表,接口权限admin,teacher")
    @GetMapping("/list")
    public Result list(){
        List<User> teachers = userService.list(new QueryWrapper<User>().like("role_list","teacher").select("id","account","name","avatar","last_login"));
        return Result.success(teachers);
    }

}
