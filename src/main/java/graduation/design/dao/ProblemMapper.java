package graduation.design.dao;

import graduation.design.entity.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题目表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {

}
