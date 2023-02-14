package com.zyz.spring.helloworld;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * SpringBoot启动类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-09-28 14:49
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@RestController
public class SpringBootDemoHelloworldApplication {

    private final static Logger logger = LoggerFactory.getLogger(SpringBootDemoHelloworldApplication.class);

    public static void main(String[] args) {
        try{
            SpringApplication.run(SpringBootDemoHelloworldApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Hello，World
     *
     * @param who 参数，非必须
     * @return Hello, ${who}
     */
    @GetMapping("/hello")
    @WebLog(desc = "这是一个欢迎接口")
    public String sayHello(@RequestParam(required = false, name = "who") String who) {
        logger.info("========================================== dada ==========================================");
        if (StrUtil.isBlank(who)) {
            who = "World";
        }
        return StrUtil.format("Hello, {}!", who);
    }
}
