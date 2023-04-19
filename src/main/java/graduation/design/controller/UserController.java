package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.User;
import graduation.design.service.UserService;
import graduation.design.vo.InformationVo;
import graduation.design.vo.PasswordVo;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("修改密码,接口权限all")
    @PostMapping("/password")
    public Result password(@RequestBody PasswordVo passwordVo){
        User user = userService.getOne(new QueryWrapper<User>().eq("account", passwordVo.getAccount()).eq("password", passwordVo.getOldPassword()));
        if(user==null){
            return Result.fail("账号或密码错误");
        }
        user.setPassword(passwordVo.getNewPassword());
        userService.saveOrUpdate(user);
        return Result.success("修改密码成功");
    }

    @ApiOperation("修改个人信息,接口权限all")
    @PostMapping("/information")
    public Result information(@RequestBody InformationVo informationVo){
        User user = userService.getById(informationVo.getId());
        user.setName(informationVo.getName());
        userService.saveOrUpdate(user);
        return Result.success("个人信息修改成功");
    }
}
