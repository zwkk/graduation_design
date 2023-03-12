package graduation.design.dao;

import graduation.design.entity.StudentAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生答题表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Mapper
public interface StudentAnswerMapper extends BaseMapper<StudentAnswer> {

}
