package com.github.fishspringboot.jdbc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceBeanConfig {
    /**
     * 把配置文件的信息加载到JavaBean中
     * @return
     */
    @Bean
    @ConfigurationProperties("foo.datasource")
    @Primary
    public DataSourceProperties fooDataSourceProperties(){
        return new DataSourceProperties();
    }

    /**
     * 创建数据源
     * @return
     */
    @Bean
    @Primary
    public DataSource fooDateSource(){
        DataSourceProperties properties = fooDataSourceProperties();
        log.info("正在创建数据库源: " + properties.getUrl());
        return properties.initializeDataSourceBuilder().build();
    }


    /**
     * 把配置文件的信息加载到JavaBean中
     * @return
     */
    @Bean
    @ConfigurationProperties("bar.datasource")
    public DataSourceProperties barDataSourceProperties(){
        return new DataSourceProperties();
    }

    /**
     * 创建数据源
     * @return
     */
    @Bean
    public DataSource barDateSource(){
        DataSourceProperties properties = barDataSourceProperties();
        log.info("正在创建数据库源: " + properties.getUrl());
        return properties.initializeDataSourceBuilder().build();
    }
}
