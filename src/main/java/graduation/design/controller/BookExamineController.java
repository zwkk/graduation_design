package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.BookExamine;
import graduation.design.entity.Section;
import graduation.design.entity.SectionDetail;
import graduation.design.service.*;
import graduation.design.vo.BookExamineVo;
import graduation.design.vo.ModifyVo;
import graduation.design.vo.ReasonVo;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 教材审核表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/bookExamine")
public class BookExamineController {

    @Autowired
    BookExamineService bookExamineService;

    @Autowired
    UserService userService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    SectionService sectionService;

    @Autowired
    SectionDetailService sectionDetailService;

    @Authority("admin")
    @ApiOperation(value = "展示审核列表,不包含文章内容,接口权限admin",response = BookExamineVo.class)
    @GetMapping("/list")
    public Result list(){
        List<BookExamine> bookExamineList = bookExamineService.list();
        List<BookExamineVo> list = new ArrayList<>();
        for (BookExamine bookExamine : bookExamineList) {
            BookExamineVo bookExamineVo = new BookExamineVo();
            bookExamineVo.setSectionId(bookExamine.getSectionId());
            bookExamineVo.setStatus(bookExamine.getStatus());
            bookExamineVo.setId(bookExamine.getId());
            bookExamineVo.setTime(bookExamine.getTime());
            bookExamineVo.setAuthorId(bookExamine.getAuthorId());
            bookExamineVo.setName(userService.getById(bookExamine.getAuthorId()).getName());
            Section section = sectionService.getById(bookExamine.getSectionId());
            bookExamineVo.setSectionTitle(section.getTitle());
            bookExamineVo.setChapterTitle(chapterService.getById(section.getChapterId()).getTitle());
            bookExamineVo.setVersion(bookExamine.getVersion());
            list.add(bookExamineVo);
        }
        return Result.success(list);
    }

    @Authority({"admin","author"})
    @ApiOperation(value = "根据审核列表id展开文章内容,接口权限admin,author")
    @GetMapping("/content")
    public Result list(Integer id){
        BookExamine bookExamine = bookExamineService.getById(id);
        return Result.success(bookExamine.getContent());
    }

    @Authority("admin")
    @ApiOperation(value = "通过,接口权限admin")
    @GetMapping("/pass")
    public Result pass(@ApiParam("审核id") Integer id){
        BookExamine bookExamine = bookExamineService.getById(id);
        bookExamine.setStatus("通过");
        bookExamineService.updateById(bookExamine);
        SectionDetail sectionDetail = new SectionDetail();
        sectionDetail.setSectionId(bookExamine.getSectionId());
        sectionDetail.setContent(bookExamine.getContent());
        sectionDetail.setAuthorId(bookExamine.getAuthorId());
        SectionDetail detail = sectionDetailService.getOne(new QueryWrapper<SectionDetail>().eq("section_id",bookExamine.getSectionId()).orderByDesc("version").last("limit 1"));
        if(detail==null) {
            sectionDetail.setVersion("1");
        }else {
            sectionDetail.setVersion(String.valueOf(Integer.parseInt(detail.getVersion())+1));
        }
        sectionDetailService.save(sectionDetail);
        return Result.success(null);
    }

    @Authority("admin")
    @ApiOperation(value = "不通过,接口权限admin",response = ReasonVo.class)
    @PostMapping("/fail")
    public Result fail(@RequestBody ReasonVo reasonVo){
        BookExamine bookExamine = bookExamineService.getById(reasonVo.getId());
        bookExamine.setStatus("不通过");
        bookExamine.setReason(reasonVo.getReason());
        bookExamineService.updateById(bookExamine);
        return Result.success(null);
    }

    @Authority({"admin","author"})
    @ApiOperation(value = "根据作者id返回其审核列表,接口权限admin,author",response = BookExamineVo.class)
    @GetMapping("/author/list")
    public Result authorList(Integer authorId){
        List<BookExamine> bookExamineList = bookExamineService.list(new QueryWrapper<BookExamine>().eq("author_id",authorId));
        List<BookExamineVo> list = new ArrayList<>();
        for (BookExamine bookExamine : bookExamineList) {
            BookExamineVo bookExamineVo = new BookExamineVo();
            bookExamineVo.setSectionId(bookExamine.getSectionId());
            bookExamineVo.setStatus(bookExamine.getStatus());
            bookExamineVo.setId(bookExamine.getId());
            bookExamineVo.setTime(bookExamine.getTime());
            bookExamineVo.setAuthorId(bookExamine.getAuthorId());
            bookExamineVo.setName(userService.getById(bookExamine.getAuthorId()).getName());
            Section section = sectionService.getById(bookExamine.getSectionId());
            bookExamineVo.setSectionTitle(section.getTitle());
            bookExamineVo.setChapterTitle(chapterService.getById(section.getChapterId()).getTitle());
            bookExamineVo.setVersion(bookExamine.getVersion());
            list.add(bookExamineVo);
        }
        return Result.success(list);
    }

    @Authority("author")
    @ApiOperation(value = "撤回,接口权限author")
    @GetMapping("/recall")
    public Result recall(Integer bookExamineId){
        bookExamineService.removeById(bookExamineId);
        return Result.success(null);
    }

    @Authority("author")
    @ApiOperation(value = "修改,接口权限author",response = ModifyVo.class)
    @PostMapping("/modify")
    public Result modify(@RequestBody ModifyVo modifyVo){
        BookExamine bookExamine = bookExamineService.getById(modifyVo.getBookExamineId());
        if("待审核".equals(bookExamine.getStatus())){
            bookExamine.setContent(modifyVo.getContent());
            bookExamine.setTime(LocalDateTime.now());
            bookExamineService.updateById(bookExamine);
            return Result.success(null);
        }else if("不通过".equals(bookExamine.getStatus())){
            bookExamine.setContent(modifyVo.getContent());
            bookExamine.setTime(LocalDateTime.now());
            bookExamine.setStatus("待审核");
            bookExamineService.updateById(bookExamine);
            return Result.success(null);
        }else if("通过".equals(bookExamine.getStatus())){
            return Result.fail("已经审核通过的文章无法修改，请联系管理员进行删除");
        }
        return Result.success(null);
    }
}
