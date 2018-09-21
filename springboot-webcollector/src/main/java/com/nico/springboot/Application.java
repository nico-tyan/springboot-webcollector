package com.nico.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** 
 * 这是入口类
 * mybatis-plus Spring Boot 测试 Demo<br>
 * 文档：http://mp.baomidou.com<br>
 */
//开启自动事物管理
@EnableTransactionManagement
//开启自动配置
@SpringBootApplication
//组件扫描
@ComponentScan(basePackages = {
        "com.nico.springboot.config",
        "com.nico.springboot.controller",
        "com.nico.springboot.service",
        "com.nico.springboot.quartz"
        })
@EnableScheduling
public class Application {

    protected final static Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * <p>
     * 测试 RUN<br>
     * 查看 h2 数据库控制台：http://localhost:8080/console<br>
     * 使用：JDBC URL 设置 jdbc:h2:mem:testdb 用户名 sa 密码 sa 进入，可视化查看 user 表<br>
     * 误删连接设置，开发机系统本地 ~/.h2.server.properties 文件<br>
     * <br>
     * 1、http://localhost:8080/user/test<br>
     * 2、http://localhost:8080/user/test1<br>
     * 3、http://localhost:8080/user/test2<br>
     * 4、http://localhost:8080/user/test3<br>
     * 5、http://localhost:8080/user/add<br>
     * 6、http://localhost:8080/user/selectsql<br>
     * 7、分页 size 一页显示数量  current 当前页码
     * 方式一：http://localhost:8080/user/page?size=1&current=1<br>
     * 方式二：http://localhost:8080/user/pagehelper?size=1&current=1<br>
     * </p>
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        //是否开启springboot logo-默认true
        //app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        logger.info("PortalApplication is success!");
        System.err.println("端口和应用名称可在 application.yml 里面动态修改");
        System.err.println("简单的开始URL:   http://localhost:8020/user/test");
    	System.err.println("SQL监控URL:   http://localhost:8020/druid/");
    	System.err.println("SQL监控账号:nico  密码:nico");
    }

}
