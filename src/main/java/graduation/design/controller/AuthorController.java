package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.entity.Author;
import graduation.design.entity.Chapter;
import graduation.design.service.AuthorService;
import graduation.design.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 作者表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @ApiOperation(value = "获取作者列表",response = Author.class)
    @GetMapping ("/getAuthors")
    public Result getAuthors(){
        List<Author> authors = authorService.list(new QueryWrapper<Author>().select("id","account","name","avatar"));
        return Result.success(authors);
    }

}
