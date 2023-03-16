package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.*;
import graduation.design.service.*;
import graduation.design.util.JWTUtils;
import graduation.design.vo.Result;
import graduation.design.vo.params.LoginParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class LoginController {

    private static final String slat = "zwk!@#";

    @Autowired
    UserService userService;

    @ApiOperation(value = "登录,返回token")
    @PostMapping("/login")
    public Result login(@RequestBody LoginParam loginParam){
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail("账号密码不能为空");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("account", account).eq("password", password));
        if(user==null) return Result.fail("账号或密码错误");
        user.setLastLogin(LocalDateTime.now());
        userService.updateById(user);
        String token = JWTUtils.createToken(user.getId());
        return Result.success(token);
    }

    @ApiOperation(value = "根据token获取当前登录的用户信息",response = User.class)
    @GetMapping("/getUser")
    public Result getUser(@RequestHeader("Authorization") String token){
        Map<String, Object> map = JWTUtils.checkToken(token);
        if(map==null){
            return Result.fail("用户未登录");
        }
        User user = userService.getById(Integer.valueOf(map.get("userId").toString()));
        user.setPassword(null);
        return Result.success(user);
    }

}
