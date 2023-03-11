package graduation.design.dao;

import graduation.design.entity.Practice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 章节练习题目表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Mapper
public interface PracticeMapper extends BaseMapper<Practice> {

}
