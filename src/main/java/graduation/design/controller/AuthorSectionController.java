package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.Author;
import graduation.design.entity.AuthorSection;
import graduation.design.service.AuthorSectionService;
import graduation.design.service.AuthorService;
import graduation.design.service.SectionService;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    AuthorService authorService;

    @Autowired
    SectionService sectionService;

    @ApiOperation("新增作者编辑权限")
    @GetMapping("/addEdit")
    public Result addEdit(Integer authorId,Integer sectionId){
        if(authorService.getById(authorId)==null) return Result.fail("作者不存在");
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

    @ApiOperation(value = "查询某一节可编辑作者列表",response = Author.class)
    @GetMapping("/queryEdit")
    public Result queryEdit(Integer sectionId){
        List<AuthorSection> authorSections = authorSectionService.list(new QueryWrapper<AuthorSection>().eq("section_id", sectionId));
        List<Author> authors = new ArrayList<>();
        for (AuthorSection authorSection : authorSections) {
            authors.add(authorService.getById(authorSection.getAuthorId()).setPassword(null));
        }
        return Result.success(authors);
    }

}
