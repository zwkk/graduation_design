package graduation.design.service.impl;

import graduation.design.entity.Author;
import graduation.design.dao.AuthorMapper;
import graduation.design.service.AuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作者表 服务实现类
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

}
