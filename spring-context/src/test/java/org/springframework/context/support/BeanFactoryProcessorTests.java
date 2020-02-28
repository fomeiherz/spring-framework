package org.springframework.context.support;

import org.junit.Test;

public class BeanFactoryProcessorTests {

	@Test
	public void test() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("BeanFactoryProcessorTest.xml", getClass());
		SimplePostProcessor processor = ctx.getBean("bfpp", SimplePostProcessor.class);
		System.out.println(processor.getUsername());
		System.out.println(processor.getPassword());
		ctx.close();
	}

	public static class SimplePostProcessor {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

}
