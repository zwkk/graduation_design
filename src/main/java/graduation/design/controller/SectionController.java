package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.AuthorSection;
import graduation.design.entity.Chapter;
import graduation.design.entity.Section;
import graduation.design.entity.SectionDetail;
import graduation.design.service.AuthorSectionService;
import graduation.design.service.ChapterService;
import graduation.design.service.SectionDetailService;
import graduation.design.service.SectionService;
import graduation.design.vo.ChapterSectionVo;
import graduation.design.vo.Result;
import graduation.design.vo.SectionVo;
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

    @Autowired
    SectionDetailService sectionDetailService;

    @Autowired
    AuthorSectionService authorSectionService;

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
        SectionDetail detail = sectionDetailService.getOne(new QueryWrapper<SectionDetail>().eq("section_id", sectionId).orderByDesc("version").last("limit 1"));
        SectionVo sectionVo = new SectionVo();
        sectionVo.setContent(detail.getContent());
        sectionVo.setVersion(detail.getVersion());
        sectionVo.setId(sectionService.getById(sectionId).getId());
        sectionVo.setTitle(sectionService.getById(sectionId).getTitle());
        return Result.success(sectionVo);
    }

    @ApiOperation("作者编辑教材")
    @PostMapping ("/editContent")
    public Result queryNewContent(Integer authorId,Integer sectionId,String content){
        if(authorSectionService.getOne(new QueryWrapper<AuthorSection>().eq("author_id",authorId).eq("section_id",sectionId))==null) {
            return Result.fail("无权限编辑");
        }
        SectionDetail sectionDetail = new SectionDetail();
        sectionDetail.setSectionId(sectionId).setContent(content).setAuthorId(authorId);
        SectionDetail detail = sectionDetailService.getOne(new QueryWrapper<SectionDetail>().eq("section_id",sectionId).orderByDesc("version").last("limit 1"));
        if(detail==null) {
            sectionDetail.setVersion("1");
        }else {
            sectionDetail.setVersion(String.valueOf(Integer.parseInt(detail.getVersion())+1));
        }
        sectionDetailService.save(sectionDetail);
        return Result.success(null);
    }


}

