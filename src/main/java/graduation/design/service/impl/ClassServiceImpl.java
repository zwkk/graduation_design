package graduation.design.service.impl;

import graduation.design.entity.Class;
import graduation.design.dao.ClassMapper;
import graduation.design.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

}
