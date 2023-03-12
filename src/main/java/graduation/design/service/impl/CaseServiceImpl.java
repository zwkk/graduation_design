package graduation.design.service.impl;

import graduation.design.entity.Case;
import graduation.design.dao.CaseMapper;
import graduation.design.service.CaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 案例表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {

}
