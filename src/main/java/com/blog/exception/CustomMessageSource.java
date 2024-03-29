package com.blog.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomMessageSource {

    private MessageSource messageSource;

    public CustomMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Returns language specific from the resources.
     *
     * @param name is the field name
     * @return
     */
    public String getMessage(String name) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(name, null, locale);
    }

}
