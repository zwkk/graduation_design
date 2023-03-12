package graduation.design.dao;

import graduation.design.entity.HomeworkClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 作业班级关联表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Mapper
public interface HomeworkClassMapper extends BaseMapper<HomeworkClass> {

}
