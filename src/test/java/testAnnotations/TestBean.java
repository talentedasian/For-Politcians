package testAnnotations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@Bean
@Primary
public @interface TestBean {
}
