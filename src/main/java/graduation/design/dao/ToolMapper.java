package graduation.design.dao;

import graduation.design.entity.Tool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 工具表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Mapper
public interface ToolMapper extends BaseMapper<Tool> {

}
