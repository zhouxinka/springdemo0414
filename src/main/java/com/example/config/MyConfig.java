package com.example.config;

import com.example.entity.Sample;
import com.example.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @author zhoupeng
 * @create time 2021-04-14-12:18
 */
@Configuration
public class MyConfig {
    @Bean(name="sample1")
    public Sample getSample(){
        return new Sample("1","BC");
    }
}
