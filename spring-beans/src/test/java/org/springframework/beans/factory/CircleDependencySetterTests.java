package org.springframework.beans.factory;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

public class CircleDependencySetterTests {

	@Test
	public void testCircleByConstructor() {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("CircleDependencySetter.xml", getClass()));
		Assert.isInstanceOf(ClassA.class, factory.getBean("classA"));
	}

	public static class ClassA {
		private ClassB classB;

		public ClassA() {
		}

		public ClassB getClassB() {
			return classB;
		}

		public void setClassB(ClassB classB) {
			this.classB = classB;
		}
	}

	public static class ClassB {
		private ClassC classC;

		public ClassB() {
		}

		public ClassC getClassC() {
			return classC;
		}

		public void setClassC(ClassC classC) {
			this.classC = classC;
		}
	}

	public static class ClassC {
		private ClassA classA;

		public ClassC() {
		}

		public ClassA getClassA() {
			return classA;
		}

		public void setClassA(ClassA classA) {
			this.classA = classA;
		}
	}

}
