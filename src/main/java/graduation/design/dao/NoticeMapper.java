package graduation.design.dao;

import graduation.design.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 通知表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}
