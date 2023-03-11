package graduation.design.dao;

import graduation.design.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
