package graduation.design.service.impl;

import graduation.design.entity.StudentAnswer;
import graduation.design.dao.StudentAnswerMapper;
import graduation.design.service.StudentAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生答题表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Service
public class StudentAnswerServiceImpl extends ServiceImpl<StudentAnswerMapper, StudentAnswer> implements StudentAnswerService {

}
