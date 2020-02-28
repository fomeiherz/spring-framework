package test.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTest {

	public static void main(String[] args) {
		UserService service = new UserServiceImpl();
		MyInvocationHandler handler = new MyInvocationHandler(service);
		UserService proxy = (UserService) handler.getProxy();
		proxy.add();
		System.out.println(proxy.toString());
	}

	public static class MyInvocationHandler implements InvocationHandler {

		private Object targetObject;

		MyInvocationHandler(Object targetObject) {
			this.targetObject = targetObject;
		}

		public Object getProxy() {
			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					targetObject.getClass().getInterfaces(), this);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("invoke begin....");
			Object result = method.invoke(targetObject, args);
			System.out.println("invoke end...");
			return result;
		}
	}

	public static class UserServiceImpl implements UserService {
		@Override
		public void add() {
			System.out.println("--------- add --------");
		}
	}

	public interface UserService {
		void add();
	}

}
