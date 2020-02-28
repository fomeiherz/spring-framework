package org.springframework.beans.factory;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class CircleDependencyTests {

	@Test(expected = BeanCurrentlyInCreationException.class)
	public void testCircleByConstructor() throws Throwable {
		try {
			BeanFactory factory = new XmlBeanFactory(new ClassPathResource("CircleDependency.xml", getClass()));
			System.out.println(factory.getBean("classA"));
			System.out.println(factory.getBean("classB"));
		} catch (Exception ex) {
			Throwable el = ex.getCause().getCause().getCause();
			throw el;
		}
	}

	public static class ClassA {
		private ClassB classB;

		public ClassA(ClassB classB) {
			this.classB = classB;
		}
	}

	public static class ClassB {
		private ClassC classC;

		public ClassB(ClassC classC) {
			this.classC = classC;
		}
	}

	public static class ClassC {
		private ClassA classA;

		public ClassC(ClassA classA) {
			this.classA = classA;
		}
	}


//	@Test(expected = BeanCurrentlyInCreationException.class)
	@Test
	public void testCircleDependency() throws Throwable {
		try {
			BeanFactory factory = new XmlBeanFactory(new ClassPathResource("CircleDependency.xml", getClass()));
			ClassD classD = (ClassD) factory.getBean("classD");
			classD.print();
			ClassE classE = (ClassE) factory.getBean("classE");
		} catch (Exception ex) {
			ex.printStackTrace();
			Throwable el = ex.getCause().getCause().getCause();
			throw el;
		}
	}

	@Test
	public void testAware() {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("CircleDependency.xml", getClass()));
		ClassF classF = factory.getBean("classF", ClassF.class);
		ClassD classD = classF.getBeanFactory().getBean("classD", ClassD.class);
		classD.print();
	}

	public static class ClassD {
		private ClassE classE;

		public ClassD() {
		}

		public void print() {
			System.out.println("hello, i am classD.");
		}

		public ClassE getClassE() {
			return classE;
		}

		public void setClassE(ClassE classE) {
			this.classE = classE;
		}
	}

	public static class ClassE {
		private ClassD classD;

		public ClassE() {
		}

		public ClassD getClassD() {
			return classD;
		}

		public void setClassD(ClassD classD) {
			this.classD = classD;
		}
	}

	public static class ClassF implements BeanFactoryAware {
		private BeanFactory beanFactory;

		@Override
		public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
			this.beanFactory = beanFactory;
		}

		public BeanFactory getBeanFactory() {
			return beanFactory;
		}
	}

	@Test
	public void testCreateInstance() throws NoSuchMethodException {
		ClassD classD = BeanUtils.instantiateClass(ClassD.class.getDeclaredConstructor());
		classD.print();
	}

}
