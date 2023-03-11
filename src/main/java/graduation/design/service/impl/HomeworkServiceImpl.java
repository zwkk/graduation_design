package graduation.design.service.impl;

import graduation.design.entity.Homework;
import graduation.design.dao.HomeworkMapper;
import graduation.design.service.HomeworkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作业表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Service
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper, Homework> implements HomeworkService {

}
