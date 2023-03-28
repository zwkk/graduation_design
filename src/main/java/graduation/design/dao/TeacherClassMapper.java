package graduation.design.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import graduation.design.entity.StudentClass;
import graduation.design.entity.TeacherClass;
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
public interface TeacherClassMapper extends BaseMapper<TeacherClass> {

}
