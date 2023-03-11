package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.Chapter;
import graduation.design.service.ChapterService;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 章表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @ApiOperation("新增章")
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody Chapter chapter){
        chapter.setId(null);
        if(chapterService.getOne(new QueryWrapper<Chapter>().eq("num",chapter.getNum()).eq("version",chapter.getVersion()))!=null){
            return Result.fail("该章已存在");
        }
        chapterService.save(chapter);
        return Result.success(null);
    }

    @ApiOperation("删除章")
    @GetMapping("/deleteChapter")
    public Result deleteChapter(Integer id){
        if(chapterService.getById(id)==null){
            return Result.fail("该章不存在");
        }
        chapterService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation("修改章")
    @PostMapping("/changeChapter")
    public Result changeChapter(@RequestBody Chapter chapter){
        if(chapterService.getById(chapter.getId())==null){
            return Result.fail("该章不存在");
        }
        chapterService.saveOrUpdate(chapter);
        return Result.success(null);
    }
}
