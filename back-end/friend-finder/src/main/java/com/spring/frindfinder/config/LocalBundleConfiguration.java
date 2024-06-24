package com.spring.frindfinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocalBundleConfiguration {

    @Value("${spring.messages.basename}")
    private String baseName;

    @Value("${spring.messages.local-default}")
    private String localDefault;


    @Bean("messages")
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(baseName);
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public LocaleResolver localeResolver(){
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(localDefault));
        return localeResolver;
    }
}
