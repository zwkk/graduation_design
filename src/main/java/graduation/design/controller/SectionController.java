package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.*;
import graduation.design.service.*;
import graduation.design.vo.ChapterSectionVo;
import graduation.design.vo.ContentVo;
import graduation.design.vo.Result;
import graduation.design.vo.SectionVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    SectionDetailService sectionDetailService;

    @Autowired
    AuthorSectionService authorSectionService;

    @Autowired
    BookExamineService bookExamineService;

    @ApiOperation("新增节")
    @PostMapping("/addSection")
    public Result addSection(@RequestBody Section section){
        section.setId(null);
        if(chapterService.getOne(new QueryWrapper<Chapter>().eq("id",section.getChapterId()))==null) return Result.fail("该章不存在");
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
            return Result.fail("该节不存在");
        }
        sectionService.saveOrUpdate(section);
        return Result.success(null);
    }

    @ApiOperation(value = "查询某节版本列表",response = SectionDetail.class)
    @GetMapping("/querySection")
    public Result querySection(Integer sectionId){
        List<SectionDetail> versionList = sectionDetailService.list(new QueryWrapper<SectionDetail>().eq("section_id", sectionId).select("section_id", "version"));
        return Result.success(versionList);
    }

    @ApiOperation(value = "根据节和版本号查询教材内容",response = SectionVo.class)
    @GetMapping ("/queryContent")
    public Result queryContent(Integer sectionId,String version){
        SectionDetail detail = sectionDetailService.getOne(new QueryWrapper<SectionDetail>().eq("section_id", sectionId).eq("version", version));
        SectionVo sectionVo = new SectionVo();
        sectionVo.setContent(detail.getContent());
        sectionVo.setVersion(detail.getVersion());
        sectionVo.setId(sectionService.getById(sectionId).getId());
        sectionVo.setTitle(sectionService.getById(sectionId).getTitle());
        return Result.success(sectionVo);
    }

    @ApiOperation(value = "根据节查询教材内容,默认最新",response = SectionVo.class)
    @GetMapping ("/queryNewContent")
    public Result queryNewContent(Integer sectionId){
        SectionVo sectionVo = new SectionVo();
        SectionDetail detail = sectionDetailService.getOne(new QueryWrapper<SectionDetail>().eq("section_id", sectionId).orderByDesc("version").last("limit 1"));
        if(detail == null){
            sectionVo.setId(sectionId);
            sectionVo.setTitle(sectionService.getById(sectionId).getTitle());
            sectionVo.setContent(null);
            sectionVo.setVersion(null);
        }else {
            sectionVo.setContent(detail.getContent());
            sectionVo.setVersion(detail.getVersion());
            sectionVo.setId(sectionId);
            sectionVo.setTitle(sectionService.getById(sectionId).getTitle());
        }
        return Result.success(sectionVo);
    }

    @ApiOperation("作者编辑教材")
    @PostMapping ("/editContent")
    public Result editContent(@RequestBody ContentVo contentVo){
        if(authorSectionService.getOne(new QueryWrapper<AuthorSection>().eq("author_id",contentVo.getAuthorId()).eq("section_id",contentVo.getSectionId()))==null) {
            return Result.fail("无权限编辑");
        }
        BookExamine bookExamine = new BookExamine();
        bookExamine.setTime(LocalDateTime.now());
        bookExamine.setAuthorId(contentVo.getAuthorId());
        bookExamine.setSectionId(contentVo.getSectionId());
        bookExamine.setContent(contentVo.getContent());
        SectionDetail detail = sectionDetailService.getOne(new QueryWrapper<SectionDetail>().eq("section_id",contentVo.getSectionId()).orderByDesc("version").last("limit 1"));
        if(detail==null) {
            bookExamine.setVersion("1");
        }else {
            bookExamine.setVersion(String.valueOf(Integer.parseInt(detail.getVersion())+1));
        }
        bookExamine.setStatus("待审核");
        bookExamineService.save(bookExamine);
        return Result.success(null);
    }


}

