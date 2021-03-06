package link.myrecipes.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@EnableJpaAuditing
public class MyRecipesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRecipesApiApplication.class, args);
    }

}
