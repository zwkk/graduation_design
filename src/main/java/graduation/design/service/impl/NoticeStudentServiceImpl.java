package graduation.design.service.impl;

import graduation.design.entity.NoticeStudent;
import graduation.design.dao.NoticeStudentMapper;
import graduation.design.service.NoticeStudentService;
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
public class NoticeStudentServiceImpl extends ServiceImpl<NoticeStudentMapper, NoticeStudent> implements NoticeStudentService {

}
