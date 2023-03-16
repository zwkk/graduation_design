package graduation.design.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import graduation.design.dao.UserMapper;
import graduation.design.entity.User;
import graduation.design.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
