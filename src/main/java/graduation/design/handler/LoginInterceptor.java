package graduation.design.handler;

import com.alibaba.fastjson.JSON;
import graduation.design.annotation.Authority;
import graduation.design.entity.User;
import graduation.design.service.UserService;
import graduation.design.util.JWTUtils;
import graduation.design.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null){
            Result result = Result.fail( "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if(map==null){
            Result result = Result.fail( "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        User user = userService.getById(Integer.valueOf(map.get("userId").toString()));
        if (user == null){
            Result result = Result.fail( "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        Authority authority = method.getAnnotation(Authority.class);
        if(authority!=null){
            String roleList = user.getRoleList();
            List<String> roles = Arrays.asList(roleList.replaceAll("[\\[\\]]", "").split(","));
            String[] authorities = authority.value();
            for (String role : roles) {
                for (String s : authorities) {
                    if(role.equals(s)) {
                        return true;
                    }
                }
            }
            Result result = Result.fail( "无权限访问此接口");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        return true;
    }
}
