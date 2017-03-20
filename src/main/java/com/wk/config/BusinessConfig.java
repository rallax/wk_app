package com.wk.config;


import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author andrey.trotsenko
 */
@Configuration
@Import({RabbitConfiguration.class, CouchbaseConfiguration.class})
public class BusinessConfig {

    @Bean
    public KieContainer kieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }
}
