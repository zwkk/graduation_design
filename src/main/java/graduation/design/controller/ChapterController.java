package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.*;
import graduation.design.service.*;
import graduation.design.vo.ChapterSectionVo;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    SectionService sectionService;

    @Autowired
    AuthorSectionService authorSectionService;

    @Autowired
    BookExamineService bookExamineService;

    @Autowired
    SectionDetailService sectionDetailService;

    @Authority("admin")
    @ApiOperation("新增章,接口权限admin")
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody Chapter chapter){
        chapter.setId(null);
        chapterService.save(chapter);
        return Result.success(null);
    }

    @Authority("admin")
    @ApiOperation("删除章,接口权限admin")
    @GetMapping("/deleteChapter")
    public Result deleteChapter(Integer id){
        if(chapterService.getById(id)==null){
            return Result.fail("该章不存在");
        }
        List<Section> sections = sectionService.list(new QueryWrapper<Section>().eq("chapter_id", id));
        for (Section section : sections) {
            authorSectionService.remove(new QueryWrapper<AuthorSection>().eq("section_id",section.getId()));
            bookExamineService.remove(new QueryWrapper<BookExamine>().eq("section_id",section.getId()));
            sectionDetailService.remove(new QueryWrapper<SectionDetail>().eq("section_id",section.getId()));
        }
        sectionService.remove(new QueryWrapper<Section>().eq("chapter_id",id));
        chapterService.removeById(id);
        return Result.success(null);
    }

    @Authority("admin")
    @ApiOperation("修改章,接口权限admin")
    @PostMapping("/changeChapter")
    public Result changeChapter(@RequestBody Chapter chapter){
        if(chapterService.getById(chapter.getId())==null){
            return Result.fail("该章不存在");
        }
        chapterService.saveOrUpdate(chapter);
        return Result.success(null);
    }

    @ApiOperation(value = "获取章节目录,接口权限all",response = ChapterSectionVo.class)
    @GetMapping ("/getChapter")
    public Result getChapter(){
        List<ChapterSectionVo> chapterSectionVoList = new ArrayList<>();
        List<Chapter> chapterList = chapterService.list();
        for (Chapter chapter : chapterList) {
            ChapterSectionVo chapterSectionVo = new ChapterSectionVo();
            chapterSectionVo.setId(chapter.getId());
            chapterSectionVo.setTitle(chapter.getTitle());
            List<Section> sectionList = sectionService.list(new QueryWrapper<Section>().eq("chapter_id",chapter.getId()).orderByAsc("section_order"));
            chapterSectionVo.setSectionList(sectionList);
            chapterSectionVoList.add(chapterSectionVo);
        }
        return Result.success(chapterSectionVoList);
    }

}
