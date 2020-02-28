package org.springframework.beans.factory.xml;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.core.io.ClassPathResource;

import java.lang.reflect.Method;

public class XmlBeanFactoryTest {

	@Test
	public void testMyTestBean() {
		// 传入getClass()参数表示当前类目录开始找文件
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("beanFactoryTest.xml", getClass()));
		MyTestBean bean = (MyTestBean) factory.getBean("myTestBean");
		System.out.println(bean.getTestStr());
	}

	public static class MyTestBean {
		private String testStr = "testStr";

		public String getTestStr() {
			return testStr;
		}

		public void setTestStr(String testStr) {
			this.testStr = testStr;
		}
	}

	@Test
	public void testLookupMethod() {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("lookupMethodTest.xml", getClass()));
		ShowBean bean = (ShowBean) factory.getBean("showBean");
		bean.showMe();
	}

	public static class User {
		public void showMe() {
			System.out.println("i am user!");
		}
	}

	public static class Teacher extends User {
		public void showMe() {
			System.out.println("i am Teacher!");
		}
	}

	public abstract static class ShowBean {
		public void showMe() {
			getBean().showMe();
		}
		public abstract User getBean();
	}

	@Test
	public void testReplacedMethod() {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("replacedMethodTest.xml", getClass()));
//		TestChangedMethod bean = (TestChangedMethod) factory.getBean("replacedMethod1");
		TestChangedMethod bean = factory.getBean("replacedMethod2", TestChangedMethod.class);
		bean.show();
	}

	public static class TestChangedMethod {
		public String showMe(String name) {
			System.out.println(String.format("i am %s!", name));
			return name;
		}

		public void show() {
			System.out.println(showMe("lilei"));
		}
	}

	public static class TestChangedReplacer implements MethodReplacer {
		@Override
		public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
//			System.out.println(String.format("replaced method name: %s.", method.getName()));
//			for (Object o : args) {
//				System.out.println(o.toString());
//			}
			return "hanmei";
		}
	}

	@Test
	public void testConstructorMethod() {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("constructorArgTest.xml", getClass()));
		ConstructorBean bean = (ConstructorBean) factory.getBean("constructorBean");
		bean.show();
	}

	public static class ConstructorBean {

		private String[] name;

		public ConstructorBean(String[] name, String value) {
			this.name = name;
		}

		public void show() {
			System.out.println(name[0]);
			System.out.println(name[1]);
		}
	}

}