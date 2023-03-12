package graduation.design.dao;

import graduation.design.entity.ToolLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 工具标签关联表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Mapper
public interface ToolLabelMapper extends BaseMapper<ToolLabel> {

}
