package com.spring.boot.rest.service.bundle;
import com.spring.boot.rest.exceptions.BundleMessage;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class BundleTranslatorService {

    public static ResourceBundleMessageSource messageSource;

    public BundleTranslatorService(ResourceBundleMessageSource messageSource) {
        BundleTranslatorService.messageSource = messageSource;
    }

    public static BundleMessage getMessage(String key) {
        return new BundleMessage(messageSource.getMessage(key, null, LocaleContextHolder.getLocale()));

    }


}
