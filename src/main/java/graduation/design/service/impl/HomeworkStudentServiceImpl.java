package graduation.design.service.impl;

import graduation.design.entity.HomeworkStudent;
import graduation.design.dao.HomeworkStudentMapper;
import graduation.design.service.HomeworkStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作业学生关联表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Service
public class HomeworkStudentServiceImpl extends ServiceImpl<HomeworkStudentMapper, HomeworkStudent> implements HomeworkStudentService {

}
