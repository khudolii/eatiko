package com.eatiko.logic.utils;

import com.eatiko.logic.processors.RecipeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppUtil {

    @Autowired
    private static ApplicationContext applicationContext;

    public static ApplicationContext getContext() {
        if (applicationContext == null)
            applicationContext = new AnnotationConfigApplicationContext();
        return applicationContext;
    }

}
