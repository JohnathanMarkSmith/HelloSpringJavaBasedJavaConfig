package com.johnathanmarksmith.hellospring;

import com.johnathanmarksmith.hellospring.bean.HelloWorld;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * Date:   4/25/13 / 9:37 AM
 * Author: Johnathan Mark Smith
 * Email:  john@johnathanmarksmith.com
 * <p/>
 * Comments:
 * This is the config example of how to use JavaConfig and not XML Files:
 * <p/>
 * This Would be the same as the following
 * <beans>
 * <bean id="helloWorld" class="HelloWorld" />
 * </beans>
 */


@Configuration
@Import(DatabaseConfiguration.class)
@ComponentScan
@PropertySource("classpath:application.properties")
public class HelloWorldConfiguration {

    @Bean
    public HelloWorld getHelloWorld(Environment env) {
        HelloWorld hw = new HelloWorld();
        hw.setMessage(env.getProperty("bean.text"));
        return hw;
    }


}