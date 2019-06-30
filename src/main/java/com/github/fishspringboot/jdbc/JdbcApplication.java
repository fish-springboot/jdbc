package com.github.fishspringboot.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * # CommandLineRunner
 * Interface used to indicate that a bean should <em>run</em> when it is contained within
 * a {@link SpringApplication}. Multiple {@link CommandLineRunner} beans can be defined
 * within the same application context and can be ordered using the {@link Ordered}
 * interface or {@link Order @Order} annotation.
 */
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
public class JdbcApplication implements CommandLineRunner {
    /**
     * 数据连接层
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 查看下数据源的信息
     * @throws SQLException
     */
    private void showDataSource() throws SQLException {
        log.info("数据源: " + dataSource.toString());
        // 数据源: HikariDataSource (null)
        // spring-boot-starter-jdbc默认是使用HikariCP
        // 我们可以自定义的DataSource来覆盖

        Connection connection = dataSource.getConnection();
        log.info("数据库连接 " + connection.toString());
        // 数据库连接 HikariProxyConnection@335639458 wrapping conn0: url=jdbc:h2:mem:testdb user=SA
        connection.close();
    }

    /**
     * 上层框架
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 测试JdbcTemplate的使用
     */
    private void crud(){
        jdbcTemplate.execute("create table fish (name char(20))");
        Arrays.asList("Jack", "Jon").forEach(value -> {
            jdbcTemplate.update("insert into fish values (?)", value);
        });

        log.info("数据库中的记录数: " +
                jdbcTemplate.queryForObject("select count(*) from fish", Long.class));
    }

    /**
     * 钩子函数
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        showDataSource();
        crud();
    }

    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class, args);
    }
}
