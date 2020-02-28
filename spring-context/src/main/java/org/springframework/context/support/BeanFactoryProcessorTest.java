package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringValueResolver;

import java.util.HashSet;
import java.util.Set;

public class BeanFactoryProcessorTest implements BeanFactoryPostProcessor {

	private Set<String> obscenties = new HashSet<>();

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] beanNames = beanFactory.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
			StringValueResolver resolver = strVal -> {
				if (isObscene(strVal)) {
					return "******";
				}
				return strVal;
			};
			BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(resolver);
			visitor.visitBeanDefinition(bd);
		}
	}

	private boolean isObscene(String strVal) {
		String potentialObscenity = strVal.toUpperCase();
		return this.obscenties.contains(potentialObscenity);
	}

	public Set<String> getObscenties() {
		return obscenties;
	}

	public void setObscenties(Set<String> obscenties) {
		this.obscenties.clear();
		for (String ob : obscenties) {
			this.obscenties.add(ob.toUpperCase());
		}
	}

}
