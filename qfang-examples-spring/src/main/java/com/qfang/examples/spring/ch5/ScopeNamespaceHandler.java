package com.qfang.examples.spring.ch5;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.support.SimpleThreadScope;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by walle on 2017/4/8.
 */
public class ScopeNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("thread", new ScopeThreadBeanDefinitionParser());
    }

    private static class ScopeThreadBeanDefinitionParser implements BeanDefinitionParser {

        @Override
        public BeanDefinition parse(Element element, ParserContext parserContext) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition();
            beanDefinition.setBeanClass(CustomScopeConfigurer.class);
            beanDefinition.setScope("singleton");

            Map<String, Object> scopes = new HashMap<>();
            scopes.put("thread", new SimpleThreadScope());
            beanDefinition.getPropertyValues().add("scopes", scopes);

            parserContext.getRegistry().registerBeanDefinition("CustomScopeConfigurer", beanDefinition);
            return beanDefinition;
        }
    }

}
