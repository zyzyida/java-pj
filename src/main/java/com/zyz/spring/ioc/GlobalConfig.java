package com.zyz.spring.ioc;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class GlobalConfig {
    @Value("8080")
    private String port;
    private String path;
    @Autowired //自动装载，通过类型进行注入byType.如果需要通过名称取值byName，使用@Qualifier("DataConfig")
    private DataConfig dataConfig;
}
