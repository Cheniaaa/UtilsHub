package common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Description : IO操作主程序
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/16  09:28:28
 * @Version : 1.0
 */

@SpringBootApplication
@MapperScan(basePackages = "com.mapper")
public class IOApplication {
    public static void main(String[] args) {
        SpringApplication.run(IOApplication.class);
    }
}
