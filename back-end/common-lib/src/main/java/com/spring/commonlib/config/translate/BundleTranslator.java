package com.spring.commonlib.config.translate;

import com.spring.commonlib.model.bundle.BundleErrorMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BundleTranslator {

    private static ResourceBundleMessageSource resourceBundleMessageSource;

    public BundleTranslator(@Qualifier("messages") ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    public static String getMessage(String code){
        Locale locale = LocaleContextHolder.getLocale();
        return resourceBundleMessageSource.getMessage(code,null,locale);
    }

    public static BundleErrorMessage getMessages(String code){
        return new BundleErrorMessage(resourceBundleMessageSource.getMessage(code,null, new Locale("ar")),
                resourceBundleMessageSource.getMessage(code,null, new Locale("en")));
    }
}
