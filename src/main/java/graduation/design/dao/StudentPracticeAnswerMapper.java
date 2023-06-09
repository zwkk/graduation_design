package graduation.design.dao;

import graduation.design.entity.StudentPracticeAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生练习作答情况表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Mapper
public interface StudentPracticeAnswerMapper extends BaseMapper<StudentPracticeAnswer> {

}
