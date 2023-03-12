package graduation.design.service.impl;

import graduation.design.entity.Student;
import graduation.design.dao.StudentMapper;
import graduation.design.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
