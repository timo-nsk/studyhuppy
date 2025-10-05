package com.studyhub.mail.application.service.filter;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class TemplateFilter {

    private final SpringTemplateEngine templateEngine;
    private final Context thisContext;

    public TemplateFilter(SpringTemplateEngine templateEngine, Context thisContext) {
        this.templateEngine = templateEngine;
        this.thisContext = thisContext;
    }

    public void setContextVariable(String value, Object obj) {
        System.out.println(obj);
        System.out.println(value);
        thisContext.setVariable(value, obj);
    }

    public String setTemplatePath(String path) {
        System.out.println(thisContext);
        return templateEngine.process(path, thisContext);
    }
}
