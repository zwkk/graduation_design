package graduation.design.controller;

import graduation.design.annotation.Authority;
import graduation.design.entity.User;
import graduation.design.service.UserService;
import graduation.design.vo.AccountVo;
import graduation.design.vo.AuthorizationVo;
import graduation.design.vo.Result;
import graduation.design.vo.UserAuthorizationVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Authority("admin")
    @ApiOperation("分配权限,接口权限admin")
    @PostMapping("/edit/authorizations")
    public Result editAuthorizations(@RequestBody AuthorizationVo authorizationVo){
        Integer id = authorizationVo.getId();
        User user = userService.getById(id);
        String[] authorizations = authorizationVo.getAuthorizations();
        user.setRoleList(Arrays.toString(authorizations));
        userService.saveOrUpdate(user);
        return Result.success("权限设置成功");
    }

    @Authority("admin")
    @ApiOperation(value = "查询用户权限列表,接口权限admin",response = UserAuthorizationVo.class)
    @GetMapping("/user/authorizations")
    public Result userAuthorizations(){
        List<User> users = userService.list();
        List<UserAuthorizationVo> userAuthorizationVoList = new ArrayList<>();
        for (User user : users) {
            UserAuthorizationVo userAuthorizationVo = new UserAuthorizationVo();
            userAuthorizationVo.setId(user.getId());
            userAuthorizationVo.setName(user.getName());
            userAuthorizationVo.setAccount(user.getAccount());
            List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
            userAuthorizationVo.setAuthorizations(list);
            userAuthorizationVoList.add(userAuthorizationVo);
        }
        return Result.success(userAuthorizationVoList);
    }

    @Authority("admin")
    @ApiOperation(value = "新建账号,接口权限admin")
    @PostMapping ("/add/account")
    public Result addAccount(@RequestBody AccountVo accountVo){
        if(accountVo.getAccount()==null || accountVo.getAccount().equals("") || accountVo.getPassword()==null || accountVo.getPassword().equals("")){
            return Result.fail("账号密码不能为空");
        }
        User user = new User();
        user.setAccount(accountVo.getAccount());
        user.setPassword(accountVo.getPassword());
        if(accountVo.getName()==null){
            user.setName(accountVo.getAccount());
        }else {
            user.setName(accountVo.getName());
        }
        user.setRoleList(Arrays.toString(accountVo.getAuthorizations()));
        userService.save(user);
        return Result.success("账号创建成功");
    }

}
