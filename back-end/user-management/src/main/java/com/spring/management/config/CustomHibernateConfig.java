package com.spring.management.config;

import com.spring.commonlib.config.HibernateConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.spring.management",
                transactionManagerRef = "jpaTransactionManager")
@EnableTransactionManagement
@EnableJpaAuditing
public class CustomHibernateConfig extends HibernateConfig {

}
