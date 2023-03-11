package graduation.design.service.impl;

import graduation.design.entity.Teacher;
import graduation.design.dao.TeacherMapper;
import graduation.design.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 教师表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

}
