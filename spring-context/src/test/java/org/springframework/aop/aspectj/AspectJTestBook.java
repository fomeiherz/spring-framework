package org.springframework.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring源码深度解析的AspectJ的案例
 *
 * @author fomeiherz
 * @date 2020/2/28 21:22
 */
public class AspectJTestBook {
	
	public static class TestBean {
		
		private String testStr = "testStr";

		public String getTestStr() {
			return testStr;
		}

		public void setTestStr(String testStr) {
			this.testStr = testStr;
		}
		
		public void test() {
			System.out.println("test");
		}
	}

	@Aspect
	public static class AspectJTest {
		@Pointcut("execution(* *.test(..))")
		public void test() {

		}

		@Before("test()")
		public void beforeTest() {
			System.out.println("beforeTest");
		}

		@After("test()")
		public void afterTest() {
			System.out.println("afterTest");
		}

		@Around("test()")
		public Object arountTest(ProceedingJoinPoint p) {
			System.out.println("before1");
			Object o = null;
			try {
				o = p.proceed();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
			System.out.println("after1");
			return o;
		}
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("org/springframework/aop/aspectj/AspectJTestBook.xml");
		AspectJTestBook.TestBean testBean = (TestBean) applicationContext.getBean("test");
		testBean.test();
	}
	
}


