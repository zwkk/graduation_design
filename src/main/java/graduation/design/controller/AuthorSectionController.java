package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.AuthorSection;
import graduation.design.entity.Chapter;
import graduation.design.entity.Section;
import graduation.design.entity.User;
import graduation.design.service.AuthorSectionService;
import graduation.design.service.ChapterService;
import graduation.design.service.SectionService;
import graduation.design.service.UserService;
import graduation.design.vo.EditVo;
import graduation.design.vo.Result;
import graduation.design.vo.SectionEditVo;
import graduation.design.vo.SectionVo;
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

    @ApiOperation("新增作者编辑权限")
    @GetMapping("/addEdit")
    public Result addEdit(Integer authorId,Integer sectionId){
        User user = userService.getById(authorId);
        if(user==null) return Result.fail("账号不存在");
        List<String> list = Arrays.asList(user.getRoleList().replaceAll("[\\[\\]]", "").split(","));
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

    @ApiOperation("删除作者编辑权限")
    @GetMapping("/deleteEdit")
    public Result deleteEdit(Integer authorId,Integer sectionId){
        authorSectionService.remove(new QueryWrapper<AuthorSection>().eq("author_id",authorId).eq("section_id",sectionId));
        return Result.success("删除权限成功");
    }

    @ApiOperation(value = "查询某一节可编辑作者列表",response = User.class)
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

    @ApiOperation(value = "查询某作者可编辑节列表",response = Section.class)
    @GetMapping("/authorEdit")
    public Result authorEdit(Integer authorId){
        List<AuthorSection> authorSections = authorSectionService.list(new QueryWrapper<AuthorSection>().eq("author_id", authorId));
        List<Section> sections = new ArrayList<>();
        for (AuthorSection authorSection : authorSections) {
            sections.add(sectionService.getById(authorSection.getSectionId()));
        }
        return Result.success(sections);
    }

    @ApiOperation(value = "查询所有节可编辑作者列表",response = EditVo.class)
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
