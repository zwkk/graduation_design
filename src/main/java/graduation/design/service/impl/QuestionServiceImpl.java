package graduation.design.service.impl;

import graduation.design.entity.Question;
import graduation.design.dao.QuestionMapper;
import graduation.design.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提问表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
