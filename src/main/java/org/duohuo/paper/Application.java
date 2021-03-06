package org.duohuo.paper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableSwagger2
@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy
public class Application {

    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }
}
