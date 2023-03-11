package graduation.design.service.impl;

import graduation.design.entity.Progress;
import graduation.design.dao.ProgressMapper;
import graduation.design.service.ProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 教材阅读进度表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Service
public class ProgressServiceImpl extends ServiceImpl<ProgressMapper, Progress> implements ProgressService {

}
