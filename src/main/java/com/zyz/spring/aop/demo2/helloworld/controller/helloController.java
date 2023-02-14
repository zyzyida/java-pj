package com.zyz.spring.aop.demo2.helloworld.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SuppressWarnings("ALL")
@RestController
@Slf4j
@CrossOrigin
public class helloController {
    private final static Logger logger = LoggerFactory.getLogger(helloController.class);

    /**
     * Hello，World
     *
     * @param who 参数，非必须
     * @return Hello, ${who}
     */
    @GetMapping("/hello")
//    @WebLog(desc = "这是一个欢迎接口")
    public String sayHello(@RequestParam(required = false, name = "who") String who) {
        logger.info("========================================== dada ==========================================");
        if (StrUtil.isBlank(who)) {
            who = "World";
        }
        return StrUtil.format("Hello, {}!", who);
    }
}
