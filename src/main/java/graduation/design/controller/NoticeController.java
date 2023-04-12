package graduation.design.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import graduation.design.annotation.Authority;
import graduation.design.entity.Notice;
import graduation.design.entity.NoticeUser;
import graduation.design.entity.User;
import graduation.design.service.NoticeService;
import graduation.design.service.NoticeUserService;
import graduation.design.service.UserService;
import graduation.design.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 通知表 前端控制器
 * </p>
 *
 * @author zwk
 * @since 2023年03月12日
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    UserService userService;

    @Autowired
    NoticeUserService noticeUserService;

    @Authority({"admin","teacher","assistant"})
    @ApiOperation("发布通知,接口权限admin,teacher,assistant")
    @PostMapping("/add")
    public Result add(@RequestBody NoticeVo noticeVo){
        Notice notice = new Notice();
        if(noticeService.getOne(new QueryWrapper<Notice>().eq("title",noticeVo.getTitle()))!=null){
            return Result.fail("该通知已存在");
        }
        if(noticeVo.getUserIds().length==0) {
            return Result.fail("用户列表不能为空");
        }
        notice.setContent(noticeVo.getContent()).setTitle(noticeVo.getTitle()).setTime(LocalDateTime.now()).setUserId(noticeVo.getUserId());
        noticeService.save(notice);
        Notice notice1 = noticeService.getOne(new QueryWrapper<Notice>().eq("title", noticeVo.getTitle()));
        Integer[] userIds = noticeVo.getUserIds();
        for (Integer userId : userIds) {
            NoticeUser noticeUser = new NoticeUser();
            if(noticeUserService.getOne(new QueryWrapper<NoticeUser>().eq("notice_id",notice1.getId()).eq("user_id",userId))!=null){
                continue;
            }
            noticeUser.setNoticeId(notice1.getId()).setUserId(userId).setReceive(0);
            noticeUserService.save(noticeUser);
        }
        return Result.success("通知发布成功");
    }

    @Authority({"admin","teacher","assistant"})
    @ApiOperation("修改通知,接口权限admin,teacher,assistant")
    @PostMapping("/modify")
    public Result modify(@RequestBody NoticeVo noticeVo){
        Notice notice = noticeService.getById(noticeVo.getId());
        if(noticeVo.getUserIds().length==0) {
            return Result.fail("用户列表不能为空");
        }
        notice.setContent(noticeVo.getContent()).setTitle(noticeVo.getTitle()).setTime(LocalDateTime.now()).setUserId(noticeVo.getUserId());
        noticeService.saveOrUpdate(notice);
        Integer[] userIds = noticeVo.getUserIds();
        noticeUserService.remove(new QueryWrapper<NoticeUser>().eq("notice_id",notice.getId()));
        for (Integer userId : userIds) {
            NoticeUser noticeUser = new NoticeUser();
            if(noticeUserService.getOne(new QueryWrapper<NoticeUser>().eq("notice_id",notice.getId()).eq("user_id",userId))!=null){
                continue;
            }
            noticeUser.setNoticeId(notice.getId()).setUserId(userId).setReceive(0);
            noticeUserService.save(noticeUser);
        }
        return Result.success("通知修改成功");
    }

    @Authority({"admin","teacher","assistant"})
    @ApiOperation("删除通知,接口权限admin,teacher,assistant")
    @GetMapping("/delete")
    public Result delete(@NotNull Integer userId,@NotNull Integer noticeId){
        if(!noticeService.getById(noticeId).getUserId().equals(userId)){
            return Result.fail("无法删除他人发布的通知");
        }
        noticeService.removeById(noticeId);
        noticeUserService.remove(new QueryWrapper<NoticeUser>().eq("notice_id",noticeId));
        return Result.success("删除成功");
    }

    @Authority({"admin","teacher","assistant"})
    @ApiOperation("获取我发布的通知列表,接口权限admin,teacher,assistant")
    @GetMapping("/publish")
    public Result publish(Integer userId){
        List<Notice> notices = noticeService.list(new QueryWrapper<Notice>().eq("user_id", userId));
        return Result.success(notices);
    }

    @Authority({"admin","teacher","assistant"})
    @ApiOperation("根据通知id获取已确认和未确认的用户列表,接口权限admin,teacher,assistant")
    @GetMapping("/user/list")
    public Result userList(Integer noticeId){
        List<NoticeUser> noticeUsers = noticeUserService.list(new QueryWrapper<NoticeUser>().eq("notice_id", noticeId));
        UserReceiveListVo userReceiveListVo = new UserReceiveListVo();
        List<UserReceiveVo> receiveUsers = new ArrayList<>();
        List<UserReceiveVo> unReceiveUsers = new ArrayList<>();
        userReceiveListVo.setReceiveUsers(receiveUsers);
        userReceiveListVo.setUnReceiveUsers(unReceiveUsers);
        for (NoticeUser noticeUser : noticeUsers) {
            UserReceiveVo userReceiveVo = new UserReceiveVo();
            User user = userService.getById(noticeUser.getUserId());
            userReceiveVo.setId(user.getId());
            userReceiveVo.setName(user.getName());
            if(noticeUser.getReceive().equals(1)){
                userReceiveVo.setReceiveTime(noticeUser.getReceiveTime());
                receiveUsers.add(userReceiveVo);
            }else if(noticeUser.getReceive().equals(0)){
                unReceiveUsers.add(userReceiveVo);
            }
        }
        return Result.success(userReceiveListVo);
    }

    @ApiOperation("获取我的通知列表,接口权限all")
    @GetMapping("/list")
    public Result list(Integer userId){
        List<NoticeVo2> list = new ArrayList<>();
        List<NoticeUser> noticeUsers = noticeUserService.list(new QueryWrapper<NoticeUser>().eq("user_id", userId));
        for (NoticeUser noticeUser : noticeUsers) {
            NoticeVo2 noticeVo2 = new NoticeVo2();
            noticeVo2.setId(noticeUser.getNoticeId());
            noticeVo2.setReceive(noticeUser.getReceive());
            Notice notice = noticeService.getById(noticeUser.getNoticeId());
            noticeVo2.setTitle(notice.getTitle());
            noticeVo2.setContent(notice.getContent());
            noticeVo2.setTime(notice.getTime());
            noticeVo2.setName(userService.getById(notice.getUserId()).getName());
            list.add(noticeVo2);
        }
        return Result.success(list);
    }

    @ApiOperation("确认通知,接口权限all")
    @GetMapping("/receive")
    public Result receive(Integer userId,Integer noticeId){
        NoticeUser noticeUser = noticeUserService.getOne(new QueryWrapper<NoticeUser>().eq("user_id", userId).eq("notice_id", noticeId));
        if(noticeUser==null) return Result.fail("该通知不存在");
        noticeUser.setReceive(1);
        noticeUser.setReceiveTime(LocalDateTime.now());
        noticeUserService.saveOrUpdate(noticeUser);
        return Result.success("确认成功");
    }

}
