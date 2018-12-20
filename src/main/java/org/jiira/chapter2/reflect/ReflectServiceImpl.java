package org.jiira.chapter2.reflect;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectServiceImpl {
	private String constructor;
	public ReflectServiceImpl() {
		constructor = "none_constructor";
	}
	public ReflectServiceImpl(String constructor) {
		this.constructor = constructor;
	}
	public void print() {
		print("none_method");
	}
	public void print(String method) {
		System.err.println("构造函数 : " + constructor + ", 函数 : " + method);
	}
	/**
	 * 反射测试
	 */
	public static void reflect(boolean classParam, boolean methodParam) {
		ReflectServiceImpl object = null;
		Method method = null;
			try {
				if(classParam) {
					object = (ReflectServiceImpl) Class.forName("org.jiira.reflect.ReflectServiceImpl").getConstructor(String.class).newInstance("构造函数传参");
				} else {
					object = (ReflectServiceImpl) Class.forName("org.jiira.reflect.ReflectServiceImpl").newInstance();
				}
				if(methodParam) {
					method = object.getClass().getDeclaredMethod("print", String.class);
					method.invoke(object, "函数传参");
				} else {
					method = object.getClass().getDeclaredMethod("print");
					method.invoke(object);
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
