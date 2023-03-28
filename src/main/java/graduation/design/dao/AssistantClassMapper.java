package graduation.design.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import graduation.design.entity.AssistantClass;
import graduation.design.entity.StudentClass;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Mapper
public interface AssistantClassMapper extends BaseMapper<AssistantClass> {

}
