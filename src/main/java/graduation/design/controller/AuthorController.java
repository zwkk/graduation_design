package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.User;
import graduation.design.service.UserService;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 作者表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    UserService userService;

    @Authority("admin")
    @ApiOperation(value = "获取作者列表,接口权限admin",response = User.class)
    @GetMapping ("/getAuthors")
    public Result getAuthors(){
        List<User> authors = userService.list(new QueryWrapper<User>().like("role_list","author").select("id","account","name","avatar","last_login"));
        return Result.success(authors);
    }

}
