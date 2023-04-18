package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.Progress;
import graduation.design.entity.User;
import graduation.design.service.ProgressService;
import graduation.design.service.SectionService;
import graduation.design.vo.AuthorizationVo;
import graduation.design.vo.RecordVo;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 教材阅读进度表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    ProgressService progressService;

    @Autowired
    SectionService sectionService;

    @ApiOperation("阅读教材,单次阅读时长少于1min则不会记录,接口权限all")
    @PostMapping("/read")
    public Result read(@RequestBody Progress progress){
        if(progress.getReadTime().isBefore(LocalTime.parse("00:01:00"))){
            return Result.success("阅读时间不足一分钟");
        }
        Progress progress1 = progressService.getOne(new QueryWrapper<Progress>().eq("section_id", progress.getSectionId()).eq("student_id", progress.getStudentId()));
        if(progress1!=null){
            progressService.removeById(progress1.getId());
            LocalTime newTime = progress1.getReadTime().plusHours(progress.getReadTime().getHour()).plusMinutes(progress.getReadTime().getMinute()).plusSeconds(progress.getReadTime().getSecond());
            progress1.setReadTime(newTime);
            progress1.setId(null);
            progressService.save(progress1);
        }else {
            progress.setId(null);
            progressService.save(progress);
        }
        return Result.success("记录成功");
    }

    @ApiOperation("上次看到,学习时长,已学习节数,总节数,接口权限all")
    @GetMapping("/record")
    public Result record(Integer userId){
        RecordVo recordVo = new RecordVo();
        Progress progress = progressService.getOne(new QueryWrapper<Progress>().eq("student_id", userId).orderByDesc("id").last("limit 1"));
        if(progress!=null){
            recordVo.setSectionId(progress.getSectionId());
            recordVo.setSectionName(sectionService.getById(progress.getSectionId()).getTitle());
        }
        List<Progress> progresses = progressService.list(new QueryWrapper<Progress>().eq("student_id", userId));
        LocalTime time = LocalTime.parse("00:00:00");
        for (Progress progress1 : progresses) {
            time = time.plusHours(progress1.getReadTime().getHour()).plusMinutes(progress1.getReadTime().getMinute()).plusSeconds(progress1.getReadTime().getSecond());
        }
        recordVo.setTime(time);
        recordVo.setReadSection(progresses.size());
        recordVo.setAllSection(sectionService.list().size());
        return Result.success(recordVo);
    }

}
