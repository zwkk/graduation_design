package graduation.design.dao;

import graduation.design.entity.NoticeStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 通知学生关联表 Mapper 接口
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@Mapper
public interface NoticeStudentMapper extends BaseMapper<NoticeStudent> {

}
