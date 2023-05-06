package org.newsportal.configuration;

import org.hibernate.SessionFactory;
import org.newsportal.repository.util.HibernateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("org.newsportal")
@EnableWebMvc
public class AppConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

}
