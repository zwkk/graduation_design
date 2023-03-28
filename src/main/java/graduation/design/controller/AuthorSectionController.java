package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.*;
import graduation.design.service.*;
import graduation.design.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 作者权限表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月09日
 */
@RestController
@RequestMapping("/authorSection")
public class AuthorSectionController {

    @Autowired
    AuthorSectionService authorSectionService;

    @Autowired
    UserService userService;

    @Autowired
    SectionService sectionService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    BookExamineService bookExamineService;

    @Autowired
    SectionDetailService sectionDetailService;

    @Authority("admin")
    @ApiOperation("新增作者编辑权限,接口权限admin")
    @GetMapping("/addEdit")
    public Result addEdit(Integer authorId,Integer sectionId){
        User user = userService.getById(authorId);
        if(user==null) return Result.fail("账号不存在");
        List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
        if(!list.contains("author")){
            return Result.fail("请先申请成为作者");
        }
        if(sectionService.getById(sectionId)==null) return Result.fail("该节不存在");
        if(authorSectionService.getOne(new QueryWrapper<AuthorSection>().eq("author_id",authorId).eq("section_id",sectionId))!=null) return Result.fail("该权限已存在");
        AuthorSection authorSection = new AuthorSection();
        authorSection.setAuthorId(authorId).setSectionId(sectionId);
        authorSectionService.save(authorSection);
        return Result.success("添加权限成功");
    }

    @Authority("admin")
    @ApiOperation("删除作者编辑权限,接口权限admin")
    @GetMapping("/deleteEdit")
    public Result deleteEdit(Integer authorId,Integer sectionId){
        authorSectionService.remove(new QueryWrapper<AuthorSection>().eq("author_id",authorId).eq("section_id",sectionId));
        return Result.success("删除权限成功");
    }

    @Authority("admin")
    @ApiOperation("编辑某一节作者列表,接口权限admin")
    @PostMapping("/edit/authors")
    public Result editAuthors(@RequestBody AuthorSectionVo authorSectionVo){
        if(sectionService.getById(authorSectionVo.getSectionId())==null) return Result.fail("该节不存在");
        authorSectionService.remove(new QueryWrapper<AuthorSection>().eq("section_id",authorSectionVo.getSectionId()));
        for (Integer id : authorSectionVo.getAuthorIds()) {
            User user = userService.getById(id);
            if(user==null) return Result.fail("账号不存在");
            List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(", "));
            if(!list.contains("author")){
                return Result.fail("请先申请成为作者");
            }
            if(authorSectionService.getOne(new QueryWrapper<AuthorSection>().eq("author_id",id).eq("section_id",authorSectionVo.getSectionId()))!=null) return Result.fail("该权限已存在");
            AuthorSection authorSection = new AuthorSection();
            authorSection.setAuthorId(id).setSectionId(authorSectionVo.getSectionId());
            authorSectionService.save(authorSection);
        }
        return Result.success("添加权限成功");
    }

    @Authority("admin")
    @ApiOperation(value = "查询某一节可编辑作者列表,接口权限admin",response = User.class)
    @GetMapping("/sectionEdit")
    public Result sectionEdit(Integer sectionId){
        List<AuthorSection> authorSections = authorSectionService.list(new QueryWrapper<AuthorSection>().eq("section_id", sectionId));
        List<User> authors = new ArrayList<>();
        for (AuthorSection authorSection : authorSections) {
            User user = userService.getById(authorSection.getAuthorId());
            user.setPassword(null);
            authors.add(user);
        }
        return Result.success(authors);
    }

    @Authority({"admin","author"})
    @ApiOperation(value = "查询某作者可编辑节列表,接口权限admin,author",response = EditSectionVo.class)
    @GetMapping("/authorEdit")
    public Result authorEdit(Integer authorId){
        List<AuthorSection> authorSections = authorSectionService.list(new QueryWrapper<AuthorSection>().eq("author_id", authorId));
        List<EditSectionVo> editSectionVos = new ArrayList<>();
        for (AuthorSection authorSection : authorSections) {
            EditSectionVo editSectionVo = new EditSectionVo();
            editSectionVo.setSectionId(authorSection.getSectionId());
            Section section = sectionService.getById(authorSection.getSectionId());
            editSectionVo.setSectionTitle(section.getTitle());
            editSectionVo.setChapterId(section.getChapterId());
            editSectionVo.setChapterTitle(chapterService.getById(section.getChapterId()).getTitle());
            BookExamine bookExamine = bookExamineService.getOne(new QueryWrapper<BookExamine>().eq("author_id", authorId).eq("section_id", authorSection.getSectionId()).orderByDesc("time").last("limit 1"));
            if(bookExamine!=null){
                editSectionVo.setStatus("已编辑");
                editSectionVo.setTime(bookExamine.getTime());
            }else {
                editSectionVo.setStatus("未编辑");
            }
            SectionDetail detail = sectionDetailService.getOne(new QueryWrapper<SectionDetail>().eq("section_id",authorSection.getSectionId()).orderByDesc("version").last("limit 1"));
            if(detail==null) {
                editSectionVo.setVersion("1");
            }else {
                editSectionVo.setVersion(String.valueOf(Integer.parseInt(detail.getVersion())+1));
            }
            editSectionVos.add(editSectionVo);
        }
        return Result.success(editSectionVos);
    }

    @Authority("admin")
    @ApiOperation(value = "查询所有节可编辑作者列表,接口权限admin",response = EditVo.class)
    @GetMapping("/edit")
    public Result edit(){
        List<EditVo> editVoList = new ArrayList<>();
        List<Chapter> chapterList = chapterService.list();
        for (Chapter chapter : chapterList) {
            EditVo editVo = new EditVo();
            editVo.setChapterId(chapter.getId());
            editVo.setTitle(chapter.getTitle());
            List<SectionEditVo> sectionEditVoList = new ArrayList<>();
            List<Section> sectionList = sectionService.list(new QueryWrapper<Section>().eq("chapter_id",chapter.getId()));
            for (Section section : sectionList) {
                SectionEditVo sectionEditVo = new SectionEditVo();
                sectionEditVo.setSectionId(section.getId());
                sectionEditVo.setTitle(section.getTitle());
                List<AuthorSection> authorSections = authorSectionService.list(new QueryWrapper<AuthorSection>().eq("section_id", section.getId()));
                List<User> authors = new ArrayList<>();
                for (AuthorSection authorSection : authorSections) {
                    User user = userService.getById(authorSection.getAuthorId());
                    user.setPassword(null);
                    authors.add(user);
                }
                sectionEditVo.setAuthors(authors);
                sectionEditVoList.add(sectionEditVo);
            }
            editVo.setSectionEditVoList(sectionEditVoList);
            editVoList.add(editVo);
        }
        return Result.success(editVoList);
    }

}
