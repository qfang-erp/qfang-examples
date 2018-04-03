package com.qfang.examples.spring.ch6;

import com.qfang.examples.spring.ioc.property.MD5EncryptedPropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by walle on 2017/4/16.
 */
public class MyExtContextHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("encrypted-property-placeholder",
                new EncryptedPropertyPlaceHolderBeanDefinitionParser());
    }

    private static class EncryptedPropertyPlaceHolderBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

        @Override
        protected boolean shouldGenerateId() {
            return true;
        }

        @Override
        protected Class<?> getBeanClass(Element element) {
            return MD5EncryptedPropertyPlaceholderConfigurer.class;
        }

        @Override
        protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
            String location = element.getAttribute("location");
            if (StringUtils.hasLength(location)) {
                location = parserContext.getReaderContext().getEnvironment().resolvePlaceholders(location);
                String[] locations = StringUtils.commaDelimitedListToStringArray(location);
                builder.addPropertyValue("locations", locations);
            }
        }
    }

}
