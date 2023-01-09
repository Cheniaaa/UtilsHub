package cn.logback.service;

import cn.logback.params.UserInfoParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2023/1/9 02:12:12
 * @Version : 1.0
 */

@Service
public class UserInfoService {
    private final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    public UserInfoParam modifyUserInfo(String name, Integer age) {
        logger.info("姓名：" + name + " 年龄：" + age);
        int i = 1 / 0;
        return new UserInfoParam(name.toLowerCase(), age);
    }
}
