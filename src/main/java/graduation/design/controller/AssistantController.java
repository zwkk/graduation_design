package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.User;
import graduation.design.service.UserService;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 助教表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/assistant")
public class AssistantController {

    @Autowired
    UserService userService;

    @Authority({"admin","teacher"})
    @ApiOperation("查询助教列表,接口权限admin,teacher")
    @GetMapping("/list")
    public Result list(){
        List<User> assistants = userService.list(new QueryWrapper<User>().like("role_list","assistant").select("id","account","name","avatar","last_login"));
        return Result.success(assistants);
    }

}
