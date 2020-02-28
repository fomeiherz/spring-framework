package test.aop;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class EnhancerDemo {

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(EnhancerDemo.class);
		enhancer.setCallback(new MethodInterceptorImpl());

		EnhancerDemo demo = (EnhancerDemo) enhancer.create();
		demo.print();
		System.out.println(demo);
	}

	public void print() {
		System.out.println("i am test.");
	}

	private static class MethodInterceptorImpl implements MethodInterceptor {

		@Override
		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			System.out.println("before invoke " + method);
			Object result = methodProxy.invokeSuper(o, objects);
			System.out.println("after invoke " + method);
			return result;
		}

	}

}
