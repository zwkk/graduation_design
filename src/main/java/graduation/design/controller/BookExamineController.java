package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.BookExamine;
import graduation.design.entity.Section;
import graduation.design.entity.SectionDetail;
import graduation.design.service.*;
import graduation.design.vo.BookExamineVo;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "展示待审核列表,不包含文章内容",response = BookExamineVo.class)
    @GetMapping("/list")
    public Result list(){
        List<BookExamine> bookExamineList = bookExamineService.list(new QueryWrapper<BookExamine>().eq("status","待审核"));
        List<BookExamineVo> list = new ArrayList<>();
        for (BookExamine bookExamine : bookExamineList) {
            BookExamineVo bookExamineVo = new BookExamineVo();
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

    @ApiOperation(value = "根据审核列表id展开文章内容")
    @GetMapping("/content")
    public Result list(Integer id){
        BookExamine bookExamine = bookExamineService.getById(id);
        return Result.success(bookExamine.getContent());
    }

    @ApiOperation(value = "通过")
    @GetMapping("/pass")
    public Result pass(@ApiParam("审核id") Integer id){
        BookExamine bookExamine = bookExamineService.getById(id);
        bookExamine.setStatus("通过");
        bookExamineService.updateById(bookExamine);
        SectionDetail sectionDetail = new SectionDetail();
        sectionDetail.setSectionId(bookExamine.getSectionId());
        sectionDetail.setVersion(bookExamine.getVersion());
        sectionDetail.setContent(bookExamine.getContent());
        sectionDetail.setAuthorId(bookExamine.getAuthorId());
        sectionDetailService.save(sectionDetail);
        return Result.success(null);
    }

    @ApiOperation(value = "不通过")
    @GetMapping("/fail")
    public Result fail(@ApiParam("审核id") Integer id){
        BookExamine bookExamine = bookExamineService.getById(id);
        bookExamine.setStatus("不通过");
        bookExamineService.updateById(bookExamine);
        return Result.success(null);
    }

}
