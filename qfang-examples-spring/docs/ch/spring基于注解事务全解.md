# spring 基于注解事务全解

xml 配置如下  
``` xml
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="dataSource"/>
</bean>

<tx:annotation-driven transaction-manager="transactionManager" />
```

`<tx:annotation-driven />` 自定义标签对应的解析类为： `TxNamespaceHandler#init` 方法如下：  
*对于 spring 自定义标签请参看 [spring 自定义标签全解](./spring自定义标签全解.md)*  

``` java
// 对于 <tx:annotation-driven /> 标签的解析类
registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
```

`AnnotationDrivenBeanDefinitionParser#parse()`方法  
``` java
// 因为我们没有配置 aspectj 模式，所以走默认的 proxy 模式
// mode="proxy"
AopAutoProxyConfigurer.configureAutoProxyCreator(element, parserContext);
```

`AopAutoProxyConfigurer#configureAutoProxyCreator()`方法  
``` java
public static void configureAutoProxyCreator(Element element, ParserContext parserContext) {
    // 1. 注册 AutoProxyCreator 自动代理类的创建类，如果之前没有注册
	AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);

	String txAdvisorBeanName = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME;
	if (!parserContext.getRegistry().containsBeanDefinition(txAdvisorBeanName)) {
		Object eleSource = parserContext.extractSource(element);

        // 2. TransactionAttributeSource 相当于 pointcut
		// Create the TransactionAttributeSource definition.
		RootBeanDefinition sourceDef = new RootBeanDefinition(
				"org.springframework.transaction.annotation.AnnotationTransactionAttributeSource");
		sourceDef.setSource(eleSource);
		sourceDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		String sourceName = parserContext.getReaderContext().registerWithGeneratedName(sourceDef);

        // 3. interceptor 相当于 advice
		// Create the TransactionInterceptor definition.
		RootBeanDefinition interceptorDef = new RootBeanDefinition(TransactionInterceptor.class);
		interceptorDef.setSource(eleSource);
		interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		registerTransactionManager(element, interceptorDef);
		interceptorDef.getPropertyValues().add("transactionAttributeSource", new RuntimeBeanReference(sourceName));
		String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

        // 4. advisor
		// Create the TransactionAttributeSourceAdvisor definition.
		RootBeanDefinition advisorDef = new RootBeanDefinition(BeanFactoryTransactionAttributeSourceAdvisor.class);
		advisorDef.setSource(eleSource);
		advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		advisorDef.getPropertyValues().add("transactionAttributeSource", new RuntimeBeanReference(sourceName));
		advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
		if (element.hasAttribute("order")) {
			advisorDef.getPropertyValues().add("order", element.getAttribute("order"));
		}
		parserContext.getRegistry().registerBeanDefinition(txAdvisorBeanName, advisorDef);

		CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), eleSource);
		compositeDef.addNestedComponent(new BeanComponentDefinition(sourceDef, sourceName));
		compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
		compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, txAdvisorBeanName));
		parserContext.registerComponent(compositeDef);
	}
}
```
