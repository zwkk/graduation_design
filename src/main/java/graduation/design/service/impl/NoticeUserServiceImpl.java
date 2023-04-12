package graduation.design.service.impl;

import graduation.design.dao.NoticeUserMapper;
import graduation.design.entity.NoticeUser;
import graduation.design.service.NoticeUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知学生关联表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Service
public class NoticeUserServiceImpl extends ServiceImpl<NoticeUserMapper, NoticeUser> implements NoticeUserService {

}
