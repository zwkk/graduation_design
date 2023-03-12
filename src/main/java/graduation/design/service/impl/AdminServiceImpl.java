package graduation.design.service.impl;

import graduation.design.entity.Admin;
import graduation.design.dao.AdminMapper;
import graduation.design.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
