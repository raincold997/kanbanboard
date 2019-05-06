package com.edu.nju.kanbanboard.web;

import com.edu.nju.kanbanboard.comm.Const;
import com.edu.nju.kanbanboard.comm.aop.LoggerManager;
import com.edu.nju.kanbanboard.model.domain.KBUser;
import com.edu.nju.kanbanboard.model.dto.JsonResult;
import com.edu.nju.kanbanboard.model.dto.LoginInfoDto;
import com.edu.nju.kanbanboard.model.enums.ResultCodeEnum;
import com.edu.nju.kanbanboard.service.UserService;
import com.edu.nju.kanbanboard.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    @LoggerManager(description = "主页面")
    public JsonResult mainPage(HttpSession session){
        if(session.getAttribute(Const.LOGIN_SESSION_KEY) == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"请先登录");
        }else{
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"登录",session.getAttribute(Const.LOGIN_SESSION_KEY));
        }
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @LoggerManager(description = "登陆")
    public JsonResult getLogin(@RequestBody LoginInfoDto loginInfoDto,
                            HttpSession session){
        final KBUser user = userService.findByEmail(loginInfoDto.getEmail());
        if(user == null){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"登录账号不存在");
        }else if(!user.getUserPass().equals(getPwd(loginInfoDto.getPassword()))){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"登录密码错误");
        }
        session.setAttribute(Const.LOGIN_SESSION_KEY,user);
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"登录成功",user);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @LoggerManager(description = "注册")
    public JsonResult register(@RequestBody @Valid KBUser user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            for(ObjectError error:bindingResult.getAllErrors()){
                return new JsonResult(ResultCodeEnum.FAIL.getCode(),error.getDefaultMessage());
            }
        }
        try{
            KBUser registUser = userService.findByEmail(user.getUserEmail());
            if(null != registUser){
                return new JsonResult(ResultCodeEnum.FAIL.getCode(),"邮箱已被使用");
            }
            registUser = userService.findByName(user.getUserName());
            if(null != registUser){
                return new JsonResult(ResultCodeEnum.FAIL.getCode(),"用户名已被使用");
            }
            user.setUserPass(getPwd(user.getUserPass()));
            userService.create(user);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(),"注册成功");
        }catch (Exception e){
            return new JsonResult(ResultCodeEnum.FAIL.getCode(),"发生了意外");
        }
    }
}
