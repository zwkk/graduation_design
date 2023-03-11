package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.Chapter;
import graduation.design.entity.Section;
import graduation.design.service.ChapterService;
import graduation.design.service.SectionService;
import graduation.design.vo.ChapterSectionVo;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 节表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    SectionService sectionService;

    @Autowired
    ChapterService chapterService;

    @ApiOperation("新增节")
    @PostMapping("/addSection")
    public Result addSection(@RequestBody Section section){
        section.setId(null);
        if(sectionService.getOne(new QueryWrapper<Section>().eq("num",section.getNum()).eq("version",section.getVersion()))!=null){
            return Result.fail("该节已存在");
        }
        if(chapterService.getOne(new QueryWrapper<Chapter>().eq("num",section.getChapterNum()).eq("version",section.getVersion()))==null) return Result.fail("该章不存在");
        sectionService.save(section);
        return Result.success(null);
    }

    @ApiOperation("删除节")
    @GetMapping("/deleteSection")
    public Result deleteSection(Integer id){
        if(sectionService.getById(id)==null){
            return Result.fail("该节不存在");
        }
        sectionService.removeById(id);
        return Result.success(null);
    }

    @ApiOperation("修改节")
    @PostMapping("/changeSection")
    public Result changeSection(@RequestBody Section section){
        if(sectionService.getById(section.getId())==null){
            return Result.fail("该章不存在");
        }
        sectionService.saveOrUpdate(section);
        return Result.success(null);
    }

    @ApiOperation(value = "根据版本获取教材内容",response = ChapterSectionVo.class)
    @GetMapping ("/querySection")
    public Result querySection(String version){
        List<Chapter> chapterList = chapterService.list(new QueryWrapper<Chapter>().eq("version", version));
        if(chapterList.size()==0) return Result.fail("未查询到对应版本的教材");
        List<ChapterSectionVo> chapterSectionVoList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            ChapterSectionVo chapterSectionVo = new ChapterSectionVo();
            chapterSectionVo.setVersion(version);
            chapterSectionVo.setNum(chapter.getNum());
            chapterSectionVo.setTitle(chapter.getTitle());
            List<Section> sectionList = sectionService.list(new QueryWrapper<Section>().eq("version", version).eq("chapter_num", chapter.getNum()));
            chapterSectionVo.setSectionList(sectionList);
            chapterSectionVoList.add(chapterSectionVo);
        }
        return Result.success(chapterSectionVoList);
    }

}
