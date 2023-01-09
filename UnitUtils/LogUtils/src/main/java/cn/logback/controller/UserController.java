package cn.logback.controller;

import cn.logback.params.UserInfoParam;
import cn.logback.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2023/1/9 02:06:06
 * @Version : 1.0
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    @GetMapping("info/{name}/{age}")
    public UserInfoParam getUserInfo(@PathVariable String name, @PathVariable Integer age) {
        return userInfoService.modifyUserInfo(name, age);

    }
}
