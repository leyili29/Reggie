package com.leyili.controller;

import com.leyili.pojo.Result;
import com.leyili.pojo.User;
import com.leyili.service.UserService;
import com.leyili.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    //发送验证码
    @PostMapping("/sendMsg")
    public Result sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //生成随机的四位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();

            //验证码输出在日志上
            log.info("****************登录的验证码为:{}",code);

            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);

            return Result.success("发送验证码成功");
        }

        return Result.error("发送验证码失败");
    }


    //移动端用户登录
    @PostMapping("/login")
    public Result login(@RequestBody Map map, HttpSession session){

        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        //送Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);

        //进行验证码的比对(页面提交的验证码和Session中保存的验证码比对)
        if (codeInSession!=null && codeInSession.equals(code)){
            //如果比对成功,登录成功

             User user = userService.getByPhone(phone);

             if(user == null){
                 //判断当前手机号对应的用户是否为新用户,如果是新用户就自动完成注册
                 user = new User();
                 user.setPhone(phone);
                 user.setStatus(1);
                 userService.save(user);
             }
             session.setAttribute("user",user.getId());
            return Result.success(user);
        }

        return Result.error("登录失败");
    }

    //退出
    @PostMapping("/loginout")
    public Result logout(HttpServletRequest request){
        //清理session中保存的当前登录user的id
        request.getSession().removeAttribute("employee");

        return Result.success("退出成功");
    }

}
