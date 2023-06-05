package com.user.management.config;

import liquibase.integration.spring.SpringLiquibase;

import javax.sql.DataSource;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.user.management",
                transactionManagerRef = "jpaTransactionManager")
@EnableTransactionManagement
@EnableJpaAuditing
public class HibernateConfig {

    @Value("${datasource.driverClassName}")
    private String driverClassName;
    @Value("${datasource.databaseUrl}")
    private String databaseUrl;
    @Value("${datasource.databaseUser}")
    private String databaseUser;
    @Value("${datasource.databasePassword}")
    private String databasePassword;
    @Value("${jpa.hibernate.hibernateDDLAuto}")
    private String hibernateDDLAuto;
    @Value("${jpa.hibernate.hibernateDialect}")
    private String hibernateDialect;
    @Value("${jpa.showSql}")
    private String showSql;
    @Value("${jpa.hibernate.create_empty_composites.enabled}")
    private boolean createEmptyComposites;

    @Bean(name = "platformDataSource")
    public DataSource platformDataSource() {
        return DataSourceBuilder.create().username(databaseUser)
                        .password(databasePassword).url(databaseUrl)
                        .driverClassName(driverClassName).build();
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean platformEntityManger() {
        LocalContainerEntityManagerFactoryBean em =
                        new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(platformDataSource());
        em.setPackagesToScan(new String[] { "com.user.management" });
        em.setPersistenceUnitName("platformPersistenceUnitName");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", hibernateDDLAuto);
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.create_empty_composites.enabled",
                        createEmptyComposites);

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(platformEntityManger().getObject());
        return transactionManager;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:db/master.xml");
        springLiquibase.setDataSource(platformDataSource());
        return springLiquibase;
    }
}
