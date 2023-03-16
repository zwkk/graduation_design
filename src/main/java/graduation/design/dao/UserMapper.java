package graduation.design.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import graduation.design.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
