package com.zyz.spring.ioc;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class DataConfig {
    @Value("localhost:3306")
    private String url;
    private String driverName;
    @Value("root")
    private String username;
    @Value("root")
    private String password;
}
